package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.InsertMode;
import club.kingon.sql.builder.util.SqlNameUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dragons
 * @date 2021/11/11 11:27
 */
public class InsertSqlBuilder implements SqlBuilder, ValueSqlBuilderRoute, ValuesSqlBuilderRoute, SelectSqlBuilderRoute {

    private final String table;

    private final InsertMode mode;

    private final List<String> columns;

    protected InsertSqlBuilder(String table, InsertMode mode, String ...columns) {
        this.table = SqlNameUtils.handleName(table);
        this.mode = mode;
        this.columns = Arrays.stream(columns).map(SqlNameUtils::handleName).collect(Collectors.toList());
    }

    public InsertSqlBuilder addColumn(String ...columns) {
        this.columns.addAll(Arrays.stream(columns).map(SqlNameUtils::handleName).collect(Collectors.toList()));
        return this;
    }

    @Override
    public String precompileSql() {
        if (columns.isEmpty()) return mode.getPrefix() + " " + table;
        return mode.getPrefix() + " " + table + "(" + String.join(", ", columns)  + ")";
    }

    @Override
    public Object[] precompileArgs() {
        return Constants.EMPTY_OBJECT_ARRAY;
    }
}
