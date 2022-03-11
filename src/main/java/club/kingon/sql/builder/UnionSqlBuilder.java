package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dragons
 * @date 2021/11/10 19:45
 */
public class UnionSqlBuilder implements SqlBuilder, OrderSqlBuilderRoute, LimitSqlBuilderRoute {

    private final String prefix;

    private final List<SqlBuilder> unions;

    private final List<Object> precompileArgs = new ArrayList<>();

    protected UnionSqlBuilder(String prefix, Object[] precompileArgs, SqlBuilder...unions) {
        this.prefix = prefix;
        this.unions = new ArrayList<>(Arrays.asList(unions));
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        for (SqlBuilder union : unions) {
            this.precompileArgs.addAll(Arrays.asList(union.precompileArgs()));
        }
    }

    public UnionSqlBuilder union(SqlBuilder...unions) {
        this.unions.addAll(Arrays.asList(unions));
        for (SqlBuilder union : unions) {
            this.precompileArgs.addAll(Arrays.asList(union.precompileArgs()));
        }
        return this;
    }

    @Override
    public String precompileSql() {
        boolean prefixEmpty = prefix == null || "".equals(prefix), unionsEmpty = unions.isEmpty();
        if (prefixEmpty && unionsEmpty) return "";
        if (prefixEmpty) return unions.stream().map(PreparedStatementSupport::precompileSql).collect(Collectors.joining(" UNION "));
        if (unionsEmpty) return prefix;
        return prefix + " UNION " + unions.stream().map(PreparedStatementSupport::precompileSql).collect(Collectors.joining(" UNION "));
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs.toArray(Constants.EMPTY_OBJECT_ARRAY);
    }
}
