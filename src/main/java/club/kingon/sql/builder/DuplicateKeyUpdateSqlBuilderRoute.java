package club.kingon.sql.builder;

import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

/**
 * @author dragons
 * @date 2021/12/7 17:12
 */
public interface DuplicateKeyUpdateSqlBuilderRoute extends SqlBuilder {

    default DuplicateKeyUpdateSqlBuilder onDuplicateKeyUpdateColumn(String column) {
        column = SqlNameUtils.handleName(column);
        return new DuplicateKeyUpdateSqlBuilder(precompileSql(), precompileArgs(),  column+ " = values(" + column + ")");
    }

    default <P>DuplicateKeyUpdateSqlBuilder onDuplicateKeyUpdateColumn(LMDFunction<P, ?> lambdaFunctions) {
        String column = SqlNameUtils.handleName(LambdaUtils.getColumnName(lambdaFunctions));
        return new DuplicateKeyUpdateSqlBuilder(precompileSql(), precompileArgs(),  column + " = values(" + column + ")");
    }

    default DuplicateKeyUpdateSqlBuilder onDuplicateKeyUpdateSetter(String setter) {
        return new DuplicateKeyUpdateSqlBuilder(precompileSql(), precompileArgs(), setter);
    }
}
