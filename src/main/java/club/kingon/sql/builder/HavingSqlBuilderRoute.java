package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Operator;
import club.kingon.sql.builder.util.ConditionUtils;
import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

import java.util.function.Supplier;

/**
 * @author dragons
 * @date 2021/11/10 19:36
 */
public interface HavingSqlBuilderRoute extends SqlBuilder {

    default HavingSqlBuilder having(HavingSqlBuilder HavingSqlBuilder) {
        return having(Boolean.TRUE, HavingSqlBuilder);
    }

    default HavingSqlBuilder having(Boolean predicate, HavingSqlBuilder HavingSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), HavingSqlBuilder);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder having(Boolean predicate, Supplier<HavingSqlBuilder> HavingSqlBuilder) {
        if (Boolean.TRUE.equals(predicate)) {
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), HavingSqlBuilder.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder having(Object ...queryCriteria) {
        return having(Boolean.TRUE, queryCriteria);
    }

    default HavingSqlBuilder having(Boolean predicate, Object ...queryCriteria) {
        return new HavingSqlBuilder(predicate, precompileSql(), precompileArgs(), queryCriteria);
    }


    default HavingSqlBuilder having(String condition, Object ...params) {
        return having(Boolean.TRUE, condition, params);
    }

    default HavingSqlBuilder having(Boolean predicate, String condition, Object ...params) {
        return new HavingSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }

    default HavingSqlBuilder having(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new HavingSqlBuilder(predicate, precompileSql(), precompileArgs(), condition, params);
    }



    default HavingSqlBuilder having(String column, Operator option, Object ...values) {
        return having(Boolean.TRUE, column, option, values);
    }

    default HavingSqlBuilder having(Boolean predicate, String column, Operator option, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder having(Boolean predicate, String column, Operator option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder having(LMDFunction<P, ?> lambdaFunction, Operator option, Object ...values) {
        return having(LambdaUtils.getColumnName(lambdaFunction), option, values);
    }

    default <P>HavingSqlBuilder having(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(LambdaUtils.getColumnName(lambdaFunction), option, values);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder having(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(LambdaUtils.getColumnName(lambdaFunction), option, values.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingEq(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.EQ, value);
    }

    default HavingSqlBuilder havingEq(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingEq(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingEq(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingEq(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingEq(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingEq(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingGt(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.GT, value);
    }

    default HavingSqlBuilder havingGt(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingGt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingGt(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingGt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGt(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGt(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }




    default HavingSqlBuilder havingGe(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.GE, value);
    }

    default HavingSqlBuilder havingGe(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingGe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default <P>HavingSqlBuilder havingGe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingGe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGe(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default HavingSqlBuilder havingLt(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.LT, value);
    }

    default HavingSqlBuilder havingLt(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingLt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingLt(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingLt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLt(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLt(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default HavingSqlBuilder havingLe(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.LE, value);
    }

    default HavingSqlBuilder havingLe(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingLe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingLe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingLe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLe(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default HavingSqlBuilder havingNe(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.NE, value);
    }

    default HavingSqlBuilder havingNe(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingNe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingNe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default HavingSqlBuilder havingNe2(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.LTGT, value);
    }

    default HavingSqlBuilder havingNe2(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNe2(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingNe2(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingNe2(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe2(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe2(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    /**
     * use havingLike to replace it.
     */
    @Deprecated
    default HavingSqlBuilder havingLRLike(String column, Object ...values) {
        return having(Boolean.TRUE, column, Operator.LRLIKE, values);
    }

    /**
     * use havingLike to replace it.
     */
    @Deprecated
    default HavingSqlBuilder havingLRLike(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, values);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    /**
     * use havingLike to replace it.
     */
    @Deprecated
    default HavingSqlBuilder havingLRLike(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, values.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingLike(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.LRLIKE, value);
    }

    default HavingSqlBuilder havingLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    /**
     * use havingNotLike to replace it.
     */
    @Deprecated
    default HavingSqlBuilder havingNotLRLike(String column, Object ...values) {
        return having(Boolean.TRUE, column, Operator.NOT_LRLIKE, values);
    }

    /**
     * use havingNotLike to replace it.
     */
    @Deprecated
    default HavingSqlBuilder havingNotLRLike(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, values);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    /**
     * use havingNotLike to replace it.
     */
    @Deprecated
    default HavingSqlBuilder havingNotLRLike(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, values.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingNotLike(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.NOT_LRLIKE, value);
    }

    default HavingSqlBuilder havingNotLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingNotLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default HavingSqlBuilder havingLLike(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.LLIKE, value);
    }

    default HavingSqlBuilder havingLLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingLLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingNotLLike(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.NOT_LLIKE, value);
    }

    default HavingSqlBuilder havingNotLLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingNotLLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingRLike(String column, Object value) {
        return having(Boolean.TRUE, column, Operator.RLIKE, value);
    }

    default HavingSqlBuilder havingRLike(Boolean predicate, String column, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, value);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingRLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, value.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingRLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingRLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingRLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingNotRLike(String column, Object ...values) {
        return having(Boolean.TRUE, column, Operator.NOT_RLIKE, values);
    }

    default HavingSqlBuilder havingNotRLike(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, values);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotRLike(Boolean predicate, String column, Supplier<Object> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, values.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingNotRLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return havingNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <P>HavingSqlBuilder havingNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotRLike(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingIn(String column, Object ...values) {
        return having(Boolean.TRUE, column, Operator.IN, values);
    }

    default HavingSqlBuilder havingIn(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, values);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, values.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingIn(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return havingIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>HavingSqlBuilder havingIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIn(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIn(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default HavingSqlBuilder havingNotIn(String column, Object ...values) {
        return having(Boolean.TRUE, column, Operator.NOT_IN, values);
    }

    default HavingSqlBuilder havingNotIn(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, values);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, values.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingNotIn(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return havingNotIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>HavingSqlBuilder havingNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotIn(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotIn(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingBetween(String column, Object ...values) {
        return having(Boolean.TRUE, column, Operator.BETWEEN_AND, values);
    }

    default HavingSqlBuilder havingBetween(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, values);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, value1.get(), value2.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, values.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingBetween(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return havingBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>HavingSqlBuilder havingBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaUtils.getColumnName(lambdaFunction), values);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaUtils.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingNotBetween(String column, Object ...values) {
        return having(Boolean.TRUE, column, Operator.NOT_BETWEEN_AND, values);
    }

    default HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, values);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, value1.get(), value2.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, values.get());
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingNotBetween(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <P>HavingSqlBuilder havingNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <P>HavingSqlBuilder havingNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default HavingSqlBuilder havingIsNull(String column) {
        return having(Boolean.TRUE, column, Operator.IS_NULL);
    }

    default <P>HavingSqlBuilder havingIsNull(LMDFunction<P, ?> lambdaFunction) {
        return havingIsNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    default <P>HavingSqlBuilder havingIsNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIsNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingIsNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(Boolean.TRUE, column, Operator.IS_NULL);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotNull(String column) {
        return having(Boolean.TRUE, column, Operator.NOT_NULL);
    }

    default HavingSqlBuilder havingNotNull(Boolean predicate, String column) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(Boolean.TRUE, column, Operator.NOT_NULL);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default <P>HavingSqlBuilder havingNotNull(LMDFunction<P, ?> lambdaFunction) {
        return havingNotNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    default <P>HavingSqlBuilder havingNotNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingExists(Object... builder) {
        return having(Boolean.TRUE, Constants.EMPTY_STRING, Operator.EXISTS, builder);
    }

    default HavingSqlBuilder havingExists(Boolean predicate, Object... builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, builder);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, builder);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }


    default HavingSqlBuilder havingNotExists(Object... builder) {
        return having(Boolean.TRUE, Constants.EMPTY_STRING, Operator.NOT_EXISTS, builder);
    }

    default HavingSqlBuilder havingNotExists(Boolean predicate, Object... builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, builder);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default HavingSqlBuilder havingNotExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, builder);
            return new HavingSqlBuilder(Boolean.TRUE, precompileSql(), precompileArgs(), pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }
}
