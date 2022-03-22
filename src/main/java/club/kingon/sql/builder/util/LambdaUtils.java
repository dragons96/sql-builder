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

    public static <T extends LMDFunction>String getFieldName(T lambda) {
        return getClassAndFieldName(lambda)._2;
    }

    public static <T extends LMDFunction>String getColumnName(T lambda) {
        Tuple2<String, String> classAndField = getClassAndFieldName(lambda);
        try {
            // support @Column name.
            Class<?> lambdaClass = Class.forName(classAndField._1);
            // use table.column
            if (GlobalConfig.OPEN_LAMBDA_TABLE_NAME_MODE) {
                return  ObjectMapperUtils.getTableName(lambdaClass) + "." + ObjectMapperUtils.getColumnName(lambdaClass, classAndField._2);
            }
            return ObjectMapperUtils.getColumnName(lambdaClass, classAndField._2);
        } catch (ClassNotFoundException e) {
            log.warn("lambda class: {} cannot be loaded, ignore", classAndField._1, e);
        }
        return ObjectMapperUtils.humpNameToUnderlinedName(classAndField._2, "_");
    }

    private static <T extends LMDFunction>Tuple2<String, String> getClassAndFieldName(T lambda) {
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
        return Tuple2.of(classAndMethod._1, fieldName);
    }
}
