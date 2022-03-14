package club.kingon.sql.builder;

/**
 * @author dragons
 * @date 2021/11/10 19:52
 */
public interface UnionSqlBuilderRoute extends SqlBuilder {

    default UnionSqlBuilder union(SqlBuilder...unions) {
        return new UnionSqlBuilder(precompileSql(), precompileArgs(), unions);
    }

    default UnionSqlBuilder unionAll(SqlBuilder ...unions) {
        return new UnionAllSqlBuilder(precompileSql(), precompileArgs(), unions);
    }
}
