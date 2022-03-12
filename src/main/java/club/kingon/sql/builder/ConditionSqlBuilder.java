package club.kingon.sql.builder;

import club.kingon.sql.builder.annotation.Queries;
import club.kingon.sql.builder.annotation.Query;
import club.kingon.sql.builder.config.ConditionPriority;
import club.kingon.sql.builder.config.GlobalConfig;
import club.kingon.sql.builder.entry.Alias;
import club.kingon.sql.builder.entry.Column;
import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Operator;
import club.kingon.sql.builder.inner.ObjectMapperUtils;
import club.kingon.sql.builder.util.CastUtils;
import club.kingon.sql.builder.util.ConditionUtils;
import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Core of condition SQL builder, contains 'where', '(join) on', 'having'
 * @author dragons
 * @date 2021/11/11 19:42
 */
public class ConditionSqlBuilder<T extends ConditionSqlBuilder> implements SqlBuilder {

    private final Logger log = LoggerFactory.getLogger(getClass());

    protected final String prefix;

    protected final StringBuilder conditionBuilder;
    /**
     * Condition type.
     */
    protected String sign = "WHERE";

    protected List<Object> precompileArgs = new ArrayList<>();
    /**
     * Brackets level.
     */
    protected int level = 2;

    protected ConditionSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, T b) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        if (Boolean.TRUE.equals(predicate)) {
            if (b != null) {
                this.conditionBuilder = b.conditionBuilder;
                this.sign = b.sign;
                this.precompileArgs.addAll(b.precompileArgs);
                this.level = b.level;
            } else {
                this.conditionBuilder = new StringBuilder();
            }
        } else {
            this.conditionBuilder = new StringBuilder();
        }
    }

    protected ConditionSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs) {
        this.prefix = prefix;
        this.conditionBuilder = new StringBuilder();
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
    }

    protected ConditionSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Object ...values) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        if (Boolean.TRUE.equals(predicate)) {
            this.conditionBuilder = new StringBuilder(condition);
            if (GlobalConfig.CONDITION_PRIORITY == ConditionPriority.LEFT_TO_RIGHT) {
                if (condition.contains("OR")) {
                    level = 1;
                } else if (condition.contains("AND")) {
                    level = 2;
                }
            }
            this.precompileArgs.addAll(Arrays.asList(values));
        } else {
            this.conditionBuilder = new StringBuilder();
        }
    }

    protected ConditionSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Supplier<Object[]> supplier) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        if (Boolean.TRUE.equals(predicate)) {
            this.conditionBuilder = new StringBuilder(condition);
            if (GlobalConfig.CONDITION_PRIORITY == ConditionPriority.LEFT_TO_RIGHT) {
                if (condition.contains("OR")) {
                    level = 1;
                } else if (condition.contains("AND")) {
                    level = 2;
                }
            }
            this.precompileArgs.addAll(Arrays.asList(supplier.get()));
        } else {
            this.conditionBuilder = new StringBuilder();
        }
    }

    protected ConditionSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, Object ...queryCriteria) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        if (Boolean.TRUE.equals(predicate)) {
            T builder = conditionQueryCriteria(queryCriteria);
            if (builder != null) {
                this.conditionBuilder = builder.conditionBuilder;
                this.sign = builder.sign;
                this.precompileArgs.addAll(builder.precompileArgs);
                this.level = builder.level;
            } else {
                this.conditionBuilder = new StringBuilder();
            }
        } else {
            this.conditionBuilder = new StringBuilder();
        }
    }

    public T and(String condition, Object ...values) {
        if (condition != null && condition.length() > 0) {
            if (conditionBuilder.length() != 0) {
                condition = " AND " + condition;
            }
            addCondition(condition, 2);
            if (values.length > 0) {
                precompileArgs.addAll(Arrays.stream(values).map(e -> {
                    if (e instanceof SqlBuilder) {
                        // deal sql as a column
                        return Column.as("(" + ((SqlBuilder) e).build() + ")");
                    }
                    return e;
                }).collect(Collectors.toList()));
            }
        }
        return (T) this;
    }

    private T and(String condition, List<Object> values) {
        if (condition != null && condition.length() > 0) {
            if (conditionBuilder.length() != 0) {
                condition = " AND " + condition;
            }
            addCondition(condition, 2);
            if (values != null && !values.isEmpty()) {
                precompileArgs.addAll(values.stream().map(e -> {
                    if (e instanceof SqlBuilder) {
                        // deal sql as a column
                        return Column.as("(" + ((SqlBuilder) e).build() + ")");
                    }
                    return e;
                }).collect(Collectors.toList()));
            }
        }
        return (T) this;
    }

    public <P>T and(LMDFunction<P, ?> lambdaFunction, Operator option, Object ...values) {
        return and(LambdaUtils.getColumnName(lambdaFunction), option, values);
    }

    public T and(String column, Operator option, Object ...values) {
        Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values);
        return and(pt._1, pt._2);
    }

    public T and(T wrapper) {
        if (wrapper != null) {
            return (T) and("(" + wrapper.conditionBuilder.toString() + ")", wrapper.precompileArgs);
        }
        return (T) this;
    }

    public T and(Object ...queryCriteria) {
        if (queryCriteria.length > 0) {
            return and(conditionQueryCriteria(queryCriteria));
        }
        return (T)this;
    }

    public <P>T and(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Object ...values) {
        return and(predicate, LambdaUtils.getColumnName(lambdaFunction), option, values);
    }

    public T and(Boolean predicate, String column, Operator option, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values);
            return and(pt._1, pt._2);
        }
        return (T) this;
    }

    public <P>T and(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Supplier<Object[]> values) {
        return and(predicate, LambdaUtils.getColumnName(lambdaFunction), option, values);
    }

    public T and(Boolean predicate, String column, Operator option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values.get());
            return and(pt._1, pt._2);
        }
        return (T) this;
    }

    public T and(Boolean predicate, String condition, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(condition, values);
        }
        return (T) this;
    }

    public T and(Boolean predicate, Supplier<String> condition, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(condition.get(), values);
        }
        return (T) this;
    }

    public T and(Boolean predicate, String condition, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(condition, values.get());
        }
        return (T) this;
    }

    public T and(Boolean predicate, Supplier<String> condition, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(condition.get(), values.get());
        }
        return (T) this;
    }

    public T and(Boolean predicate, T wrapper) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(wrapper);
        }
        return (T) this;
    }

    public T and(Boolean predicate, Supplier<T> wrapper) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(wrapper.get());
        }
        return (T) this;
    }

    public T and(Boolean predicate, Object ...queryCriteria) {
        if (predicate) {
            return and(queryCriteria);
        }
        return (T) this;
    }

    public T or(String condition, Object ...values) {
        if (condition != null && condition.length() > 0) {
            if (conditionBuilder.length() != 0) {
                condition = " OR " + condition;
            }
            addCondition(condition, 1);
            if (values.length > 0) {
                precompileArgs.addAll(Arrays.stream(values).map(e -> {
                    if (e instanceof SqlBuilder) {
                        // deal sql as a column
                        return Column.as("(" + ((SqlBuilder) e).build() + ")");
                    }
                    return e;
                }).collect(Collectors.toList()));
            }
        }
        return (T) this;
    }

    private T or(String condition, List<Object> values) {
        if (condition != null && condition.length() > 0) {
            if (conditionBuilder.length() != 0) {
                condition = " OR " + condition;
            }
            addCondition(condition, 1);
            if (values != null && !values.isEmpty()) {
                precompileArgs.addAll(values.stream().map(e -> {
                    if (e instanceof SqlBuilder) {
                        // deal sql as a column
                        return Column.as("(" + ((SqlBuilder) e).build() + ")");
                    }
                    return e;
                }).collect(Collectors.toList()));
            }
        }
        return (T) this;
    }

    public T or(String column, Operator option, Object ...values) {
        Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values);
        return or(pt._1, pt._2);
    }

    public <P>T or(LMDFunction<P, ?> lambdaFunction, Operator option, Object ...values) {
        return or(LambdaUtils.getColumnName(lambdaFunction), option, values);
    }

    @Deprecated
    public T or(String column, Operator option, Supplier<Object[]> values) {
        Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values.get());
        return or(pt._1, pt._2);
    }

    public T or(T wrapper) {
        if (wrapper != null) {
            return (T) or("(" + wrapper.conditionBuilder.toString() + ")", wrapper.precompileArgs);
        }
        return (T) this;
    }

    public T or(Object ...queryCriteria) {
        if (queryCriteria.length > 0) {
            return or(conditionQueryCriteria(queryCriteria));
        }
        return (T) this;
    }

    public T or(Boolean predicate, String column, Operator option, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values);
            return or(pt._1, pt._2);
        }
        return (T) this;
    }

    public <P>T or(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Object ...values) {
        return or(predicate, LambdaUtils.getColumnName(lambdaFunction), option, values);
    }

    public T or(Boolean predicate, String column, Operator option, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), option, values.get());
            return or(pt._1, pt._2);
        }
        return (T) this;
    }

    public <P>T or(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Operator option, Supplier<Object[]> values) {
        return or(predicate, LambdaUtils.getColumnName(lambdaFunction), option, values);
    }

    public T or(Boolean predicate, String condition, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(condition, values);
        }
        return (T) this;
    }

    public T or(Boolean predicate, Supplier<String> condition, Object ...values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(condition.get(), values);
        }
        return (T) this;
    }

    public T or(Boolean predicate, String condition, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(condition, values.get());
        }
        return (T) this;
    }

    public T or(Boolean predicate, Supplier<String> condition, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(condition.get(), values.get());
        }
        return (T) this;
    }

    public T or(Boolean predicate, T wrapper) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(wrapper);
        }
        return (T) this;
    }

    public T or(Boolean predicate, Supplier<T> wrapper) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(wrapper.get());
        }
        return (T) this;
    }

    public T or(Boolean predicate, Object ...queryCriteria) {
        if (predicate) {
            return or(queryCriteria);
        }
        return (T) this;
    }

    public T andEq(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.EQ, value.get());
        }
        return (T) this;
    }

    public <P>T andEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andEq(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andEq(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.EQ, value);
    }

    public <P>T andEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andEq(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }


    public T andEq(String column, Object value) {
        return and(column, Operator.EQ, value);
    }

    public <P> T andEq(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andEq(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andExists(Boolean predicate, Object builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(Constants.EMPTY_STRING, Operator.EXISTS, new Object[]{builder});
        }
        return (T) this;
    }

    public T andExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(Constants.EMPTY_STRING, Operator.EXISTS, new Object[]{builder.get()});
        }
        return (T) this;
    }

    public T andExists(Object builder) {
        return and(Constants.EMPTY_STRING, Operator.EXISTS, new Object[]{builder});
    }

    public T andNotExists(Boolean predicate, Object builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(Constants.EMPTY_STRING, Operator.NOT_EXISTS, new Object[]{builder});
        }
        return (T) this;
    }

    public T andNotExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(Constants.EMPTY_STRING, Operator.NOT_EXISTS, new Object[]{builder.get()});
        }
        return (T) this;
    }

    public T andNotExists(Object builder) {
        return and(Constants.EMPTY_STRING, Operator.NOT_EXISTS, new Object[]{builder});
    }

    public T andGt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.GT, value.get());
        }
        return (T) this;
    }

    public <P>T andGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andGt(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andGt(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.GT, value);
    }

    public <P>T andGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andGt(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andGt(String column, Object value) {
        return and(column, Operator.GT, value);
    }

    public <P>T andGt(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andGt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andGe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.GE, value.get());
        }
        return (T) this;
    }

    public <P>T andGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andGe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andGe(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.GE, value);
    }

    public <P>T andGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andGe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andGe(String column, Object value) {
        return and(column, Operator.GE, value);
    }

    public <P>T andGe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andGe(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andLt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.LT, value.get());
        }
        return (T) this;
    }

    public T andLt(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.LT, value);
    }

    public T andLt(String column, Object value) {
        return and(column, Operator.LT, value);
    }


    public <P>T andLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andLt(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andLt(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andLt(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andLt(LambdaUtils.getColumnName(lambdaFunction), value);
    }




    public T andLe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.LE, value.get());
        }
        return (T) this;
    }

    public T andLe(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.LE, value);
    }

    public T andLe(String column, Object value) {
        return and(column, Operator.LE, value);
    }



    public <P>T andLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andLe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andLe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andLe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andLe(LambdaUtils.getColumnName(lambdaFunction), value);
    }




    public T andNe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.NE, value.get());
        }
        return (T) this;
    }

    public T andNe(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.NE, value);
    }

    public T andNe(String column, Object value) {
        return and(column, Operator.NE, value);
    }

    public <P>T andNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andNe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andNe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andNe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andNe(LambdaUtils.getColumnName(lambdaFunction), value);
    }


    public T andNe2(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.LTGT, value.get());
        }
        return (T) this;
    }

    public T andNe2(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.LTGT, value);
    }

    public T andNe2(String column, Object value) {
        return and(column, Operator.LTGT, value);
    }


    public <P>T andNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andNe2(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andNe2(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andNe2(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andNe2(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.LLIKE, value.get());
        }
        return (T) this;
    }

    public T andLLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.LLIKE, value);
    }

    public T andLLike(String column, Object value) {
        return and(column, Operator.LLIKE, value);
    }


    public <P>T andLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andLLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andLLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andLLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }



    public T andNotLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.NOT_LLIKE, value.get());
        }
        return (T) this;
    }

    public T andNotLLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.NOT_LLIKE, value);
    }

    public T andNotLLike(String column, Object value) {
        return and(column, Operator.NOT_LLIKE, value);
    }


    public <P>T andNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andNotLLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andNotLLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andNotLLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }


    public T andRLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.RLIKE, value.get());
        }
        return (T) this;
    }

    public T andRLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.RLIKE, value);
    }

    public T andRLike(String column, Object value) {
        return and(column, Operator.RLIKE, value);
    }


    public <P>T andRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andRLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andRLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andRLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }



    public T andNotRLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.NOT_RLIKE, value.get());
        }
        return (T) this;
    }

    public T andNotRLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.NOT_RLIKE, value);
    }

    public T andNotRLike(String column, Object value) {
        return and(column, Operator.NOT_RLIKE, value);
    }

    public <P>T andNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andNotRLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andNotRLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andNotRLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.LRLIKE, value.get());
        }
        return (T) this;
    }

    public T andLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.LRLIKE, value);
    }

    public T andLike(String column, Object value) {
        return and(column, Operator.LRLIKE, value);
    }

    public <P>T andLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andNotLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.NOT_LRLIKE, value.get());
        }
        return (T) this;
    }

    public T andNotLike(Boolean predicate, String column, Object value) {
        return and(predicate, column, Operator.NOT_LRLIKE, value);
    }

    public T andNotLike(String column, Object value) {
        return and(column, Operator.NOT_LRLIKE, value);
    }


    public <P>T andNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return andNotLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return andNotLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T andNotLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return andNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T andIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.IN, values.get());
        }
        return (T) this;
    }

    public T andIn(Boolean predicate, String column, Object... values) {
        return and(predicate, column, Operator.IN, values);
    }

    public T andIn(String column, Object... values) {
        return and(column, Operator.IN, values);
    }



    public <P>T andIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return andIn(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T andIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        return andIn(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T andIn(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return andIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }




    public T andNotIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.NOT_IN, values.get());
        }
        return (T) this;
    }

    public T andNotIn(Boolean predicate, String column, Object... values) {
        return and(predicate, column, Operator.NOT_IN, values);
    }

    public T andNotIn(String column, Object... values) {
        return and(column, Operator.NOT_IN, values);
    }


    public <P>T andNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return andNotIn(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T andNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        return andNotIn(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T andNotIn(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return andNotIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }



    public T andBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.BETWEEN_AND, values.get());
        }
        return (T) this;
    }

    public T andBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.BETWEEN_AND, value1.get(), value2.get());
        }
        return (T) this;
    }

    public T andBetween(Boolean predicate, String column, Object... values) {
        return and(predicate, column, Operator.BETWEEN_AND, values);
    }

    public T andBetween(Boolean predicate, String column, Object value1, Object value2) {
        return and(predicate, column, Operator.BETWEEN_AND, value1, value2);
    }

    public T andBetween(String column, Object... values) {
        return and(column, Operator.BETWEEN_AND, values);
    }

    public T andBetween(String column, Object value1, Object value2) {
        return and(column, Operator.BETWEEN_AND, value1, value2);
    }


    public <P>T andBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return andBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T andBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        return andBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }

    public <P>T andBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        return andBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T andBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return andBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }

    public <P>T andBetween(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return andBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T andBetween(LMDFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return andBetween(LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }




    public T andNotBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.NOT_BETWEEN_AND, values.get());
        }
        return (T) this;
    }

    public T andNotBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return and(column, Operator.NOT_BETWEEN_AND, value1.get(), value2.get());
        }
        return (T) this;
    }

    public T andNotBetween(Boolean predicate, String column, Object... values) {
        return and(predicate, column, Operator.NOT_BETWEEN_AND, values);
    }

    public T andNotBetween(Boolean predicate, String column, Object value1, Object value2) {
        return and(predicate, column, Operator.NOT_BETWEEN_AND, value1, value2);
    }

    public T andNotBetween(String column, Object... values) {
        return and(column, Operator.NOT_BETWEEN_AND, values);
    }

    public T andNotBetween(String column, Object value1, Object value2) {
        return and(column, Operator.NOT_BETWEEN_AND, value1, value2);
    }



    public <P>T andNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return andNotBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T andNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        return andNotBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }

    public <P>T andNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        return andNotBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T andNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return andNotBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }

    public <P>T andNotBetween(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return andNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T andNotBetween(LMDFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return andNotBetween(LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }



    public T andIsNull(Boolean predicate, String column) {
        return and(predicate, column, Operator.IS_NULL);
    }

    public T andIsNull(String column) {
        return and(column, Operator.IS_NULL);
    }

    public <P>T andIsNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        return andIsNull(predicate, LambdaUtils.getColumnName(lambdaFunction));
    }

    public <P>T andIsNull(LMDFunction<P, ?> lambdaFunction) {
        return andIsNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    public T andNotNull(Boolean predicate, String column) {
        return and(predicate, column, Operator.NOT_NULL);
    }

    public T andNotNull(String column) {
        return and(column, Operator.NOT_NULL);
    }

    public <P>T andNotNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        return andNotNull(predicate, LambdaUtils.getColumnName(lambdaFunction));
    }

    public <P>T andNotNull(LMDFunction<P, ?> lambdaFunction) {
        return andNotNull(LambdaUtils.getColumnName(lambdaFunction));
    }


    public T orEq(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.EQ, value.get());
        }
        return (T) this;
    }

    public T orEq(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.EQ, value);
    }

    public T orEq(String column, Object value) {
        return or(column, Operator.EQ, value);
    }


    public <P>T orEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orEq(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orEq(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orEq(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orEq(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orEq(LambdaUtils.getColumnName(lambdaFunction), value);
    }



    public T orExists(Boolean predicate, Object builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(Constants.EMPTY_STRING, Operator.EXISTS, new Object[]{builder});
        }
        return (T) this;
    }

    public T orExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(Constants.EMPTY_STRING, Operator.EXISTS, new Object[]{builder.get()});
        }
        return (T) this;
    }

    public T orExists(Object builder) {
        return or(Constants.EMPTY_STRING, Operator.EXISTS, new Object[]{builder});
    }

    public T orNotExists(Boolean predicate, Object builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(Constants.EMPTY_STRING, Operator.NOT_EXISTS, new Object[]{builder});
        }
        return (T) this;
    }

    public T orNotExists(Boolean predicate, Supplier<Object> builder) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(Constants.EMPTY_STRING, Operator.NOT_EXISTS, new Object[]{builder.get()});
        }
        return (T) this;
    }

    public T orNotExists(Object builder) {
        return or(Constants.EMPTY_STRING, Operator.NOT_EXISTS, new Object[]{builder});
    }

    public T orGt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.GT, value.get());
        }
        return (T) this;
    }

    public T orGt(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.GT, value);
    }

    public T orGt(String column, Object value) {
        return or(column, Operator.GT, value);
    }

    public <P>T orGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orGt(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orGt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orGt(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orGt(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orGt(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T orGe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.GE, value.get());
        }
        return (T) this;
    }

    public T orGe(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.GE, value);
    }

    public T orGe(String column, Object value) {
        return or(column, Operator.GE, value);
    }

    public <P>T orGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orGe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orGe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orGe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orGe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orGe(LambdaUtils.getColumnName(lambdaFunction), value);
    }



    public T orLt(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.LT, value.get());
        }
        return (T) this;
    }

    public T orLt(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.LT, value);
    }

    public T orLt(String column, Object value) {
        return or(column, Operator.LT, value);
    }


    public <P>T orLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orLt(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orLt(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orLt(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orLt(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orLt(LambdaUtils.getColumnName(lambdaFunction), value);
    }


    public T orLe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.LE, value.get());
        }
        return (T) this;
    }

    public T orLe(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.LE, value);
    }

    public T orLe(String column, Object value) {
        return or(column, Operator.LE, value);
    }


    public <P>T orLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orLe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orLe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orLe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orLe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orLe(LambdaUtils.getColumnName(lambdaFunction), value);
    }




    public T orNe(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.NE, value.get());
        }
        return (T) this;
    }

    public T orNe(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.NE, value);
    }

    public T orNe(String column, Object value) {
        return or(column, Operator.NE, value);
    }


    public <P>T orNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orNe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orNe(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orNe(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orNe(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orNe(LambdaUtils.getColumnName(lambdaFunction), value);
    }



    public T orNe2(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.LTGT, value.get());
        }
        return (T) this;
    }

    public T orNe2(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.LTGT, value);
    }

    public T orNe2(String column, Object value) {
        return or(column, Operator.LTGT, value);
    }


    public <P>T orNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orNe2(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orNe2(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orNe2(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orNe2(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orNe2(LambdaUtils.getColumnName(lambdaFunction), value);
    }


    public T orLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.LLIKE, value.get());
        }
        return (T) this;
    }

    public T orLLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.LLIKE, value);
    }

    public T orLLike(String column, Object value) {
        return or(column, Operator.LLIKE, value);
    }


    public <P>T orLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orLLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orLLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orLLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public T orNotLLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.NOT_LLIKE, value.get());
        }
        return (T) this;
    }

    public T orNotLLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.NOT_LLIKE, value);
    }

    public T orNotLLike(String column, Object value) {
        return or(column, Operator.NOT_LLIKE, value);
    }


    public <P>T orNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orNotLLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orNotLLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orNotLLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orNotLLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orNotLLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }


    public T orRLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.RLIKE, value.get());
        }
        return (T) this;
    }


    public T orRLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.RLIKE, value);
    }

    public T orRLike(String column, Object value) {
        return or(column, Operator.RLIKE, value);
    }


    public <P>T orRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orRLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orRLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orRLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }



    public T orNotRLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.NOT_RLIKE, value.get());
        }
        return (T) this;
    }

    public T orNotRLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.NOT_RLIKE, value);
    }

    public T orNotRLike(String column, Object value) {
        return or(column, Operator.NOT_RLIKE, value);
    }


    public <P>T orNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orNotRLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orNotRLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orNotRLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orNotRLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orNotRLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }


    public T orLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.LRLIKE, value.get());
        }
        return (T) this;
    }

    public T orLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.LRLIKE, value);
    }

    public T orLike(String column, Object value) {
        return or(column, Operator.LRLIKE, value);
    }


    public <P>T orLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }


    public T orNotLike(Boolean predicate, String column, Supplier<Object> value) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.NOT_LRLIKE, value.get());
        }
        return (T) this;
    }

    public T orNotLike(Boolean predicate, String column, Object value) {
        return or(predicate, column, Operator.NOT_LRLIKE, value);
    }

    public T orNotLike(String column, Object value) {
        return or(column, Operator.NOT_LRLIKE, value);
    }


    public <P>T orNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value) {
        return orNotLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orNotLike(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value) {
        return orNotLike(predicate, LambdaUtils.getColumnName(lambdaFunction), value);
    }

    public <P>T orNotLike(LMDFunction<P, ?> lambdaFunction, Object value) {
        return orNotLike(LambdaUtils.getColumnName(lambdaFunction), value);
    }


    public T orIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.IN, values.get());
        }
        return (T) this;
    }

    public T orIn(Boolean predicate, String column, Object... values) {
        return or(predicate, column, Operator.IN, values);
    }

    public T orIn(String column, Object... values) {
        return or(column, Operator.IN, values);
    }


    public <P>T orIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return orIn(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T orIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        return orIn(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T orIn(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return orIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }


    public T orNotIn(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.NOT_IN, values.get());
        }
        return (T) this;
    }

    public T orNotIn(Boolean predicate, String column, Object... values) {
        return or(predicate, column, Operator.NOT_IN, values);
    }

    public T orNotIn(String column, Object... values) {
        return or(column, Operator.NOT_IN, values);
    }


    public <P>T orNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return orNotIn(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T orNotIn(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        return orNotIn(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T orNotIn(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return orNotIn(LambdaUtils.getColumnName(lambdaFunction), values);
    }


    public T orBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.BETWEEN_AND, values.get());
        }
        return (T) this;
    }

    public T orBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.BETWEEN_AND, value1.get(), value2.get());
        }
        return (T) this;
    }

    public T orBetween(Boolean predicate, String column, Object... values) {
        return or(predicate, column, Operator.BETWEEN_AND, values);
    }

    public T orBetween(Boolean predicate, String column, Object value1, Object value2) {
        return or(predicate, column, Operator.BETWEEN_AND, value1, value2);
    }

    public T orBetween(String column, Object... values) {
        return or(column, Operator.BETWEEN_AND, values);
    }

    public T orBetween(String column, Object value1, Object value2) {
        return or(column, Operator.BETWEEN_AND, value1, value2);
    }



    public <P>T orBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return orBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T orBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        return orBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }

    public <P>T orBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        return orBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T orBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return orBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }

    public <P>T orBetween(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return orBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T orBetween(LMDFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return orBetween(LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }




    public T orNotBetween(Boolean predicate, String column, Supplier<Object[]> values) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.NOT_BETWEEN_AND, values.get());
        }
        return (T) this;
    }

    public T orNotBetween(Boolean predicate, String column, Supplier<Object> value1, Supplier<Object> value2) {
        if (Boolean.TRUE.equals(predicate)) {
            return or(column, Operator.NOT_BETWEEN_AND, value1.get(), value2.get());
        }
        return (T) this;
    }

    public T orNotBetween(Boolean predicate, String column, Object... values) {
        return or(predicate, column, Operator.NOT_BETWEEN_AND, values);
    }

    public T orNotBetween(Boolean predicate, String column, Object value1, Object value2) {
        return or(predicate, column, Operator.NOT_BETWEEN_AND, value1, value2);
    }

    public T orNotBetween(String column, Object... values) {
        return or(column, Operator.NOT_BETWEEN_AND, values);
    }

    public T orNotBetween(String column, Object value1, Object value2) {
        return or(column, Operator.NOT_BETWEEN_AND, value1, value2);
    }


    public <P>T orNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object[]> values) {
        return orNotBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T orNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Supplier<Object> value1, Supplier<Object> value2) {
        return orNotBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }

    public <P>T orNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object... values) {
        return orNotBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T orNotBetween(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return orNotBetween(predicate, LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }

    public <P>T orNotBetween(LMDFunction<P, ?> lambdaFunction, Object... values) {
        return orNotBetween(LambdaUtils.getColumnName(lambdaFunction), values);
    }

    public <P>T orNotBetween(LMDFunction<P, ?> lambdaFunction, Object value1, Object value2) {
        return orNotBetween(LambdaUtils.getColumnName(lambdaFunction), value1, value2);
    }



    public T orIsNull(Boolean predicate, String column) {
        return or(predicate, column, Operator.IS_NULL);
    }

    public T orIsNull(String column) {
        return or(column, Operator.IS_NULL);
    }

    public <P>T orIsNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        return orIsNull(predicate, LambdaUtils.getColumnName(lambdaFunction));
    }

    public <P>T orIsNull(LMDFunction<P, ?> lambdaFunction) {
        return orIsNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    public T orNotNull(Boolean predicate, String column) {
        return or(predicate, column, Operator.NOT_NULL);
    }

    public T orNotNull(String column) {
        return or(column, Operator.NOT_NULL);
    }

    public <P>T orNotNull(Boolean predicate, LMDFunction<P, ?> lambdaFunction) {
        return orNotNull(predicate, LambdaUtils.getColumnName(lambdaFunction));
    }

    public <P>T orNotNull(LMDFunction<P, ?> lambdaFunction) {
        return orNotNull(LambdaUtils.getColumnName(lambdaFunction));
    }

    private T conditionQueryCriteria(Object ...queryCriteria) {
        T conditionSqlBuilder = null;
        for (Object criteria : queryCriteria) {
            if (criteria instanceof Map) {
                Map<?, ?> criteriaMap = (Map<?, ?>) criteria;
                for (Map.Entry<?, ?> entry : criteriaMap.entrySet()) {
                    try {
                        if (entry.getValue() instanceof Collection || (entry.getValue() != null && entry.getValue().getClass().isArray())) {
                            conditionSqlBuilder = handleQueryConditionBuilder(conditionSqlBuilder, String.valueOf(entry.getKey()), Operator.IN, entry.getValue());
                        } else if (entry.getValue() == null) {
                            conditionSqlBuilder = handleQueryConditionBuilder(conditionSqlBuilder, String.valueOf(entry.getKey()), Operator.IS_NULL, Constants.EMPTY_OBJECT_ARRAY);
                        } else {
                            conditionSqlBuilder = handleQueryConditionBuilder(conditionSqlBuilder, String.valueOf(entry.getKey()), Operator.EQ, entry.getValue());
                        }
                    } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
                        log.warn("The conditional model " + criteria.getClass().getName() + " field " + entry.getKey() + " handles an exception.");
                        throw new SqlBuilderException("The conditional model " + criteria.getClass().getName() + " field " + entry.getKey() + " handles an exception.", e);
                    }
                }
            } else {
                Class<?> criteriaClass = criteria.getClass();
                List<Alias> fields = ObjectMapperUtils.getColumnFields(criteriaClass);
                for (Alias alias : fields) {
                    try {
                        Field field = ObjectMapperUtils.getField(criteriaClass, alias.getAlias());
                        Object value = ObjectMapperUtils.getFieldValue(criteria, alias.getAlias());
                        // It should not have a sql segment when collection is empty.
                        if (field != null && value != null && (!(value instanceof Collection) || !((Collection<?>) value).isEmpty())) {
                            Queries queriesAnnotation = field.getAnnotation(Queries.class);
                            Query queryAnnotation;
                            Query[] queries = null;
                            if (queriesAnnotation != null && queriesAnnotation.value().length > 0) {
                                queries = queriesAnnotation.value();
                            } else if ((queryAnnotation = field.getAnnotation(Query.class)) != null) {
                                queries = new Query[]{queryAnnotation};
                            }
                            if (queries != null) {
                                for (Query query : queries) {
                                    if (query != null) {
                                        Object queryValue = handleQuery(query, alias, criteria, value);
                                        if (queryValue != null) {
                                            Object queryColumn = "".equals(query.value()) ? alias.getOrigin() : handleQueryColumn(query.value(), value);
                                            // batch handle
                                            if (queryColumn instanceof Collection && queryValue instanceof Collection && ((Collection<?>) queryColumn).size() == ((Collection<?>) queryValue).size()) {
                                                Iterator<?> queryColumnIterator = ((Collection<?>) queryColumn).iterator(), queryValueIterator = ((Collection<?>) queryValue).iterator();
                                                while (queryColumnIterator.hasNext() && queryValueIterator.hasNext()) {
                                                    conditionSqlBuilder = handleQueryConditionBuilder(conditionSqlBuilder, String.valueOf(queryColumnIterator.next()), query.type(), queryValueIterator.next());
                                                }
                                            } else {
                                                if (queryColumn instanceof Collection && !((Collection<?>) queryColumn).isEmpty()) {
                                                    queryColumn = ((Collection<?>) queryColumn).iterator().next();
                                                }
                                                conditionSqlBuilder = handleQueryConditionBuilder(conditionSqlBuilder, String.valueOf(queryColumn), query.type(), queryValue);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
                        log.warn("The conditional model " + criteria.getClass().getName() + " field " + alias.getAlias() + " handles an exception.");
                        throw new SqlBuilderException("The conditional model " + criteria.getClass().getName() + " field " + alias.getAlias() + " handles an exception.", e);
                    }
                }
            }
        }
        return conditionSqlBuilder;
    }

    private Object handleQuery(Query query, Alias alias, Object criteria, Object fieldValue) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object value = fieldValue;
        Class<?> mapFunClass = query.mapFun();
        String attr = query.attr();
        if (mapFunClass != Void.class) {
            Object mapFun = ObjectMapperUtils.getSingleObject(mapFunClass);
            if (mapFun instanceof Function) {
                value = ((Function) mapFun).apply(fieldValue);
            } else if (mapFun instanceof BiFunction) {
                value = ((BiFunction) mapFun).apply(criteria, fieldValue);
            } else {
                throw new SqlBuilderException("field \"" + alias.getAlias() + "\" mapFun \"" + mapFunClass.getName() +"\" does not implement the java.util.Function interface");
            }
        }
        else if (!"".equals(attr)) {
            value = ObjectMapperUtils.getAttr(fieldValue, attr.split("\\."), 0);
        }
        else if (query.openBooleanToConst()) {
            // Open "bool to const" mode to handle const value rather than bool value. Therefore, value should be reinitialized to null.
            value = null;
            if (query.trueToConstType() != Void.class && Boolean.TRUE.equals(fieldValue)) {
                value = CastUtils.strTo(query.trueToConst(), query.trueToConstType());
            } else if (query.falseToConstType() != Void.class && Boolean.FALSE.equals(fieldValue)) {
                value = CastUtils.strTo(query.falseToConst(), query.falseToConstType());
            }
        }
        if (value instanceof Collection && ((Collection<?>) value).isEmpty()) {
            // Empty Collection should return null.
            value = null;
        }
        return value;
    }

    private Object handleQueryColumn(String queryColumn, Object fieldValue) throws InvocationTargetException, IllegalAccessException {
        if (queryColumn.startsWith("${") && queryColumn.endsWith("}") && queryColumn.length() > 3) {
            String[] queryColumnFieldNames = queryColumn.substring(2, queryColumn.length() - 1).split("\\.");
            return ObjectMapperUtils.getAttr(fieldValue, queryColumnFieldNames, 0);
        }
        return queryColumn;
    }

    private T handleQueryConditionBuilder(T conditionSqlBuilder, String columnName, Operator option, Object queryValue) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (conditionSqlBuilder == null) {
            Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(columnName), option, queryValue);
            return (T) ObjectMapperUtils.getInstance(getClass(), Constants.CONDITION_CONSTRUCTOR_PARAMETER_TYPES,
                true, null, Constants.EMPTY_OBJECT_ARRAY, pt._1, pt._2);
        }
        return  (T) conditionSqlBuilder.and(columnName, option, queryValue);
    }

    private void addCondition(String condition, int level) {
        if (GlobalConfig.CONDITION_PRIORITY == ConditionPriority.LEFT_TO_RIGHT && conditionBuilder.length() > 0 && this.level < level) {
            conditionBuilder.insert(0, "(").append(")").append(condition);
        } else {
            conditionBuilder.append(condition);
        }
        this.level = level;
    }

    @Override
    public String precompileSql() {
        boolean prefixEmpty = prefix == null || "".equals(prefix), conditionEmpty = conditionBuilder.length() == 0;
        if (prefixEmpty && conditionEmpty) return "";
        if (prefixEmpty) return conditionBuilder.toString();
        if (conditionEmpty) return prefix;
        return prefix + " " + sign + " " + conditionBuilder;
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs.toArray(Constants.EMPTY_OBJECT_ARRAY);
    }
}
