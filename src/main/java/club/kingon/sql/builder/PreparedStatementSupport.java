package club.kingon.sql.builder;

/**
 * Jdbc PreparedStatement support
 * @see java.sql.PreparedStatement
 * @author dragons
 * @date 2022/1/18 16:41
 */
public interface PreparedStatementSupport {
    /**
     * Precompile sql.
     * @return sql
     */
    String precompileSql();
    /**
     * Precompile args.
     * @return args
     */
    Object[] precompileArgs();
}
