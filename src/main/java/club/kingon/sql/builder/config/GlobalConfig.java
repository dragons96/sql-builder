package club.kingon.sql.builder.config;

/**
 * @author dragons
 * @date 2022/1/27 18:41
 */
public abstract class GlobalConfig {
    /**
     * todo
     * Global sql builder type.
     */
    public static DataSourceType DATA_SOURCE_TYPE = DataSourceType.MYSQL;

    /**
     * Condition priority config.
     */
    public static ConditionPriority CONDITION_PRIORITY = ConditionPriority.DEFAULT;
    /**
     * Configure whether lambda column names need to include table names.
     */
    public static boolean OPEN_LAMBDA_TABLE_NAME_MODE = false;
    /**
     * Configure SQL strict mode.
     */
    public static boolean OPEN_STRICT_MODE = false;
}
