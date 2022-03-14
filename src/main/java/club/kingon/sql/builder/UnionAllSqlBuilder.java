package club.kingon.sql.builder;

/**
 * @author dragons
 * @date 2022/3/14 17:19
 */
public class UnionAllSqlBuilder extends UnionSqlBuilder {
    protected UnionAllSqlBuilder(String prefix, Object[] precompileArgs, SqlBuilder... unions) {
        super(prefix, precompileArgs, unions);
        sep = "UNION ALL";
    }
}
