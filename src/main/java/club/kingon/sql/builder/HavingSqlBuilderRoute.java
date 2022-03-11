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

    default <F extends Serializable>HavingSqlBuilder having(F lambdaFunction, Operator option, Object ...values) {
        return having(LambdaUtils.getColumnName(lambdaFunction), option, values);
    }

    default <F extends Serializable>HavingSqlBuilder having(Boolean predicate, F lambdaFunction, Operator option, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(LambdaUtils.getColumnName(lambdaFunction), option, values);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder having(Boolean predicate, F lambdaFunction, Operator option, Supplier<Object[]> values) {
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

    default <F extends Serializable>HavingSqlBuilder havingEq(F lambdaFunction, Object value) {
        return havingEq(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingEq(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingEq(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingEq(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingGt(F lambdaFunction, Object value) {
        return havingGt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingGt(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGt(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingGt(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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



    default <F extends Serializable>HavingSqlBuilder havingGe(F lambdaFunction, Object value) {
        return havingGe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingGe(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingGe(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingLt(F lambdaFunction, Object value) {
        return havingLt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingLt(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLt(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingLt(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingLe(F lambdaFunction, Object value) {
        return havingLe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingLe(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingLe(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingNe(F lambdaFunction, Object value) {
        return havingNe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingNe(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingNe(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingNe2(F lambdaFunction, Object value) {
        return havingNe2(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingNe2(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe2(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingNe2(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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

    default <F extends Serializable>HavingSqlBuilder havingLike(F lambdaFunction, Object value) {
        return havingLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingNotLike(F lambdaFunction, Object value) {
        return havingNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingNotLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingNotLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingLLike(F lambdaFunction, Object value) {
        return havingLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingLLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingLLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingNotLLike(F lambdaFunction, Object value) {
        return havingNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingNotLLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingNotLLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingRLike(F lambdaFunction, Object value) {
        return havingRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingRLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingRLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingRLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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

    default <F extends Serializable>HavingSqlBuilder havingNotRLike(F lambdaFunction, Object value) {
        return havingNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    default <F extends Serializable>HavingSqlBuilder havingNotRLike(Boolean predicate, F lambdaFunction, Object value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingNotRLike(Boolean predicate, F lambdaFunction, Supplier<Object> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingIn(F lambdaFunction, Object... values) {
        return havingIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <F extends Serializable>HavingSqlBuilder havingIn(Boolean predicate, F lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIn(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingIn(Boolean predicate, F lambdaFunction, Supplier<Object[]> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingNotIn(F lambdaFunction, Object... values) {
        return havingNotIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <F extends Serializable>HavingSqlBuilder havingNotIn(Boolean predicate, F lambdaFunction, Object... value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotIn(LambdaUtils.getColumnName(lambdaFunction), value);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingNotIn(Boolean predicate, F lambdaFunction, Supplier<Object[]> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingBetween(F lambdaFunction, Object... values) {
        return havingBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <F extends Serializable>HavingSqlBuilder havingBetween(Boolean predicate, F lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaUtils.getColumnName(lambdaFunction), values);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingBetween(Boolean predicate, F lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaUtils.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingBetween(Boolean predicate, F lambdaFunction, Supplier<Object[]> value) {
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


    default <F extends Serializable>HavingSqlBuilder havingNotBetween(F lambdaFunction, Object... values) {
        return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    default <F extends Serializable>HavingSqlBuilder havingNotBetween(Boolean predicate, F lambdaFunction, Object... values) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingNotBetween(Boolean predicate, F lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), value1.get(), value2.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }

    default <F extends Serializable>HavingSqlBuilder havingNotBetween(Boolean predicate, F lambdaFunction, Supplier<Object[]> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), value.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, precompileSql(), precompileArgs());
    }



    default HavingSqlBuilder havingIsNull(String column) {
        return having(Boolean.TRUE, column, Operator.IS_NULL);
    }

    default <F extends Serializable>HavingSqlBuilder havingIsNull(F lambdaFunction) {
        return havingIsNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    default <F extends Serializable>HavingSqlBuilder havingIsNull(Boolean predicate, F lambdaFunction) {
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


    default <S extends Serializable>HavingSqlBuilder havingNotNull(S lambdaFunction) {
        return havingNotNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    default <F extends Serializable>HavingSqlBuilder havingNotNull(Boolean predicate, F lambdaFunction) {
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
