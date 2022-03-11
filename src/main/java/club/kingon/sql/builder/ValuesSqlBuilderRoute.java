package club.kingon.sql.builder;

/**
 * @author dragons
 * @date 2021/11/11 12:34
 */
public interface ValuesSqlBuilderRoute extends SqlBuilder {

    default ValuesSqlBuilder values() {
        return new ValuesSqlBuilder(precompileSql(), precompileArgs());
    }
}
