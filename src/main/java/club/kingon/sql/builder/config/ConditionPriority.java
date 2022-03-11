package club.kingon.sql.builder.config;

/**
 * @author dragons
 * @date 2022/1/28 17:01
 */
public enum ConditionPriority {
    /**
     * Condition priority. The default is first and then or.
     */
    DEFAULT,
    /**
     * Condition priority. This is first left and then right.
     */
    LEFT_TO_RIGHT,
}
