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
     * example:
     * <code>
     *     Conditions.whereEq((LMDFunction<User, ?>) User::getId, 1)
     * </code>
     * if false, the result is "id = 1", or else "user.id = 1"
     */
    public static boolean OPEN_LAMBDA_TABLE_NAME_MODE = false;
    /**
     * Configure SQL strict mode.
     * example:
     * <code>
     *     Conditions.whereEq("a", 1)
     * </code>
     * if false, the result is "a = 1", or else "`a` = 1"
     */
    public static boolean OPEN_STRICT_MODE = false;
}
