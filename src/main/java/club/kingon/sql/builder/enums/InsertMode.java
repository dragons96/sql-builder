package club.kingon.sql.builder.enums;

/**
 * @author dragons
 * @date 2021/11/11 11:30
 */
public enum InsertMode {
    INSERT_INTO("INSERT INTO"),
    REPLACE("REPLACE"),
    INSERT_OVERWRITE("INSERT OVERWRITE"),
    INSERT_IGNORE("INSERT IGNORE"),
    REPLACE_INTO("REPLACE INTO"),
    ;

    private String prefix;

    InsertMode(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
