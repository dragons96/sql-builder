package club.kingon.sql.builder.example;

import club.kingon.sql.builder.SqlBuilder;
import club.kingon.sql.builder.enums.Operator;

import java.util.Arrays;

/**
 * @author dragons
 * @date 2021/11/11 20:24
 */
public class SimpleUpdateSql {
    public static void main(String[] args) {
        SqlBuilder builder = SqlBuilder
            .update("table_a")
            .set("column_1", 18)
            .where("column_2", Operator.EQ, "test");
        System.out.println(builder.build());
        System.out.println(builder.precompileSql());
        System.out.println(Arrays.toString(builder.precompileArgs()));
    }
}
