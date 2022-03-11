package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Alias;

/**
 * @author dragons
 * @date 2021/11/11 11:37
 */
public interface SelectSqlBuilderRoute extends SqlBuilder {

    default SelectSqlBuilder selectAll() {
        return new SelectSqlBuilder(precompileSql(), precompileArgs(), "*");
    }

    default SelectSqlBuilder select(String ...columns) {
        return new SelectSqlBuilder(precompileSql(), precompileArgs(), columns);
    }

    default SelectSqlBuilder select(Alias...columns) {
        return new SelectSqlBuilder(precompileSql(), precompileArgs(), columns);
    }

    default SelectSqlBuilder select(Object ...columns) {
        return new SelectSqlBuilder(precompileSql(), precompileArgs(), columns);
    }
}
