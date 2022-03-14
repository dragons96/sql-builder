package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Order;

/**
 * @author dragons
 * @date 2021/11/11 19:54
 */
public class Orders {

    private Orders() {
    }

    public static OrderSqlBuilder sort(String column, Order order) {
        return sort(Boolean.TRUE, column, order);
    }

    public static OrderSqlBuilder sort(Boolean predicate, String column, Order order) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY, column, order);
        }
        return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static OrderSqlBuilder sortAsc(String ...columns) {
        return sortAsc(Boolean.TRUE, columns);
    }

    public static OrderSqlBuilder sortAsc(Boolean predicate, String ...columns) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY).addAsc(columns);
        }
        return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static OrderSqlBuilder sortDesc(String ...columns) {
        return sortDesc(Boolean.TRUE, columns);
    }

    public static OrderSqlBuilder sortDesc(Boolean predicate, String ...columns) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY).addDesc(columns);
        }
        return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>OrderSqlBuilder sortAsc(LMDFunction<P, ?>... lambdaFunctions) {
        return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY).addAsc(lambdaFunctions);
    }

    public static <P>OrderSqlBuilder sortAsc(Boolean predicate, LMDFunction<P, ?>... lambdaFunctions) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY).addAsc(lambdaFunctions);
        }
        return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>OrderSqlBuilder sortDesc(LMDFunction<P, ?>... lambdaFunctions) {
        return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY).addDesc(lambdaFunctions);
    }

    public static <P>OrderSqlBuilder sortDesc(Boolean predicate, LMDFunction<P, ?>... lambdaFunctions) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY).addDesc(lambdaFunctions);
        }
        return new OrderSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY);
    }
}
