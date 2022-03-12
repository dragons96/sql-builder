package club.kingon.sql.builder.entry;

import club.kingon.sql.builder.LMDFunction;
import club.kingon.sql.builder.util.LambdaUtils;
import club.kingon.sql.builder.util.SqlNameUtils;

/**
 * @author dragons
 * @date 2021/11/16 18:37
 */
public class Column {

    private final String name;

    protected Column(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Column as(String column) {
        return new Column(SqlNameUtils.handleName(column));
    }

    public static <P> Column as(LMDFunction<P, ?> lambdaFunction) {
        return new Column(SqlNameUtils.handleName(LambdaUtils.getColumnName(lambdaFunction)));
    }
}
