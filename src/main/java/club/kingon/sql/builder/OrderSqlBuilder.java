package club.kingon.sql.builder;


import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Order;
import club.kingon.sql.builder.util.SqlNameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

    @Deprecated
    protected OrderSqlBuilder(Supplier<Boolean> predicate, String prefix, Object[] precompileArgs, String sort) {
        this(prefix, precompileArgs);
        addSort(predicate, sort);
    }

    @Deprecated
    protected OrderSqlBuilder(Supplier<Boolean> predicate, String prefix, Object[] precompileArgs, String column, Order order) {
        this(prefix, precompileArgs);
        addSort(predicate, column, order);
    }

    public OrderSqlBuilder addAsc(String ...columns) {
        return addAsc(Constants.TRUE_PREDICATE, columns);
    }

    public OrderSqlBuilder addAsc(boolean predicate, String ...columns) {
        return addAsc(predicate ? Constants.TRUE_PREDICATE : Constants.FALSE_PREDICATE, columns);
    }

    @Deprecated
    public OrderSqlBuilder addAsc(Supplier<Boolean> predicate, String ...columns) {
        if (prefix == null) passPredicate &= predicate.get();
        if (predicate.get() && passPredicate && columns.length > 0) {
            for (String column : columns) {
                addSort(column, Order.ASC);
            }
        }
        return this;
    }

    public OrderSqlBuilder addDesc(String ...columns) {
        return addDesc(Constants.TRUE_PREDICATE, columns);
    }

    public OrderSqlBuilder addDesc(boolean predicate, String ...columns) {
        return addDesc(predicate ? Constants.TRUE_PREDICATE : Constants.FALSE_PREDICATE, columns);
    }

    @Deprecated
    public OrderSqlBuilder addDesc(Supplier<Boolean> predicate, String ...columns) {
        if (prefix == null) passPredicate &= predicate.get();
        if (predicate.get() && passPredicate && columns.length > 0) {
            for (String column : columns) {
                addSort(column, Order.DESC);
            }
        }
        return this;
    }

    public OrderSqlBuilder addSort(String column, Order order) {
        return addSort(true, column, order);
    }

    public OrderSqlBuilder addSort(boolean predicate, String column, Order order) {
        if (prefix == null) passPredicate &= predicate;
        if (predicate && passPredicate) {
            sorts.add(SqlNameUtils.handleName(column) + " " + order.toString());
        }
        return this;
    }

    @Deprecated
    public OrderSqlBuilder addSort(Supplier<Boolean> predicate, String column, Order order) {
        if (prefix == null) passPredicate &= predicate.get();
        if (predicate.get() && passPredicate) {
            sorts.add(SqlNameUtils.handleName(column) + " " + order.toString());
        }
        return this;
    }

    public OrderSqlBuilder addSort(String sort) {
        return addSort(true, sort);
    }

    public OrderSqlBuilder addSort(boolean predicate, String sort) {
        if (prefix == null) passPredicate &= predicate;
        if (predicate && passPredicate) {
            sorts.add(sort);
        }
        return this;
    }

    @Deprecated
    public OrderSqlBuilder addSort(Supplier<Boolean> predicate, String sort) {
        if (prefix == null) passPredicate &= predicate.get();
        if (predicate.get() && passPredicate) {
            sorts.add(sort);
        }
        return this;
    }

    public OrderSqlBuilder addSort(OrderSqlBuilder wrapper) {
        return addSort(Constants.TRUE_PREDICATE, wrapper);
    }

    public OrderSqlBuilder addSort(boolean predicate, OrderSqlBuilder wrapper) {
        return addSort(predicate ? Constants.TRUE_PREDICATE : Constants.FALSE_PREDICATE, wrapper);
    }

    public OrderSqlBuilder addSort(Supplier<Boolean> predicate, OrderSqlBuilder wrapper) {
        if (prefix == null) passPredicate &= predicate.get();
        if (predicate.get() && passPredicate && !wrapper.sorts.isEmpty()) {
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
