package club.kingon.sql.builder;

import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dragons
 * @date 2021/12/7 16:57
 */
public class DuplicateKeyUpdateSqlBuilder implements SqlBuilder {

    private final String sign;

    private final String prefix;

    private final List<String> setters;

    private final Object[] precompileArgs;

    protected DuplicateKeyUpdateSqlBuilder(String prefix, Object[] precompileArgs) {
        this.sign = "values";
        this.prefix = prefix;
        this.setters = new ArrayList<>();
        this.precompileArgs = precompileArgs;
    }

    protected DuplicateKeyUpdateSqlBuilder(String prefix, Object[] precompileArgs, String setter) {
        this(prefix, precompileArgs);
        setters.add(setter);
    }

    public DuplicateKeyUpdateSqlBuilder addUpdateColumn(String column) {
        column = SqlNameUtils.handleName(column);
        setters.add(column + " = " + sign + "(" + column + ")");
        return this;
    }

    public DuplicateKeyUpdateSqlBuilder addUpdateSetter(String setter) {
        setters.add(setter);
        return this;
    }

    public <P>DuplicateKeyUpdateSqlBuilder addUpdateColumn(LMDFunction<P, ?> lambdaFunction) {
        String column = SqlNameUtils.handleName(LambdaUtils.getColumnName(lambdaFunction));
        setters.add(column + " = " + sign + "(" + column + ")");
        return this;
    }

    @Override
    public String precompileSql() {
        return prefix + " ON DUPLICATE KEY UPDATE " + String.join(", ", setters);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs;
    }
}
