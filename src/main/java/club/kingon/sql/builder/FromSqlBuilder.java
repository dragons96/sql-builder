package club.kingon.sql.builder;


import club.kingon.sql.builder.entry.Alias;
import club.kingon.sql.builder.inner.ObjectMapperUtils;
import club.kingon.sql.builder.util.SqlNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dragons
 * @date 2021/11/9 18:30
 */
public class FromSqlBuilder implements SqlBuilder, WhereSqlBuilderRoute, JoinSqlBuilderRoute, LimitSqlBuilderRoute,
    OrderSqlBuilderRoute, UnionSqlBuilderRoute, GroupSqlBuilderRoute {

    private final static Logger log = LoggerFactory.getLogger(FromSqlBuilder.class);

    private final String prefix;

    private final List<String> tables;

    private final Object[] precompileArgs;

    protected FromSqlBuilder(String prefix, Object[] precompileArgs, String ...tables) {
        this.prefix = prefix;
        this.tables = Arrays.stream(tables).map(SqlNameUtils::handleName).collect(Collectors.toList());
        this.precompileArgs = precompileArgs;
    }

    protected FromSqlBuilder(String prefix, Object[] precompileArgs, Alias ...tables) {
        this.prefix = prefix;
        this.tables = new ArrayList<>();
        this.precompileArgs = precompileArgs;
        addTable(tables);
    }

    protected FromSqlBuilder(String prefix, Object[] precompileArgs, Object ...tables) {
        this.prefix = prefix;
        this.tables = new ArrayList<>();
        this.precompileArgs = precompileArgs;
        Arrays.stream(tables).forEach(e -> {
            if (e instanceof CharSequence) {
                addTable(e.toString());
            } else if (e instanceof Alias) {
                addTable((Alias) e);
            } else if (e instanceof Class) {
                this.tables.add(SqlNameUtils.handleName(ObjectMapperUtils.getTableName((Class<?>) e)));
            } else {
                log.warn("Table type " + e.getClass().getName() + " is an unrecognized type in from sql, ignore.");
            }
        });
    }

    public FromSqlBuilder addTable(String ...tables) {
        this.tables.addAll(Arrays.stream(tables).map(SqlNameUtils::handleName).collect(Collectors.toList()));
        return this;
    }

    public FromSqlBuilder addTable(Alias ...tables) {
        this.tables.addAll(Arrays.stream(tables).map(SqlNameUtils::handleName).collect(Collectors.toList()));
        return this;
    }

    @Override
    public String precompileSql() {
        if (tables.size() == 0) return prefix;
        return prefix + " FROM " + String.join(", ", tables);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }
}
