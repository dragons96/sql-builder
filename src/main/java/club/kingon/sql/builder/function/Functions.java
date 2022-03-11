package club.kingon.sql.builder.function;

import club.kingon.sql.builder.entry.Column;
import club.kingon.sql.builder.util.ConditionUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * todo how to implements
 * @author dragons
 * @date 2021/11/16 19:17
 */
public class Functions {

    /**
     * sql count() function
     * like:
     *  Functions.count(column) => count(column)
     * @param column column name
     * @return
     */
    public static String count(Object column) {
        return parseFunction(column);
    }

    /**
     * sql sum() function
     * like:
     *  Functions.sum(column1) => sum(column1)
     * @param column column name
     */
    public static String sum(String column) {
        return parseFunction(Column.as(column));
    }

    /**
     * sql sum() function
     * like:
     *  Functions.sum("column1 * 30") => sum(column1)
     * @param params params
     */
    public static String sum(Object ...params) {
        return parseFunction(params);
    }

    /**
     * sql concat() function
     * like:
     *  Functions.concat("(", Column.as("column1"), ")") => concat("(", column1, ")")
     * @param params params
     */
    public static String concat(Object ...params) {
        return parseFunction(params);
    }


    public static String parseFunction(Object ...params) {
        return Thread.currentThread().getStackTrace()[2].getMethodName().toUpperCase() + "(" + Arrays.stream(params).map(ConditionUtils::parseValue).collect(Collectors.joining(", ")) + ")";
    }
}
