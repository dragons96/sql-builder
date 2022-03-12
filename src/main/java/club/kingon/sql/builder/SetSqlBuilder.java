package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Operator;
import club.kingon.sql.builder.util.ConditionUtils;
import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dragons
 * @date 2021/11/11 13:30
 */
public class SetSqlBuilder implements SqlBuilder, FromSqlBuilderRoute, WhereSqlBuilderRoute {

    private final String prefix;

    private final List<String> setters;

    private final List<Object> precompileArgs = new ArrayList<>();

    protected SetSqlBuilder(String prefix, Object[] precompileArgs) {
        this.prefix = prefix;
        this.setters = new ArrayList<>();
        this.precompileArgs.addAll(Arrays.asList(precompileArgs));
    }

    protected SetSqlBuilder(String prefix, Object[] precompileArgs, String column, Object value) {
        this(prefix, precompileArgs);
        addSet(column, value);
    }

    protected SetSqlBuilder(String prefix, Object[] precompileArgs, LMDFunction<?, ?> lambdaFunction, Object value) {
        this(prefix, precompileArgs);
        addSet(lambdaFunction, value);
    }

    protected SetSqlBuilder(String prefix, Object[] precompileArgs, String setter) {
        this(prefix, precompileArgs);
        setters.add(setter);
    }

    public SetSqlBuilder addSet(String setter, Object...values) {
        setters.add(setter);
        if (values.length > 0) {
            precompileArgs.addAll(Arrays.asList(values));
        }
        return this;
    }

    public SetSqlBuilder addSet(String column, Object value) {
        Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(column), Operator.EQ, value);
        return addSet(pt._1, pt._2);
    }

    public <P>SetSqlBuilder addSet(LMDFunction<P, ?> lambdaFunction, Object value) {
        Tuple2<String, Object[]> pt = ConditionUtils.parsePrecompileCondition(SqlNameUtils.handleName(LambdaUtils.getColumnName(lambdaFunction)), Operator.EQ, value);
        return addSet(pt._1, pt._2);
    }

    @Deprecated
    public SetSqlBuilder addSetColumn(String column1, String column2) {
        return addSet(ConditionUtils.parseConditionColumn(SqlNameUtils.handleName(column1), Operator.EQ, SqlNameUtils.handleName(column2)));
    }

    @Override
    public String precompileSql() {
        boolean prefixEmpty = prefix == null || "".equals(prefix), settersEmpty = setters.isEmpty();
        if (prefixEmpty && settersEmpty) return "";
        if (settersEmpty) return prefix;
        if (prefixEmpty) return String.join(", ", setters);
        return prefix + " SET " + String.join(", ", setters);
    }

    @Override
    public Object[] precompileArgs() {
        return precompileArgs.toArray(Constants.EMPTY_OBJECT_ARRAY);
    }
}
