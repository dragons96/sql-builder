package club.kingon.sql.builder;


/**
 * @author dragons
 * @date 2021/11/10 19:14
 */
public interface LimitSqlBuilderRoute extends SqlBuilder {

    default LimitSqlBuilder limit(int count) {
        return new LimitSqlBuilder(precompileSql(), precompileArgs(), count);
    }

    default LimitSqlBuilder limit(int offset, int count) {
        return new LimitSqlBuilder(precompileSql(), precompileArgs(), offset, count);
    }

    default LimitSqlBuilder limit(Boolean predicate, int count) {
        if (Boolean.TRUE.equals(predicate)) {
            return new LimitSqlBuilder(precompileSql(), precompileArgs(), count);
        }
        return new LimitSqlBuilder(precompileSql(), precompileArgs());
    }

    default LimitSqlBuilder limit(Boolean predicate, int offset, int count) {
        if (Boolean.TRUE.equals(predicate)) {
            return new LimitSqlBuilder(precompileSql(), precompileArgs(), offset, count);
        }
        return new LimitSqlBuilder(precompileSql(), precompileArgs());
    }
}
