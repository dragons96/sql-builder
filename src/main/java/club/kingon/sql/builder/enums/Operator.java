package club.kingon.sql.builder.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Operator Enum
 * @author dragons
 * @date 2021/11/10 12:53
 */
public enum Operator {
    /**
     * Equal
     */
    EQ("="),
    /**
     * Less than
     */
    LT("<"),
    /**
     * Less than or equal
     */
    LE("<="),
    /**
     * Greater than
     */
    GT(">"),
    /**
     * Greater than or equal
     */
    GE(">="),
    /**
     * Not equal
     */
    NE("!="),
    /**
     * A not equal another way. It means less than and greater than.
     */
    LTGT("<>"),
    /**
     * Fuzzy query. It means "field like 'query'"
     */
    LIKE("LIKE"),
    /**
     * Left fuzzy query. It means "field like '%query'"
     */
    LLIKE("LIKE"),
    /**
     * Right fuzzy query. It means "field like 'query%'"
     */
    RLIKE("LIKE"),
    /**
     * Left and right fuzzy query. It means "field like '%query%'"
     */
    LRLIKE("LIKE"),
    /**
     * Not fuzzy query. It means "field not like 'query'"
     */
    NOT_LIKE("NOT LIKE"),
    /**
     * Not left fuzzy query. It means "field not like '%query'"
     */
    NOT_LLIKE("NOT LIKE"),
    /**
     * Not right fuzzy query. It means "field not like 'query%'"
     */
    NOT_RLIKE("NOT LIKE"),
    /**
     * Not left and right fuzzy query. It means "field not like '%query%'"
     */
    NOT_LRLIKE("NOT LIKE"),
    /**
     * In query. It means "field in (...)"
     */
    IN("IN (", ")"),
    /**
     * Between and query. It means "field between query1 and query2"
     */
    BETWEEN_AND("BETWEEN", "AND"),
    /**
     * Not between and query. It means "field not between query1 and query2"
     */
    NOT_BETWEEN_AND("NOT BETWEEN", "AND"),
    /**
     * Not in query. It means "field not in (...)"
     */
    NOT_IN("NOT IN (", ")"),

    IS_NULL("IS NULL"),

    NOT_NULL("IS NOT NULL"),

    EXISTS("EXISTS (", ")"),

    NOT_EXISTS("NOT EXISTS (", ")")
    ;

    private final List<String> options;

    Operator(String... options) {
        this.options = Arrays.asList(options);
    }

    public List<String> getOptions() {
        return options;
    }
}
