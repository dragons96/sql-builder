package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dragons
 * @date 2021/11/9 20:14
 */
public class LimitSqlBuilder implements SqlBuilder, UnionSqlBuilderRoute {

    private final String prefix;

    private final String limitSql;

    private final List<Object> precompileArgs = new ArrayList<>();

    protected LimitSqlBuilder(String prefix, Object[] precompileArgs) {
        this.prefix = prefix;
        this.limitSql = null;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
    }

    protected LimitSqlBuilder(String prefix, Object[] precompileArgs, int count) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        this.precompileArgs.add(count);
        limitSql = "?";
    }

    protected LimitSqlBuilder(String prefix, Object[] precompileArgs, int offset, int count) {
        this.prefix = prefix;
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
        this.precompileArgs.add(offset);
        this.precompileArgs.add(count);
        limitSql = "?, ?";
    }

    @Override
    public String precompileSql() {
        if (prefix == null || "".equals(prefix)) return limitSql;
        if (limitSql == null || "".equals(limitSql)) return prefix;
        return prefix + " LIMIT " + limitSql;
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs.toArray(Constants.EMPTY_OBJECT_ARRAY);
    }
}
