package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.util.SqlNameUtils;

/**
 * @author dragons
 * @date 2021/11/11 13:08
 */
public class UpdateSqlBuilder implements SqlBuilder, SetSqlBuilderRoute {

    private final String table;

    protected UpdateSqlBuilder(String table) {
        this.table = SqlNameUtils.handleName(table);
    }

    @Override
    public String precompileSql() {
        return "UPDATE " + table;
    }

    @Override
    public Object[] precompileArgs() {
        return Constants.EMPTY_OBJECT_ARRAY;
    }
}
