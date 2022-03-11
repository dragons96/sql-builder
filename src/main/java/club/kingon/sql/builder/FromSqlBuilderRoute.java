package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Alias;

/**
 * @author dragons
 * @date 2021/11/10 19:26
 */
public interface FromSqlBuilderRoute extends SqlBuilder {

    default FromSqlBuilder from(String ...tableNames) {
        return new FromSqlBuilder(precompileSql(), precompileArgs(), tableNames);
    }

    default FromSqlBuilder from(Alias...tableNames) {
        return new FromSqlBuilder(precompileSql(), precompileArgs(), tableNames);
    }

    default FromSqlBuilder from(Object ...tableNames) {
        return new FromSqlBuilder(precompileSql(), precompileArgs(), tableNames);
    }
}
