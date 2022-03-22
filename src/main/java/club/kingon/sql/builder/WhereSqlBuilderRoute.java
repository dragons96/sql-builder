package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Operator;
import club.kingon.sql.builder.util.ConditionUtils;
import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

import java.util.function.Supplier;

/**
 * @author dragons
 * @date 2021/11/10 19:22
 */
public interface WhereSqlBuilderRoute extends SqlBuilder {

    default WhereSqlBuilder where(WhereSqlBuilder whereSqlBuilder) {
        return where(Boolean.TRUE, whereSqlBuilder);
    }

    default WhereSqlBuilder where(Boolean predicate, WhereSqlBuilder whereSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), whereSqlBuilder);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder where(Boolean predicate, Supplier<WhereSqlBuilder> whereSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), whereSqlBuilder.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder where(Object ...queryCriteria) {
        return where(Boolean.TRUE, queryCriteria);
    }

    default WhereSqlBuilder where(Boolean predicate, Object ...queryCriteria) {
        return new WhereSqlBuilder(predicate, precompileSql(), precompileArgs(), queryCriteria);
    }


    default WhereSqlBuilder where(String condition, Object ...params) {
        return where(Boolean.TRUE, condition, params);
    }

    default WhereSqlBuilder where(Boolean predicate, String condition, Object ...params) {
        return new WhereSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }

    default WhereSqlBuilder where(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new WhereSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }



    default WhereSqlBuilder where(String column, Operator option, Object ...values) {
        return where(Boolean.TRUE, column, option, values);
    }

    default WhereSqlBuilder where(Boolean predicate, String column, Operator option, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder where(Boolean predicate, String column, Operator option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder where(LMDFunction<P, ?> lambdaFunction, Operator option, Object ...values) {
        return where(LambdaUtils.getColumnName(lambdaFunction), option, values);
    }

    default <P>WhereSqlBuilder where(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(LambdaUtils.getColumnName(lambdaFunction), option, values);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder where(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(LambdaUtils.getColumnName(lambdaFunction), option, values.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default WhereSqlBuilder whereEq(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.EQ, value);
    }

    default WhereSqlBuilder whereEq(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereEq(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereEq(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereEq(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereEq(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereEq(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default WhereSqlBuilder whereGt(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.GT, value);
    }

    default WhereSqlBuilder whereGt(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereGt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereGt(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereGt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGt(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGt(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }




    default WhereSqlBuilder whereGe(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.GE, value);
    }

    default WhereSqlBuilder whereGe(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereGe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default <P>WhereSqlBuilder whereGe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereGe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGe(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default WhereSqlBuilder whereLt(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.LT, value);
    }

    default WhereSqlBuilder whereLt(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereLt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereLt(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereLt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLt(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLt(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default WhereSqlBuilder whereLe(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.LE, value);
    }

    default WhereSqlBuilder whereLe(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereLe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereLe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereLe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLe(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default WhereSqlBuilder whereNe(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.NE, value);
    }

    default WhereSqlBuilder whereNe(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereNe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereNe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default WhereSqlBuilder whereNe2(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.LTGT, value);
    }

    default WhereSqlBuilder whereNe2(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNe2(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereNe2(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereNe2(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe2(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe2(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereLike(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.LRLIKE, value);
    }

    default WhereSqlBuilder whereLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotLike(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.NOT_LRLIKE, value);
    }

    default WhereSqlBuilder whereNotLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereNotLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default WhereSqlBuilder whereLLike(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.LLIKE, value);
    }

    default WhereSqlBuilder whereLLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereLLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default WhereSqlBuilder whereNotLLike(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.NOT_LLIKE, value);
    }

    default WhereSqlBuilder whereNotLLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereNotLLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default WhereSqlBuilder whereRLike(String column, Object value) {
        return where(Boolean.TRUE, column, Operator.RLIKE, value);
    }

    default WhereSqlBuilder whereRLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, value);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereRLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, value.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereRLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereRLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereRLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default WhereSqlBuilder whereNotRLike(String column, Object ...values) {
        return where(Boolean.TRUE, column, Operator.NOT_RLIKE, values);
    }

    default WhereSqlBuilder whereNotRLike(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, values);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotRLike(Boolean predicate, String column, Supplier<Object> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, values.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereNotRLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return whereNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>WhereSqlBuilder whereNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotRLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default WhereSqlBuilder whereIn(String column, Object ...values) {
        return where(Boolean.TRUE, column, Operator.IN, values);
    }

    default WhereSqlBuilder whereIn(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, values);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, values.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereIn(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return whereIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>WhereSqlBuilder whereIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIn(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIn(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default WhereSqlBuilder whereNotIn(String column, Object ...values) {
        return where(Boolean.TRUE, column, Operator.NOT_IN, values);
    }

    default WhereSqlBuilder whereNotIn(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, values);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, values.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereNotIn(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return whereNotIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>WhereSqlBuilder whereNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotIn(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotIn(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default WhereSqlBuilder whereBetween(String column, Object ...values) {
        return where(Boolean.TRUE, column, Operator.BETWEEN_AND, values);
    }

    default WhereSqlBuilder whereBetween(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, values);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, value1.get(), value2.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, values.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereBetween(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return whereBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>WhereSqlBuilder whereBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaUtils.getColumnName(lambdaFunction), values);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaUtils.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default WhereSqlBuilder whereNotBetween(String column, Object ...values) {
        return where(Boolean.TRUE, column, Operator.NOT_BETWEEN_AND, values);
    }

    default WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, values);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, value1.get(), value2.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, values.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereNotBetween(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>WhereSqlBuilder whereNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>WhereSqlBuilder whereNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default WhereSqlBuilder whereNull(String column) {
        return where(Boolean.TRUE, column, Operator.IS_NULL);
    }

    default <P>WhereSqlBuilder whereNull(LMDFunction<P, ?> lambdaFunction) {
        return whereNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    default <P>WhereSqlBuilder whereNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(Boolean.TRUE, column, Operator.IS_NULL);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotNull(String column) {
        return where(Boolean.TRUE, column, Operator.NOT_NULL);
    }

    default WhereSqlBuilder whereNotNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(Boolean.TRUE, column, Operator.NOT_NULL);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>WhereSqlBuilder whereNotNull(LMDFunction<P, ?> lambdaFunction) {
        return whereNotNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    default <P>WhereSqlBuilder whereNotNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default WhereSqlBuilder whereExists(Object... builder) {
        return where(Boolean.TRUE, Constants.EMPTY_STRING, Operator.EXISTS, builder);
    }

    default WhereSqlBuilder whereExists(Boolean predicate, Object... builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, builder);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, builder);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default WhereSqlBuilder whereNotExists(Object... builder) {
        return where(Boolean.TRUE, Constants.EMPTY_STRING, Operator.NOT_EXISTS, builder);
    }

    default WhereSqlBuilder whereNotExists(Boolean predicate, Object... builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, builder);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereNotExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, builder);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }
}
