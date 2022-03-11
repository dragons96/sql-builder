package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Operator;
import club.kingon.sql.builder.util.ConditionUtils;
import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

import java.io.Serializable;
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

    default <F extends Serializable>WhereSqlBuilder where(F lambdaFunction, Operator option, Object ...values) {
        return where(LambdaUtils.getColumnName(lambdaFunction), option, values);
    }

    default <F extends Serializable>WhereSqlBuilder where(Boolean predicate, F lambdaFunction, Operator option, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(LambdaUtils.getColumnName(lambdaFunction), option, values);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder where(Boolean predicate, F lambdaFunction, Operator option, Supplier<Object[]> values) {
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

    default <F extends Serializable>WhereSqlBuilder whereEq(F lambdaFunction, Object value) {
        return whereEq(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereEq(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereEq(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereEq(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereGt(F lambdaFunction, Object value) {
        return whereGt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereGt(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGt(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereGt(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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



    default <F extends Serializable>WhereSqlBuilder whereGe(F lambdaFunction, Object value) {
        return whereGe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereGe(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereGe(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereLt(F lambdaFunction, Object value) {
        return whereLt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereLt(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLt(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereLt(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereLe(F lambdaFunction, Object value) {
        return whereLe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereLe(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereLe(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereNe(F lambdaFunction, Object value) {
        return whereNe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereNe(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereNe(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereNe2(F lambdaFunction, Object value) {
        return whereNe2(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereNe2(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe2(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereNe2(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe2(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    /**
     * use whereLike to replace it.
     */
    @Deprecated
    default WhereSqlBuilder whereLRLike(String column, Object ...values) {
        return where(Boolean.TRUE, column, Operator.LRLIKE, values);
    }

    /**
     * use whereLike to replace it.
     */
    @Deprecated
    default WhereSqlBuilder whereLRLike(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, values);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    /**
     * use whereLike to replace it.
     */
    @Deprecated
    default WhereSqlBuilder whereLRLike(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, values.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
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

    default <F extends Serializable>WhereSqlBuilder whereLike(F lambdaFunction, Object value) {
        return whereLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    /**
     * use whereNotLike to replace it.
     */
    @Deprecated
    default WhereSqlBuilder whereNotLRLike(String column, Object ...values) {
        return where(Boolean.TRUE, column, Operator.NOT_LRLIKE, values);
    }

    /**
     * use whereNotLike to replace it.
     */
    @Deprecated
    default WhereSqlBuilder whereNotLRLike(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, values);
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    /**
     * use whereNotLike to replace it.
     */
    @Deprecated
    default WhereSqlBuilder whereNotLRLike(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, values.get());
            return new WhereSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
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


    default <F extends Serializable>WhereSqlBuilder whereNotLike(F lambdaFunction, Object value) {
        return whereNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereNotLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereNotLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereLLike(F lambdaFunction, Object value) {
        return whereLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereLLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereLLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereNotLLike(F lambdaFunction, Object value) {
        return whereNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereNotLLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereNotLLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereRLike(F lambdaFunction, Object value) {
        return whereRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereRLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereRLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereRLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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

    default <F extends Serializable>WhereSqlBuilder whereNotRLike(F lambdaFunction, Object value) {
        return whereNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>WhereSqlBuilder whereNotRLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereNotRLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereIn(F lambdaFunction, Object... values) {
        return whereIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <F extends Serializable>WhereSqlBuilder whereIn(Boolean predicate, F lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIn(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereIn(Boolean predicate, F lambdaFunction, Supplier<Object[]> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereNotIn(F lambdaFunction, Object... values) {
        return whereNotIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <F extends Serializable>WhereSqlBuilder whereNotIn(Boolean predicate, F lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotIn(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereNotIn(Boolean predicate, F lambdaFunction, Supplier<Object[]> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereBetween(F lambdaFunction, Object... values) {
        return whereBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <F extends Serializable>WhereSqlBuilder whereBetween(Boolean predicate, F lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaUtils.getColumnName(lambdaFunction), values);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereBetween(Boolean predicate, F lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaUtils.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereBetween(Boolean predicate, F lambdaFunction, Supplier<Object[]> value) {
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


    default <F extends Serializable>WhereSqlBuilder whereNotBetween(F lambdaFunction, Object... values) {
        return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <F extends Serializable>WhereSqlBuilder whereNotBetween(Boolean predicate, F lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereNotBetween(Boolean predicate, F lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>WhereSqlBuilder whereNotBetween(Boolean predicate, F lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default WhereSqlBuilder whereIsNull(String column) {
        return where(Boolean.TRUE, column, Operator.IS_NULL);
    }

    default <F extends Serializable>WhereSqlBuilder whereIsNull(F lambdaFunction) {
        return whereIsNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    default <F extends Serializable>WhereSqlBuilder whereIsNull(Boolean predicate, F lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIsNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new WhereSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default WhereSqlBuilder whereIsNull(Boolean predicate, String column) {
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


    default <S extends Serializable>WhereSqlBuilder whereNotNull(S lambdaFunction) {
        return whereNotNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    default <F extends Serializable>WhereSqlBuilder whereNotNull(Boolean predicate, F lambdaFunction) {
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
