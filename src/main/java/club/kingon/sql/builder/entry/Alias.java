package club.kingon.sql.builder.entry;

import club.kingon.sql.builder.SqlBuilder;

import java.util.Objects;

/**
 * @author dragons
 * @date 2021/11/10 12:28
 */
public class Alias {
    private final String origin;

    private final String alias;

    private Alias(String origin) {
        this.origin = origin;
        this.alias = null;
    }

    private Alias(String origin, String alias) {
        this.origin = origin;
        this.alias = alias;
    }

    public static Alias of(String origin) {
        return new Alias(origin);
    }

    public static Alias of(String origin, String alias) {
        return new Alias(origin, alias);
    }

    public static Alias of(SqlBuilder sb) {
        return new Alias("(" + sb.build() + ")");
    }

    public static Alias of(SqlBuilder sb, String alias) {
        return new Alias("(" + sb.build() + ")", alias);
    }

    public String getAlias() {
        return alias;
    }

    public String getOrigin() {
        return origin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alias alias1 = (Alias) o;
        return Objects.equals(origin, alias1.origin) && Objects.equals(alias, alias1.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, alias);
    }

    @Override
    public String toString() {
        if (alias == null || "".equals(alias)) return origin;
        return origin + " as " + alias;
    }
}
