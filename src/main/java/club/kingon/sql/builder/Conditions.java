package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Operator;
import club.kingon.sql.builder.util.ConditionUtils;
import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

import java.util.function.Supplier;

/**
 * @author dragons
 * @date 2021/11/11 12:38
 */
public class Conditions {

    private Conditions() {}

    public static WhereSqlBuilder where(Object ...queryCriteria) {
        return where(Boolean.TRUE, queryCriteria);
    }

    public static WhereSqlBuilder where(Boolean predicate, Object ...queryCriteria) {
        return new WhereSqlBuilder(predicate, null, queryCriteria);
    }


    public static WhereSqlBuilder where(String condition, Object ...params) {
        return where(Boolean.TRUE, condition, params);
    }

    public static WhereSqlBuilder where(Boolean predicate, String condition, Object ...params) {
        return new WhereSqlBuilder(predicate, null, Constants.EMPTY_OBJECT_ARRAY, condition, params);
    }

    public static WhereSqlBuilder where(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new WhereSqlBuilder(predicate, null, Constants.EMPTY_OBJECT_ARRAY, condition, params);
    }




    public static WhereSqlBuilder where(String column, Operator option, Object ...params) {
        return where(Boolean.TRUE, column, option, params);
    }

    public static WhereSqlBuilder where(Boolean predicate, String column, Operator option, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, params);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder where(Boolean predicate, String column, Operator option, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, params.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder where(LMDFunction<P, ?> lambdaFunction, Operator option, Object ...params) {
        return where(LambdaUtils.getColumnName(lambdaFunction), option, params);
    }

    public static <P>WhereSqlBuilder where(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Object ...params) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(LambdaUtils.getColumnName(lambdaFunction), option, params);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder where(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return where(LambdaUtils.getColumnName(lambdaFunction), option, params.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder whereEq(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.EQ, param);
    }

    public static WhereSqlBuilder whereEq(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereEq(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereEq(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereEq(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereEq(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereEq(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static WhereSqlBuilder whereNe(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.NE, param);
    }

    public static WhereSqlBuilder whereNe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereNe(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereNe(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder whereNe2(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.LTGT, param);
    }

    public static WhereSqlBuilder whereNe2(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNe2(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereNe2(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereNe2(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe2(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNe2(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static WhereSqlBuilder whereGt(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.GT, param);
    }

    public static WhereSqlBuilder whereGt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder whereGt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereGt(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereGt(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGt(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGt(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static WhereSqlBuilder whereGe(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.GE, param);
    }

    public static WhereSqlBuilder whereGe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereGe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereGe(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereGe(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGe(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereGe(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static WhereSqlBuilder whereLt(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.LT, param);
    }

    public static WhereSqlBuilder whereLt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereLt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereLt(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereLt(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLt(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLt(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder whereLe(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.LE, param);
    }

    public static WhereSqlBuilder whereLe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereLe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereLe(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereLe(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLe(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLe(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder whereLike(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.LRLIKE, param);
    }

    public static WhereSqlBuilder whereLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static WhereSqlBuilder whereNotLike(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.NOT_LRLIKE, param);
    }

    public static WhereSqlBuilder whereNotLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereNotLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereNotLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static WhereSqlBuilder whereLLike(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.LLIKE, param);
    }

    public static WhereSqlBuilder whereLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereLLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereLLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereLLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder whereNotLLike(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.NOT_LLIKE, param);
    }

    public static WhereSqlBuilder whereNotLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereNotLLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereNotLLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotLLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static WhereSqlBuilder whereRLike(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.RLIKE, param);
    }

    public static WhereSqlBuilder whereRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereRLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereRLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereRLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereRLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder whereNotRLike(String column, Object param) {
        return where(Boolean.TRUE, column, Operator.NOT_RLIKE, param);
    }

    public static WhereSqlBuilder whereNotRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, param);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, param.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereNotRLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return whereNotRLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>WhereSqlBuilder whereNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotRLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotRLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static WhereSqlBuilder whereIn(String column, Object ...params) {
        return where(Boolean.TRUE, column, Operator.IN, params);
    }

    public static WhereSqlBuilder whereIn(Boolean predicate, String column, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, params);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, params.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereIn(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return whereIn(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>WhereSqlBuilder whereIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIn(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIn(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static WhereSqlBuilder whereNotIn(String column, Object ...params) {
        return where(Boolean.TRUE, column, Operator.NOT_IN, params);
    }

    public static WhereSqlBuilder whereNotIn(Boolean predicate, String column, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, params);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, params.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereNotIn(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return whereNotIn(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>WhereSqlBuilder whereNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotIn(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotIn(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static WhereSqlBuilder whereBetween(String column, Object... params) {
        return where(Boolean.TRUE, column, Operator.BETWEEN_AND, params);
    }

    public static WhereSqlBuilder whereBetween(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, params);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, param1.get(), param2.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, params.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereBetween(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return whereBetween(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>WhereSqlBuilder whereBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaUtils.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereBetween(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static WhereSqlBuilder whereNotBetween(String column, Object ...params) {
        return where(Boolean.TRUE, column, Operator.NOT_BETWEEN_AND, params);
    }

    public static WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, params);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, param1.get(), param2.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, params.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereNotBetween(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>WhereSqlBuilder whereNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereNotBetween(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder whereIsNull(String column) {
        return where(Boolean.TRUE, column, Operator.IS_NULL);
    }

    public static WhereSqlBuilder whereIsNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IS_NULL);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>WhereSqlBuilder whereIsNull(LMDFunction<P, ?> lambdaFunction) {
        return whereIsNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    public static <P>WhereSqlBuilder whereIsNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIsNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereIsNotNull(String column) {
        return where(Boolean.TRUE, column, Operator.NOT_NULL);
    }

    public static WhereSqlBuilder whereIsNotNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_NULL);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>WhereSqlBuilder whereIsNotNull(LMDFunction<P, ?> lambdaFunction) {
        return whereIsNotNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    public static <P>WhereSqlBuilder whereIsNotNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return whereIsNotNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static WhereSqlBuilder whereExists(Object... sqlOrBuilder) {
        return where(Boolean.TRUE, Constants.EMPTY_STRING, Operator.EXISTS, sqlOrBuilder);
    }

    public static WhereSqlBuilder whereExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, sqlOrBuilder);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, sqlOrBuilder.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static WhereSqlBuilder whereNotExists(Object... sqlOrBuilder) {
        return where(Boolean.TRUE, Constants.EMPTY_STRING, Operator.NOT_EXISTS, sqlOrBuilder);
    }

    public static WhereSqlBuilder whereNotExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, sqlOrBuilder);
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static WhereSqlBuilder whereNotExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, sqlOrBuilder.get());
            return new WhereSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new WhereSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static JoinOnSqlBuilder on(Object ...queryCriteria) {
        return on(Boolean.TRUE, queryCriteria);
    }

    public static JoinOnSqlBuilder on(Boolean predicate, Object ...queryCriteria) {
        return new JoinOnSqlBuilder(predicate, null, queryCriteria);
    }


    public static JoinOnSqlBuilder on(String condition, Object ...params) {
        return on(Boolean.TRUE, condition, params);
    }

    public static JoinOnSqlBuilder on(Boolean predicate, String condition, Object ...params) {
        return new JoinOnSqlBuilder(predicate, null, Constants.EMPTY_OBJECT_ARRAY, condition, params);
    }

    public static JoinOnSqlBuilder on(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new JoinOnSqlBuilder(predicate, null, Constants.EMPTY_OBJECT_ARRAY, condition, params);
    }




    public static JoinOnSqlBuilder on(String column, Operator option, Object ...params) {
        return on(Boolean.TRUE, column, option, params);
    }

    public static JoinOnSqlBuilder on(Boolean predicate, String column, Operator option, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, params);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static JoinOnSqlBuilder on(Boolean predicate, String column, Operator option, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, params.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder on(LMDFunction<P, ?> lambdaFunction, Operator option, Object ...params) {
        return on(LambdaUtils.getColumnName(lambdaFunction), option, params);
    }

    public static <P>JoinOnSqlBuilder on(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Object ...params) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(LambdaUtils.getColumnName(lambdaFunction), option, params);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder on(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return on(LambdaUtils.getColumnName(lambdaFunction), option, params.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static JoinOnSqlBuilder onEq(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.EQ, param);
    }

    public static JoinOnSqlBuilder onEq(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onEq(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onEq(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onEq(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onEq(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onEq(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static JoinOnSqlBuilder onNe(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.NE, param);
    }

    public static JoinOnSqlBuilder onNe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onNe(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onNe(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static JoinOnSqlBuilder onNe2(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.LTGT, param);
    }

    public static JoinOnSqlBuilder onNe2(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNe2(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onNe2(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onNe2(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe2(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNe2(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static JoinOnSqlBuilder onGt(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.GT, param);
    }

    public static JoinOnSqlBuilder onGt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static JoinOnSqlBuilder onGt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onGt(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onGt(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGt(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGt(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static JoinOnSqlBuilder onGe(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.GE, param);
    }

    public static JoinOnSqlBuilder onGe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onGe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onGe(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onGe(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGe(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onGe(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static JoinOnSqlBuilder onLt(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.LT, param);
    }

    public static JoinOnSqlBuilder onLt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onLt(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onLt(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLt(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLt(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static JoinOnSqlBuilder onLe(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.LE, param);
    }

    public static JoinOnSqlBuilder onLe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onLe(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onLe(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLe(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLe(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static JoinOnSqlBuilder onLike(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.LRLIKE, param);
    }

    public static JoinOnSqlBuilder onLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static JoinOnSqlBuilder onNotLike(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.NOT_LRLIKE, param);
    }

    public static JoinOnSqlBuilder onNotLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onNotLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onNotLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static JoinOnSqlBuilder onLLike(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.LLIKE, param);
    }

    public static JoinOnSqlBuilder onLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onLLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onLLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onLLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static JoinOnSqlBuilder onNotLLike(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.NOT_LLIKE, param);
    }

    public static JoinOnSqlBuilder onNotLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onNotLLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onNotLLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotLLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static JoinOnSqlBuilder onRLike(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.RLIKE, param);
    }

    public static JoinOnSqlBuilder onRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onRLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onRLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onRLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onRLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static JoinOnSqlBuilder onNotRLike(String column, Object param) {
        return on(Boolean.TRUE, column, Operator.NOT_RLIKE, param);
    }

    public static JoinOnSqlBuilder onNotRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, param);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, param.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onNotRLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return onNotRLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>JoinOnSqlBuilder onNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotRLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotRLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static JoinOnSqlBuilder onIn(String column, Object ...params) {
        return on(Boolean.TRUE, column, Operator.IN, params);
    }

    public static JoinOnSqlBuilder onIn(Boolean predicate, String column, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, params);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, params.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onIn(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return onIn(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>JoinOnSqlBuilder onIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIn(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIn(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static JoinOnSqlBuilder onNotIn(String column, Object ...params) {
        return on(Boolean.TRUE, column, Operator.NOT_IN, params);
    }

    public static JoinOnSqlBuilder onNotIn(Boolean predicate, String column, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, params);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, params.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onNotIn(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return onNotIn(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>JoinOnSqlBuilder onNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotIn(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotIn(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static JoinOnSqlBuilder onBetween(String column, Object... params) {
        return on(Boolean.TRUE, column, Operator.BETWEEN_AND, params);
    }

    public static JoinOnSqlBuilder onBetween(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, params);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, param1.get(), param2.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, params.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onBetween(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return onBetween(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>JoinOnSqlBuilder onBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaUtils.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onBetween(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static JoinOnSqlBuilder onNotBetween(String column, Object ...params) {
        return on(Boolean.TRUE, column, Operator.NOT_BETWEEN_AND, params);
    }

    public static JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, params);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, param1.get(), param2.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, params.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onNotBetween(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return onNotBetween(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>JoinOnSqlBuilder onNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaUtils.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return onNotBetween(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static JoinOnSqlBuilder onIsNull(String column) {
        return on(Boolean.TRUE, column, Operator.IS_NULL);
    }

    public static JoinOnSqlBuilder onIsNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IS_NULL);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>JoinOnSqlBuilder onIsNull(LMDFunction<P, ?> lambdaFunction) {
        return onIsNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    public static <P>JoinOnSqlBuilder onIsNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIsNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onIsNotNull(String column) {
        return on(Boolean.TRUE, column, Operator.NOT_NULL);
    }

    public static JoinOnSqlBuilder onIsNotNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_NULL);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>JoinOnSqlBuilder onIsNotNull(LMDFunction<P, ?> lambdaFunction) {
        return onIsNotNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    public static <P>JoinOnSqlBuilder onIsNotNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return onIsNotNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static JoinOnSqlBuilder onExists(Object... sqlOrBuilder) {
        return on(Boolean.TRUE, Constants.EMPTY_STRING, Operator.EXISTS, sqlOrBuilder);
    }

    public static JoinOnSqlBuilder onExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, sqlOrBuilder);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, sqlOrBuilder.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static JoinOnSqlBuilder onNotExists(Object... sqlOrBuilder) {
        return on(Boolean.TRUE, Constants.EMPTY_STRING, Operator.NOT_EXISTS, sqlOrBuilder);
    }

    public static JoinOnSqlBuilder onNotExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, sqlOrBuilder);
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static JoinOnSqlBuilder onNotExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, sqlOrBuilder.get());
            return new JoinOnSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new JoinOnSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static HavingSqlBuilder having(Object ...queryCriteria) {
        return having(Boolean.TRUE, queryCriteria);
    }

    public static HavingSqlBuilder having(Boolean predicate, Object ...queryCriteria) {
        return new HavingSqlBuilder(predicate, null, queryCriteria);
    }


    public static HavingSqlBuilder having(String condition, Object ...params) {
        return having(Boolean.TRUE, condition, params);
    }

    public static HavingSqlBuilder having(Boolean predicate, String condition, Object ...params) {
        return new HavingSqlBuilder(predicate, null, Constants.EMPTY_OBJECT_ARRAY, condition, params);
    }

    public static HavingSqlBuilder having(Boolean predicate, String condition, Supplier<Object[]> params) {
        return new HavingSqlBuilder(predicate, null, Constants.EMPTY_OBJECT_ARRAY, condition, params);
    }




    public static HavingSqlBuilder having(String column, Operator option, Object ...params) {
        return having(Boolean.TRUE, column, option, params);
    }

    public static HavingSqlBuilder having(Boolean predicate, String column, Operator option, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, params);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static HavingSqlBuilder having(Boolean predicate, String column, Operator option, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, params.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder having(LMDFunction<P, ?> lambdaFunction, Operator option, Object ...params) {
        return having(LambdaUtils.getColumnName(lambdaFunction), option, params);
    }

    public static <P>HavingSqlBuilder having(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Object ...params) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(LambdaUtils.getColumnName(lambdaFunction), option, params);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder having(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return having(LambdaUtils.getColumnName(lambdaFunction), option, params.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static HavingSqlBuilder havingEq(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.EQ, param);
    }

    public static HavingSqlBuilder havingEq(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingEq(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingEq(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingEq(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingEq(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingEq(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static HavingSqlBuilder havingNe(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.NE, param);
    }

    public static HavingSqlBuilder havingNe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingNe(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingNe(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static HavingSqlBuilder havingNe2(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.LTGT, param);
    }

    public static HavingSqlBuilder havingNe2(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNe2(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LTGT, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingNe2(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingNe2(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe2(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNe2(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static HavingSqlBuilder havingGt(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.GT, param);
    }

    public static HavingSqlBuilder havingGt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static HavingSqlBuilder havingGt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GT, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingGt(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingGt(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGt(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGt(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static HavingSqlBuilder havingGe(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.GE, param);
    }

    public static HavingSqlBuilder havingGe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingGe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.GE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingGe(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingGe(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGe(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingGe(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static HavingSqlBuilder havingLt(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.LT, param);
    }

    public static HavingSqlBuilder havingLt(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLt(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LT, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingLt(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingLt(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLt(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLt(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static HavingSqlBuilder havingLe(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.LE, param);
    }

    public static HavingSqlBuilder havingLe(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLe(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingLe(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingLe(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLe(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLe(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static HavingSqlBuilder havingLike(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.LRLIKE, param);
    }

    public static HavingSqlBuilder havingLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LRLIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static HavingSqlBuilder havingNotLike(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.NOT_LRLIKE, param);
    }

    public static HavingSqlBuilder havingNotLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LRLIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingNotLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingNotLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static HavingSqlBuilder havingLLike(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.LLIKE, param);
    }

    public static HavingSqlBuilder havingLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.LLIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingLLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingLLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingLLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static HavingSqlBuilder havingNotLLike(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.NOT_LLIKE, param);
    }

    public static HavingSqlBuilder havingNotLLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotLLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_LLIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingNotLLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingNotLLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotLLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static HavingSqlBuilder havingRLike(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.RLIKE, param);
    }

    public static HavingSqlBuilder havingRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.RLIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingRLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingRLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingRLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingRLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static HavingSqlBuilder havingNotRLike(String column, Object param) {
        return having(Boolean.TRUE, column, Operator.NOT_RLIKE, param);
    }

    public static HavingSqlBuilder havingNotRLike(Boolean predicate, String column, Object param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, param);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotRLike(Boolean predicate, String column, Supplier<Object> param) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_RLIKE, param.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingNotRLike(LMDFunction<P, ?> lambdaFunction, Object param) {
        return havingNotRLike(LambdaUtils.getColumnName(lambdaFunction), param);
    }

    public static <P>HavingSqlBuilder havingNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotRLike(LambdaUtils.getColumnName(lambdaFunction), param);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotRLike(LambdaUtils.getColumnName(lambdaFunction), param.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static HavingSqlBuilder havingIn(String column, Object ...params) {
        return having(Boolean.TRUE, column, Operator.IN, params);
    }

    public static HavingSqlBuilder havingIn(Boolean predicate, String column, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, params);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IN, params.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingIn(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return havingIn(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>HavingSqlBuilder havingIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIn(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIn(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static HavingSqlBuilder havingNotIn(String column, Object ...params) {
        return having(Boolean.TRUE, column, Operator.NOT_IN, params);
    }

    public static HavingSqlBuilder havingNotIn(Boolean predicate, String column, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, params);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotIn(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_IN, params.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingNotIn(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return havingNotIn(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>HavingSqlBuilder havingNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotIn(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotIn(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static HavingSqlBuilder havingBetween(String column, Object... params) {
        return having(Boolean.TRUE, column, Operator.BETWEEN_AND, params);
    }

    public static HavingSqlBuilder havingBetween(Boolean predicate, String column, Object... params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, params);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, param1.get(), param2.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.BETWEEN_AND, params.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingBetween(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return havingBetween(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>HavingSqlBuilder havingBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaUtils.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingBetween(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }




    public static HavingSqlBuilder havingNotBetween(String column, Object ...params) {
        return having(Boolean.TRUE, column, Operator.NOT_BETWEEN_AND, params);
    }

    public static HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Object ...params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, params);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Supplier<Object> param1, Supplier<Object> param2) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, param1.get(), param2.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotBetween(Boolean predicate, String column, Supplier<Object[]> params) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_BETWEEN_AND, params.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingNotBetween(LMDFunction<P, ?> lambdaFunction, Object... params) {
        return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), params);
    }

    public static <P>HavingSqlBuilder havingNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), params);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> param1, Supplier<Object> param2) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), param1.get(), param2.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> params) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingNotBetween(LambdaUtils.getColumnName(lambdaFunction), params.get());
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static HavingSqlBuilder havingIsNull(String column) {
        return having(Boolean.TRUE, column, Operator.IS_NULL);
    }

    public static HavingSqlBuilder havingIsNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.IS_NULL);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static <P>HavingSqlBuilder havingIsNull(LMDFunction<P, ?> lambdaFunction) {
        return havingIsNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    public static <P>HavingSqlBuilder havingIsNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIsNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingIsNotNull(String column) {
        return having(Boolean.TRUE, column, Operator.NOT_NULL);
    }

    public static HavingSqlBuilder havingIsNotNull(Boolean predicate, String column) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.NOT_NULL);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static <P>HavingSqlBuilder havingIsNotNull(LMDFunction<P, ?> lambdaFunction) {
        return havingIsNotNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    public static <P>HavingSqlBuilder havingIsNotNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        if (Boolean.TRUE.equals(predicate)) {
            return havingIsNotNull(LambdaUtils.getColumnName(lambdaFunction));
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }


    public static HavingSqlBuilder havingExists(Object... sqlOrBuilder) {
        return having(Boolean.TRUE, Constants.EMPTY_STRING, Operator.EXISTS, sqlOrBuilder);
    }

    public static HavingSqlBuilder havingExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, sqlOrBuilder);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.EXISTS, sqlOrBuilder.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }



    public static HavingSqlBuilder havingNotExists(Object... sqlOrBuilder) {
        return having(Boolean.TRUE, Constants.EMPTY_STRING, Operator.NOT_EXISTS, sqlOrBuilder);
    }

    public static HavingSqlBuilder havingNotExists(Boolean predicate, Object... sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, sqlOrBuilder);
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }

    public static HavingSqlBuilder havingNotExists(Boolean predicate, Supplier<Object> sqlOrBuilder) {
        if (predicate) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(null, Operator.NOT_EXISTS, sqlOrBuilder.get());
            return new HavingSqlBuilder(Boolean.TRUE, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return new HavingSqlBuilder(Boolean.FALSE, null, Constants.EMPTY_OBJECT_ARRAY);
    }
}
