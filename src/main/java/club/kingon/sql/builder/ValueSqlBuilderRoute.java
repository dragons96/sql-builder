package club.kingon.sql.builder;

/**
 * @author dragons
 * @date 2021/11/11 12:32
 */
public interface ValueSqlBuilderRoute extends SqlBuilder {

    default ValueSqlBuilder value() {
        return new ValueSqlBuilder(precompileSql(), precompileArgs());
    }

}
