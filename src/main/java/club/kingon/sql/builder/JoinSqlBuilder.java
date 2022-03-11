package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Alias;
import club.kingon.sql.builder.inner.ObjectMapperUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

/**
 * @author dragons
 * @date 2021/11/10 10:11
 */
public class JoinSqlBuilder implements SqlBuilder, JoinOnSqlBuilderRoute {

    private final String prefix;

    private final String joinType;

    private final String table;

    private final Object[] precompileArgs;

    protected JoinSqlBuilder(String prefix, Object[] precompileArgs, String joinType, Object table) {
        this.prefix = prefix;
        this.precompileArgs = precompileArgs;
        this.joinType = joinType;
        if (table instanceof CharSequence) {
            this.table = SqlNameUtils.handleName(table.toString());
        } else if (table instanceof Alias) {
            this.table = SqlNameUtils.handleName((Alias) table);
        } else if (table instanceof Class) {
            this.table = SqlNameUtils.handleName(ObjectMapperUtils.getTableName((Class<?>) table));
        } else {
            throw new RuntimeException("Join table type " + table.getClass().getName() + " is an unrecognized type in from sql.");
        }
    }

    @Override
    public String precompileSql() {
        return prefix + " " + joinType + " " + table;
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }
}
