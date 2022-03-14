package club.kingon.sql.builder;

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
}
