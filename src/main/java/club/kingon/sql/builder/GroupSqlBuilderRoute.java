package club.kingon.sql.builder;

/**
 * @author dragons
 * @date 2021/11/10 19:28
 */
public interface GroupSqlBuilderRoute extends SqlBuilder {

    default GroupSqlBuilder groupBy(String ...columns) {
        return new GroupSqlBuilder(precompileSql(), precompileArgs(), columns);
    }

    default GroupSqlBuilder groupBy(SqlBuilder...subQueries) {
        return new GroupSqlBuilder(precompileSql(), precompileArgs(), subQueries);
    }
}
