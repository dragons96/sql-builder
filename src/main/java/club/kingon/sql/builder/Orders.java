package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Order;

import java.util.function.Supplier;

/**
 * @author dragons
 * @date 2021/11/11 19:54
 */
public class Orders {

    private Orders() {
    }

    public static OrderSqlBuilder sort(String column, Order order) {
        return sort(true, column, order);
    }

    public static OrderSqlBuilder sort(boolean predicate, String column, Order order) {
        return sort(predicate ? Constants.TRUE_PREDICATE : Constants.FALSE_PREDICATE, column, order);
    }

    public static OrderSqlBuilder sort(Supplier<Boolean> predicate, String column, Order order) {
        return new OrderSqlBuilder(predicate, null, Constants.EMPTY_OBJECT_ARRAY, column, order);
    }

    public static OrderSqlBuilder sortAsc(String ...columns) {
        return sortAsc(true, columns);
    }

    public static OrderSqlBuilder sortAsc(boolean predicate, String ...columns) {
        return sortAsc(predicate ? Constants.TRUE_PREDICATE : Constants.FALSE_PREDICATE, columns);
    }

    public static OrderSqlBuilder sortAsc(Supplier<Boolean> predicate, String ...columns) {
        if (columns.length == 0) throw new SqlBuilderException("Column cannot be null in order by sql.");

        OrderSqlBuilder builder = new OrderSqlBuilder(predicate, null, Constants.EMPTY_OBJECT_ARRAY, columns[0], Order.ASC);
        for (int i = 1; i < columns.length; i++) {
            builder.addAsc(columns[i]);
        }
        return builder;
    }


    public static OrderSqlBuilder sortDesc(String ...columns) {
        return sortDesc(true, columns);
    }
    public static OrderSqlBuilder sortDesc(boolean predicate, String ...columns) {
        return sortDesc(predicate ? Constants.TRUE_PREDICATE : Constants.FALSE_PREDICATE, columns);
    }

    public static OrderSqlBuilder sortDesc(Supplier<Boolean> predicate, String ...columns) {
        if (columns.length == 0) throw new SqlBuilderException("Column cannot be null in order by sql.");

        OrderSqlBuilder builder = new OrderSqlBuilder(predicate, null, Constants.EMPTY_OBJECT_ARRAY, columns[0], Order.DESC);
        for (int i = 1; i < columns.length; i++) {
            builder.addDesc(columns[i]);
        }
        return builder;
    }

    public static Order parseOrder(String order) {
        if ("asc".equalsIgnoreCase(order)) return Order.ASC;
        if ("desc".equalsIgnoreCase(order)) return Order.DESC;
        return null;
    }
}
