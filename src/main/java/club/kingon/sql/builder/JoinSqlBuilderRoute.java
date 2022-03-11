package club.kingon.sql.builder;

/**
 * @author dragons
 * @date 2021/11/10 19:23
 */
public interface JoinSqlBuilderRoute extends SqlBuilder {
    default JoinSqlBuilder join(Object table) {
        return new JoinSqlBuilder(precompileSql(), precompileArgs(),"JOIN", table);
    }

    default JoinSqlBuilder innerJoin(Object table) {
        return new JoinSqlBuilder(precompileSql(), precompileArgs(),"INNER JOIN", table);
    }

    default JoinSqlBuilder leftJoin(Object table) {
        return new JoinSqlBuilder(precompileSql(), precompileArgs(), "LEFT JOIN", table);
    }

    default JoinSqlBuilder rightJoin(Object table) {
        return new JoinSqlBuilder(precompileSql(), precompileArgs(), "RIGHT JOIN", table);
    }

    default JoinSqlBuilder fullJoin(Object table) {
        return new JoinSqlBuilder(precompileSql(), precompileArgs(), "FULL JOIN", table);
    }
}
