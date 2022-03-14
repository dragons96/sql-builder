package club.kingon.sql.builder.util;

import club.kingon.sql.builder.config.GlobalConfig;
import club.kingon.sql.builder.entry.Alias;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dragons
 * @date 2022/3/11 12:54
 */
public class SqlNameUtils {

    public static String handleName(Alias alias) {
        if (GlobalConfig.OPEN_STRICT_MODE && alias != null) {
            return handleName(alias.toString());
        }
        return "";
    }

    public static String handleName(String name) {
        if (GlobalConfig.OPEN_STRICT_MODE && name != null) {
            return Arrays.stream(name.split("\\.")).map(SqlNameUtils::handleStrictName).collect(Collectors.joining("."));
        }
        return name;
    }

    private static String handleStrictName(String name) {
        if (!"*".equals(name) && !name.contains("(")) {
            if (name.contains(",")) {
                return Arrays.stream(name.split(",")).map(String::trim).map(SqlNameUtils::handleName).collect(Collectors.joining(", "));
            } else if (name.contains("as")) {
                String[] columnAndAlias = name.split("as");
                return "`" + columnAndAlias[0].trim() + "` as `" + columnAndAlias[1].trim() + "`";
            } else if (name.startsWith("`")) {
                return name;
            }
            return "`" + name + "`";
        }
        return name;
    }
}
