package club.kingon.sql.builder;

import club.kingon.sql.builder.enums.Order;
import club.kingon.sql.builder.util.LambdaUtils;

import java.util.function.Supplier;

/**
 * @author dragons
 * @date 2021/11/10 19:20
 */
public interface OrderSqlBuilderRoute extends SqlBuilder {

    default OrderSqlBuilder orderBy(String sort) {
        return orderBy(Boolean.TRUE, sort);
    }

    default OrderSqlBuilder orderBy(Boolean predicate, String sort) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs(), sort);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    default OrderSqlBuilder orderBy(Boolean predicate, Supplier<String> sort) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs(), sort.get());
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    default OrderSqlBuilder orderBy(String column, Order order) {
        return orderBy(Boolean.TRUE, column, order);
    }

    default OrderSqlBuilder orderBy(Boolean predicate, String column, Order order) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs(), column, order);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    default <P>OrderSqlBuilder orderBy(LMDFunction<P, ?> lambdaFunction, Order order) {
        return orderBy(LambdaUtils.getColumnName(lambdaFunction), order);
    }

    default <P>OrderSqlBuilder orderBy(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Order order) {
        if (Boolean.TRUE.equals(predicate)) {
            return orderBy(lambdaFunction, order);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    default OrderSqlBuilder orderByAsc(String ...columns) {
        return orderByAsc(Boolean.TRUE, columns);
    }

    default OrderSqlBuilder orderByAsc(Boolean predicate, String ...columns) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs()).addAsc(columns);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

    default <P>OrderSqlBuilder orderByAsc(LMDFunction<P, ?> lambdaFunctions) {
        return new OrderSqlBuilder(precompileSql(), precompileArgs()).addAsc(lambdaFunctions);
    }

    default <P>OrderSqlBuilder orderByAsc(Boolean predicate, LMDFunction<P, ?> lambdaFunctions) {
        if (Boolean.TRUE.equals(predicate)) {
            return orderByAsc(lambdaFunctions);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }


    default OrderSqlBuilder orderByDesc(String ...columns) {
        return orderByDesc(Boolean.TRUE, columns);
    }

    default OrderSqlBuilder orderByDesc(Boolean predicate, String ...columns) {
        if (Boolean.TRUE.equals(predicate)) {
            return new OrderSqlBuilder(precompileSql(), precompileArgs()).addDesc(columns);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }


    default <P>OrderSqlBuilder orderByDesc(LMDFunction<P, ?> lambdaFunctions) {
        return new OrderSqlBuilder(precompileSql(), precompileArgs()).addDesc(lambdaFunctions);
    }

    default <P>OrderSqlBuilder orderByDesc(Boolean predicate, LMDFunction<P, ?> lambdaFunctions) {
        if (Boolean.TRUE.equals(predicate)) {
            return orderByDesc(lambdaFunctions);
        }
        return new OrderSqlBuilder(precompileSql(), precompileArgs());
    }

}
