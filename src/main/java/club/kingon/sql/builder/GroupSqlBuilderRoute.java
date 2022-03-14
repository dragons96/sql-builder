package club.kingon.sql.builder;

import club.kingon.sql.builder.util.LambdaUtils;

import java.util.Arrays;

/**
 * @author dragons
 * @date 2021/11/10 19:28
 */
public interface GroupSqlBuilderRoute extends SqlBuilder {

    default GroupSqlBuilder groupBy(String ...columns) {
        return new GroupSqlBuilder(precompileSql(), precompileArgs(), columns);
    }

    default <P>GroupSqlBuilder groupBy(LMDFunction<P, ?>... lambdaFunctions) {
        return new GroupSqlBuilder(precompileSql(), precompileArgs(), Arrays.stream(lambdaFunctions).map(LambdaUtils::getColumnName).toArray(String[]::new));
    }

    default GroupSqlBuilder groupBy(SqlBuilder...subQueries) {
        return new GroupSqlBuilder(precompileSql(), precompileArgs(), subQueries);
    }
}
