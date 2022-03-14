package club.kingon.sql.builder;


import club.kingon.sql.builder.enums.Order;
import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dragons
 * @date 2021/11/9 20:05
 */
public class OrderSqlBuilder implements SqlBuilder, LimitSqlBuilderRoute, UnionSqlBuilderRoute {

    private final String prefix;

    private final List<String> sorts;

    private boolean passPredicate;

    private Object[] precompileArgs;

    protected OrderSqlBuilder(String prefix, Object[] precompileArgs) {
        this.prefix = prefix;
        this.sorts = new ArrayList<>();
        this.passPredicate = true;
        this.precompileArgs = precompileArgs;
    }

    protected OrderSqlBuilder(String prefix, Object[] precompileArgs, String sort) {
        this(prefix, precompileArgs);
        addSort(sort);
    }

    protected OrderSqlBuilder(String prefix, Object[] precompileArgs, String column, Order order) {
        this(prefix, precompileArgs);
        addSort(column, order);
    }

    @SafeVarargs
    public final <P>OrderSqlBuilder addAsc(LMDFunction<P, ?>... lambdaFunctions) {
        return addAsc(Arrays.stream(lambdaFunctions).map(LambdaUtils::getColumnName).toArray(String[]::new));
    }

    public <P>OrderSqlBuilder addAsc(Boolean predicate, LMDFunction<P, ?>... lambdaFunctions) {
        if (prefix == null) passPredicate &= predicate;
        if (Boolean.TRUE.equals(predicate)) {
            return addAsc(lambdaFunctions);
        }
        return this;
    }

    @SafeVarargs
    public final <P>OrderSqlBuilder addDesc(LMDFunction<P, ?>... lambdaFunctions) {
        return addDesc(Arrays.stream(lambdaFunctions).map(LambdaUtils::getColumnName).toArray(String[]::new));
    }

    public <P>OrderSqlBuilder addDesc(Boolean predicate, LMDFunction<P, ?>... lambdaFunctions) {
        if (prefix == null) passPredicate &= predicate;
        if (Boolean.TRUE.equals(predicate)) {
            return addDesc(lambdaFunctions);
        }
        return this;
    }

    public OrderSqlBuilder addAsc(String ...columns) {
        return addAsc(Boolean.TRUE, columns);
    }

    public OrderSqlBuilder addAsc(Boolean predicate, String ...columns) {
        if (prefix == null) passPredicate &= predicate;
        if (Boolean.TRUE.equals(predicate) && passPredicate && columns.length > 0) {
            for (String column : columns) {
                addSort(column, Order.ASC);
            }
        }
        return this;
    }

    public OrderSqlBuilder addDesc(String ...columns) {
        return addDesc(Boolean.TRUE, columns);
    }

    public OrderSqlBuilder addDesc(Boolean predicate, String ...columns) {
        if (prefix == null) passPredicate &= predicate;
        if (Boolean.TRUE.equals(predicate) && passPredicate && columns.length > 0) {
            for (String column : columns) {
                addSort(column, Order.DESC);
            }
        }
        return this;
    }

    public <P>OrderSqlBuilder addSort(LMDFunction<P, ?> lambdaFunction, Order order) {
        return addSort(LambdaUtils.getColumnName(lambdaFunction), order);
    }

    public <P>OrderSqlBuilder addSort(Boolean predicate, LMDFunction<P, ?> lambdaFunction, Order order) {
        if (prefix == null) passPredicate &= predicate;
        if (Boolean.TRUE.equals(predicate)) {
            return addSort(lambdaFunction, order);
        }
        return this;
    }

    public OrderSqlBuilder addSort(String column, Order order) {
        return addSort(Boolean.TRUE, column, order);
    }

    public OrderSqlBuilder addSort(Boolean predicate, String column, Order order) {
        if (prefix == null) passPredicate &= predicate;
        if (Boolean.TRUE.equals(predicate) && passPredicate) {
            sorts.add(SqlNameUtils.handleName(column) + " " + order.toString());
        }
        return this;
    }

    public OrderSqlBuilder addSort(String sort) {
        return addSort(Boolean.TRUE, sort);
    }

    public OrderSqlBuilder addSort(Boolean predicate, String sort) {
        if (prefix == null) passPredicate &= predicate;
        if (Boolean.TRUE.equals(predicate) && passPredicate) {
            sorts.add(sort);
        }
        return this;
    }

    public OrderSqlBuilder addSort(OrderSqlBuilder wrapper) {
        return addSort(Boolean.TRUE, wrapper);
    }

    public OrderSqlBuilder addSort(Boolean predicate, OrderSqlBuilder wrapper) {
        if (prefix == null) passPredicate &= predicate;
        if (Boolean.TRUE.equals(predicate) && passPredicate && wrapper != null && !wrapper.sorts.isEmpty()) {
            this.sorts.addAll(wrapper.sorts);
        }
        return this;
    }

    @Override
    public String precompileSql() {
        if (prefix == null || "".equals(prefix)) return String.join(", ", sorts);
        if (sorts.isEmpty()) return prefix;
        return prefix + " ORDER BY " + String.join(", ", sorts);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }
}
