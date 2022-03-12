package club.kingon.sql.builder;

import club.kingon.sql.builder.enums.Operator;
import club.kingon.sql.builder.util.ConditionUtils;

/**
 * @author dragons
 * @date 2021/11/11 20:13
 */
public interface SetSqlBuilderRoute extends SqlBuilder {

    default SetSqlBuilder set(String setter) {
        return new SetSqlBuilder(precompileSql(), precompileArgs(), setter);
    }

    default SetSqlBuilder set(String column, Object value) {
        return new SetSqlBuilder(precompileSql(), precompileArgs(), column, value);
    }

    default <P>SetSqlBuilder set(LMDFunction<P, ?> lambdaFunction, Object value) {
        return new SetSqlBuilder(precompileSql(), precompileArgs(), lambdaFunction, value);
    }

    @Deprecated
    default SetSqlBuilder setColumn(String column1, String column2) {
        return new SetSqlBuilder(precompileSql(), precompileArgs(), ConditionUtils.parseConditionColumn(column1, Operator.EQ, column2));
    }
}
