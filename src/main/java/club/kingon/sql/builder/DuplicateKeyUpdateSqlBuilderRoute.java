package club.kingon.sql.builder;

import club.kingon.sql.builder.util.SqlNameUtils;

/**
 * @author dragons
 * @date 2021/12/7 17:12
 */
public interface DuplicateKeyUpdateSqlBuilderRoute extends SqlBuilder {

    default DuplicateKeyUpdateSqlBuilder onDuplicateKeyUpdateColumn(String column) {
        return new DuplicateKeyUpdateSqlBuilder(precompileSql(), precompileArgs(), SqlNameUtils.handleName(column) + " = values(" + SqlNameUtils.handleName(column) + ")");
    }

    default DuplicateKeyUpdateSqlBuilder onDuplicateKeyUpdateSetter(String setter) {
        return new DuplicateKeyUpdateSqlBuilder(precompileSql(), precompileArgs(), setter);
    }
}
