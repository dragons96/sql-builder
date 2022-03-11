package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Order;

import java.util.function.Supplier;

/**
 * @author dragons
 * @date 2021/11/10 19:20
 */
public interface OrderSqlBuilderRoute extends SqlBuilder {

    default OrderSqlBuilder orderBy(String sort) {
        return orderBy(true, sort);
    }

    default OrderSqlBuilder orderBy(boolean predicate, String sort) {
        if (predicate) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs(), sort);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    default OrderSqlBuilder orderBy(boolean predicate, Supplier<String> sort) {
        if (predicate) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs(), sort.get());
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    @Deprecated
    default OrderSqlBuilder orderBy(Supplier<Boolean> predicate, String sort) {
        return new OrderSqlBuilder(predicate, precompileSql(), precompileArgs(), sort);
    }

    default OrderSqlBuilder orderBy(String column, Order order) {
        return orderBy(true, column, order);
    }

    default OrderSqlBuilder orderBy(boolean predicate, String column, Order order) {
        if (predicate) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs(), column, order);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    @Deprecated
    default OrderSqlBuilder orderBy(Supplier<Boolean> predicate, String column, Order order) {
        return new OrderSqlBuilder(predicate, precompileSql(), precompileArgs(), column, order);
    }

    default OrderSqlBuilder orderByAsc(String ...columns) {
        return orderByAsc(Constants.TRUE_PREDICATE, columns);
    }

    default OrderSqlBuilder orderByAsc(boolean predicate, String ...columns) {
        return orderByAsc(predicate ? Constants.TRUE_PREDICATE : Constants.FALSE_PREDICATE, columns);
    }

    @Deprecated
    default OrderSqlBuilder orderByAsc(Supplier<Boolean> predicate, String ...columns) {
        if (columns.length == 0) throw new SqlBuilderException("Column cannot be null in order by sql.");
        OrderSqlBuilder builder = new OrderSqlBuilder(predicate, precompileSql(), precompileArgs(), columns[0], Order.ASC);
        for (int i = 1; i < columns.length; i++) {
            builder.addAsc(predicate, columns[i]);
        }
        return builder;
    }


    default OrderSqlBuilder orderByDesc(String ...columns) {
        return orderByDesc(Constants.TRUE_PREDICATE, columns);
    }

    default OrderSqlBuilder orderByDesc(boolean predicate, String ...columns) {
        return orderByDesc(predicate ? Constants.TRUE_PREDICATE : Constants.FALSE_PREDICATE, columns);
    }

    @Deprecated
    default OrderSqlBuilder orderByDesc(Supplier<Boolean> predicate, String ...columns) {
        if (columns.length == 0) throw new SqlBuilderException("Column cannot be null in order by sql.");
        OrderSqlBuilder builder = new OrderSqlBuilder(predicate, precompileSql(), precompileArgs(), columns[0], Order.DESC);
        for (int i = 1; i < columns.length; i++) {
            builder.addAsc(predicate, columns[i]);
        }
        return builder;
    }
}
