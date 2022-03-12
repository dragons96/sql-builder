package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Operator;
import club.kingon.sql.builder.util.ConditionUtils;
import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

import java.util.function.Supplier;

/**
 * @author dragons
 * @date 2021/11/10 19:24
 */
public interface JoinOnSqlBuilderRoute extends SqlBuilder {

    default JoinOnSqlBuilder on(JoinOnSqlBuilder JoinOnSqlBuilder) {
        return on(Boolean.TRUE, JoinOnSqlBuilder);
    }

    default JoinOnSqlBuilder on(Boolean predicate, JoinOnSqlBuilder JoinOnSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), JoinOnSqlBuilder);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder on(Boolean predicate, Supplier<JoinOnSqlBuilder> JoinOnSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), JoinOnSqlBuilder.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder on(Object ...queryCriteria) {
        return on(Boolean.TRUE, queryCriteria);
    }

    default JoinOnSqlBuilder on(Boolean predicate, Object ...queryCriteria) {
        return new JoinOnSqlBuilder(predicate, precompileSql(), precompileArgs(), queryCriteria);
    }


    default JoinOnSqlBuilder on(String condition, Object ...params) {
        return on(Boolean.TRUE, condition, params);
    }

    default JoinOnSqlBuilder on(Boolean predicate, String condition, Object ...params) {
        return new JoinOnSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }

    default JoinOnSqlBuilder on(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new JoinOnSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }



    default JoinOnSqlBuilder on(String column, Operator option, Object ...values) {
        return on(Boolean.TRUE, column, option, values);
    }

    default JoinOnSqlBuilder on(Boolean predicate, String column, Operator option, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder on(Boolean predicate, String column, Operator option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder on(LMDFunction<P, ?> lambdaFunction, Operator option, Object ...values) {
        return on(LambdaUtils.getColumnName(lambdaFunction), option, values);
    }

    default <P>JoinOnSqlBuilder on(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(LambdaUtils.getColumnName(lambdaFunction), option, values);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder on(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(LambdaUtils.getColumnName(lambdaFunction), option, values.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onEq(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.EQ, value);
    }

    default JoinOnSqlBuilder onEq(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onEq(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onEq(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onEq(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onEq(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onEq(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onGt(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.GT, value);
    }

    default JoinOnSqlBuilder onGt(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onGt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onGt(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onGt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGt(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGt(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }




    default JoinOnSqlBuilder onGe(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.GE, value);
    }

    default JoinOnSqlBuilder onGe(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onGe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default <P>JoinOnSqlBuilder onGe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onGe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGe(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default JoinOnSqlBuilder onLt(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.LT, value);
    }

    default JoinOnSqlBuilder onLt(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onLt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onLt(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onLt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLt(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLt(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default JoinOnSqlBuilder onLe(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.LE, value);
    }

    default JoinOnSqlBuilder onLe(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onLe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onLe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onLe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLe(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default JoinOnSqlBuilder onNe(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.NE, value);
    }

    default JoinOnSqlBuilder onNe(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onNe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onNe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default JoinOnSqlBuilder onNe2(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.LTGT, value);
    }

    default JoinOnSqlBuilder onNe2(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNe2(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onNe2(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onNe2(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe2(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe2(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    /**
     * use onLike to replace it.
     */
    @Deprecated
    default JoinOnSqlBuilder onLRLike(String column, Object ...values) {
        return on(Boolean.TRUE, column, Operator.LRLIKE, values);
    }

    /**
     * use onLike to replace it.
     */
    @Deprecated
    default JoinOnSqlBuilder onLRLike(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, values);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    /**
     * use onLike to replace it.
     */
    @Deprecated
    default JoinOnSqlBuilder onLRLike(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, values.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onLike(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.LRLIKE, value);
    }

    default JoinOnSqlBuilder onLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    /**
     * use onNotLike to replace it.
     */
    @Deprecated
    default JoinOnSqlBuilder onNotLRLike(String column, Object ...values) {
        return on(Boolean.TRUE, column, Operator.NOT_LRLIKE, values);
    }

    /**
     * use onNotLike to replace it.
     */
    @Deprecated
    default JoinOnSqlBuilder onNotLRLike(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, values);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    /**
     * use onNotLike to replace it.
     */
    @Deprecated
    default JoinOnSqlBuilder onNotLRLike(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, values.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onNotLike(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.NOT_LRLIKE, value);
    }

    default JoinOnSqlBuilder onNotLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onNotLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default JoinOnSqlBuilder onLLike(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.LLIKE, value);
    }

    default JoinOnSqlBuilder onLLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onLLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onNotLLike(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.NOT_LLIKE, value);
    }

    default JoinOnSqlBuilder onNotLLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onNotLLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onRLike(String column, Object value) {
        return on(Boolean.TRUE, column, Operator.RLIKE, value);
    }

    default JoinOnSqlBuilder onRLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, value);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onRLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, value.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onRLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onRLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onRLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onNotRLike(String column, Object ...values) {
        return on(Boolean.TRUE, column, Operator.NOT_RLIKE, values);
    }

    default JoinOnSqlBuilder onNotRLike(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, values);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotRLike(Boolean predicate, String column, Supplier<Object> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, values.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onNotRLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return onNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>JoinOnSqlBuilder onNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotRLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onIn(String column, Object ...values) {
        return on(Boolean.TRUE, column, Operator.IN, values);
    }

    default JoinOnSqlBuilder onIn(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, values);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, values.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onIn(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return onIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>JoinOnSqlBuilder onIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIn(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIn(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default JoinOnSqlBuilder onNotIn(String column, Object ...values) {
        return on(Boolean.TRUE, column, Operator.NOT_IN, values);
    }

    default JoinOnSqlBuilder onNotIn(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, values);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, values.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onNotIn(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return onNotIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>JoinOnSqlBuilder onNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotIn(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotIn(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onBetween(String column, Object ...values) {
        return on(Boolean.TRUE, column, Operator.BETWEEN_AND, values);
    }

    default JoinOnSqlBuilder onBetween(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, values);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, value1.get(), value2.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, values.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onBetween(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return onBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>JoinOnSqlBuilder onBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaUtils.getColumnName(lambdaFunction), values);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaUtils.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onNotBetween(String column, Object ...values) {
        return on(Boolean.TRUE, column, Operator.NOT_BETWEEN_AND, values);
    }

    default JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, values);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, value1.get(), value2.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, values.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onNotBetween(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return onNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>JoinOnSqlBuilder onNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaUtils.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>JoinOnSqlBuilder onNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default JoinOnSqlBuilder onIsNull(String column) {
        return on(Boolean.TRUE, column, Operator.IS_NULL);
    }

    default <P>JoinOnSqlBuilder onIsNull(LMDFunction<P, ?> lambdaFunction) {
        return onIsNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    default <P>JoinOnSqlBuilder onIsNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIsNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onIsNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(Boolean.TRUE, column, Operator.IS_NULL);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotNull(String column) {
        return on(Boolean.TRUE, column, Operator.NOT_NULL);
    }

    default JoinOnSqlBuilder onNotNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(Boolean.TRUE, column, Operator.NOT_NULL);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>JoinOnSqlBuilder onNotNull(LMDFunction<P, ?> lambdaFunction) {
        return onNotNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    default <P>JoinOnSqlBuilder onNotNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onExists(Object... builder) {
        return on(Boolean.TRUE, Constants.EMPTY_STRING, Operator.EXISTS, builder);
    }

    default JoinOnSqlBuilder onExists(Boolean predicate, Object... builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, builder);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, builder);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default JoinOnSqlBuilder onNotExists(Object... builder) {
        return on(Boolean.TRUE, Constants.EMPTY_STRING, Operator.NOT_EXISTS, builder);
    }

    default JoinOnSqlBuilder onNotExists(Boolean predicate, Object... builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, builder);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default JoinOnSqlBuilder onNotExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, builder);
            return new JoinOnSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }
}
