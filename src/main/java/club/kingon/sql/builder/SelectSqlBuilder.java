package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Alias;
import club.kingon.sql.builder.inner.ObjectMapperUtils;
import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dragons
 * @date 2021/11/9 18:28
 */
public class SelectSqlBuilder implements SqlBuilder, FromSqlBuilderRoute, OrderSqlBuilderRoute, LimitSqlBuilderRoute, UnionSqlBuilderRoute {

    private final static Logger log = LoggerFactory.getLogger(SelectSqlBuilder.class);

    private final String prefix;

    private final Object[] precompileArgs;

    private final List<String> columns;

    protected SelectSqlBuilder(String prefix, Object[] precompileArgs, String ...columns) {
        this.prefix = prefix;
        this.precompileArgs = precompileArgs;
        this.columns = Arrays.stream(columns).map(SqlNameUtils::handleName).collect(Collectors.toList());
    }

    protected SelectSqlBuilder(String prefix, Object[] precompileArgs, Alias ...columns) {
        this.prefix = prefix;
        this.precompileArgs = precompileArgs;
        this.columns = new ArrayList<>(columns.length);
        addColumn(columns);
    }

    protected <P>SelectSqlBuilder(String prefix, Object[] precompileArgs, LMDFunction<P, ?>... lambdaFunctions) {
        this.prefix = prefix;
        this.precompileArgs = precompileArgs;
        this.columns = new ArrayList<>(lambdaFunctions.length);
        addColumn(lambdaFunctions);
    }

    protected SelectSqlBuilder(String prefix, Object[] precompileArgs, Object ...columns) {
        this.prefix = prefix;
        this.precompileArgs = precompileArgs;
        this.columns = new ArrayList<>(columns.length);
        Arrays.stream(columns).forEach(e -> {
            if (e instanceof CharSequence) {
                addColumn(e.toString());
            } else if (e instanceof Alias) {
                addColumn((Alias) e);
            } else if (e instanceof Class) {
                this.columns.addAll(ObjectMapperUtils.getColumns((Class<?>) e).stream().map(SqlNameUtils::handleName).collect(Collectors.toList()));
            } else if (e instanceof LMDFunction) {
                 this.columns.add(SqlNameUtils.handleName(LambdaUtils.getColumnName((LMDFunction) e)));
            } else {
                log.warn("Column type " + e.getClass().getName() + " is an unrecognized type in from sql, ignore.");
            }
        });
    }


    public SelectSqlBuilder addColumn(String ...columns) {
        this.columns.addAll(Arrays.stream(columns).map(SqlNameUtils::handleName).collect(Collectors.toList()));
        return this;
    }

    public SelectSqlBuilder addColumn(Alias ...columns) {
        this.columns.addAll(Arrays.stream(columns).map(SqlNameUtils::handleName).collect(Collectors.toList()));
        return this;
    }

    public <P>SelectSqlBuilder addColumn(LMDFunction<P, ?>... lambdaFunctions) {
        this.columns.addAll(Arrays.stream(lambdaFunctions).map(LambdaUtils::getColumnName).map(SqlNameUtils::handleName).collect(Collectors.toList()));
        return this;
    }

    @Override
    public String precompileSql() {
        if (prefix == null || "".equals(prefix)) return "SELECT " + String.join(", ", columns);
        return prefix + " SELECT " + String.join(", ", columns);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }
}
