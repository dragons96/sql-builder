package club.kingon.sql.builder;

import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dragons
 * @date 2021/11/10 18:22
 */
public class GroupSqlBuilder implements SqlBuilder, HavingSqlBuilderRoute, OrderSqlBuilderRoute, LimitSqlBuilderRoute, UnionSqlBuilderRoute {

    private final String prefix;

    private final List<String> columns;

    private final Object[] precompileArgs;

    protected GroupSqlBuilder(String prefix, Object[] precompileArgs, String ...columns) {
        this.prefix = prefix;
        this.columns = Arrays.stream(columns).map(SqlNameUtils::handleName).collect(Collectors.toList());
        this.precompileArgs = precompileArgs;
    }

    protected GroupSqlBuilder(String prefix, Object[] precompileArgs, SqlBuilder...columns) {
        this(prefix, precompileArgs, Arrays.stream(columns).map(s -> "(" + s.precompileSql() + ")").toArray(String[]::new));
    }

    public GroupSqlBuilder addColumn(String ...columns) {
        this.columns.addAll(Arrays.stream(columns).map(SqlNameUtils::handleName).collect(Collectors.toList()));
        return this;
    }

    public GroupSqlBuilder addColumn(SqlBuilder...columns) {
        this.columns.addAll(Arrays.stream(columns).map(s -> "(" + s.build() + ")").collect(Collectors.toList()));
        return this;
    }

    @SafeVarargs
    public final <P>GroupSqlBuilder addColumn(LMDFunction<P, ?>... lambdaFunctions) {
        this.columns.addAll(Arrays.stream(lambdaFunctions).map(LambdaUtils::getColumnName).map(SqlNameUtils::handleName).collect(Collectors.toList()));
        return this;
    }

    @Override
    public String precompileSql() {
        boolean prefixEmpty = prefix == null || "".equals(prefix), columnsEmpty = columns.isEmpty();
        if (prefixEmpty && columnsEmpty) return "";
        if (prefixEmpty) return String.join(", ", columns);
        if (columnsEmpty) return prefix;
        return prefix + " GROUP BY " + String.join(", ", columns);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }
}
