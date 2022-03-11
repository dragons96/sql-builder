package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dragons
 * @date 2021/11/11 12:24
 */
public class ValuesSqlBuilder implements SqlBuilder, DuplicateKeyUpdateSqlBuilderRoute {

    private final String prefix;

    private final StringBuilder valuesBuilder;

    protected String sign = "VALUES";

    private final List<Object> precompileArgs = new ArrayList<>();

    protected ValuesSqlBuilder(String prefix, Object[] compileArgs) {
        this.prefix = prefix;
        this.valuesBuilder = new StringBuilder();
        precompileArgs.addAll(Arrays.asList(compileArgs));
    }

    public ValuesSqlBuilder addValue(Object ...value) {
        if (value.length == 0) return this;
        if (valuesBuilder.length() > 0) {
            valuesBuilder.append(", ");
        }
        valuesBuilder.append("(");
        for (int i = 0; i < value.length; i++) {
            if (i > 0) {
                valuesBuilder.append(", ");
            }
            valuesBuilder.append("?");
            precompileArgs.add(value[i]);
        }
        valuesBuilder.append(")");
        return this;
    }

    public ValuesSqlBuilder addValues(List<Object[]> values) {
        if (values == null || values.isEmpty()) return this;
        values.forEach(this::addValue);
        return this;
    }


    @Override
    public String precompileSql() {
        boolean prefixEmpty = prefix == null || "".equals(prefix), valuesEmpty = valuesBuilder.length() == 0;
        if (prefixEmpty && valuesEmpty) return "";
        if (valuesEmpty) return prefix;
        if (prefixEmpty) return valuesBuilder.toString();
        return prefix + " " + sign + valuesBuilder;
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs.toArray(Constants.EMPTY_OBJECT_ARRAY);
    }
}
