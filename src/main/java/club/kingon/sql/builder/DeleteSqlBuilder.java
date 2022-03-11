package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.util.SqlNameUtils;

/**
 * @author dragons
 * @date 2021/11/11 20:19
 */
public class DeleteSqlBuilder implements SqlBuilder, WhereSqlBuilderRoute, FromSqlBuilderRoute {

    private final String table;

    protected DeleteSqlBuilder(String table) {
        this.table = SqlNameUtils.handleName(table);
    }

    @Override
    public String precompileSql() {
        return "DELETE FROM " + table;
    }

    @Override
    public Object[] precompileArgs() {
        return Constants.EMPTY_OBJECT_ARRAY;
    }
}
