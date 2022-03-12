package club.kingon.sql.builder.util;

import club.kingon.sql.builder.LMDFunction;
import club.kingon.sql.builder.LambdaException;
import club.kingon.sql.builder.Tuple2;
import club.kingon.sql.builder.config.GlobalConfig;
import club.kingon.sql.builder.inner.ObjectMapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragons
 * @date 2022/3/10 17:20
 */
public class LambdaUtils {

    private final static Logger log = LoggerFactory.getLogger(LambdaUtils.class);

    public static <T extends LMDFunction>String getColumnName(T lambda) {
        Tuple2<String, String> classAndMethod = ObjectMapperUtils.getLambdaImplementClassAndMethodName(lambda);
        String fieldName;
        if (classAndMethod._2.startsWith("is")) {
            fieldName = classAndMethod._2.substring(2);
        } else if (classAndMethod._2.startsWith("get") || classAndMethod._2.startsWith("set")) {
            fieldName = classAndMethod._2.substring(3);
        } else {
            throw new LambdaException("Error parsing property name '" + classAndMethod._2 + "'.  Didn't start with 'is', 'get' or 'set'.");
        }
        if (fieldName.length() == 1 || (fieldName.length() > 1 && !Character.isUpperCase(fieldName.charAt(1)))) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }

        try {
            // support @Column name.
            Class<?> lambdaClass = Class.forName(classAndMethod._1);
            // use table.column
            if (GlobalConfig.OPEN_LAMBDA_TABLE_NAME_MODE) {
                return  ObjectMapperUtils.getTableName(lambdaClass) + "." + ObjectMapperUtils.getColumnName(lambdaClass, fieldName);
            }
            return ObjectMapperUtils.getColumnName(lambdaClass, fieldName);
        } catch (ClassNotFoundException e) {
            log.warn("lambda class: {} cannot be loaded, ignore", classAndMethod._1, e);
        }
        return ObjectMapperUtils.humpNameToUnderlinedName(fieldName, "_");
    }
}
