package club.kingon.sql.builder.example;

import club.kingon.sql.builder.SqlBuilder;
import club.kingon.sql.builder.enums.Operator;

import java.util.Arrays;

/**
 * @author dragons
 * @date 2021/11/11 20:23
 */
public class SimpleDeleteSql {
    public static void main(String[] args) {
        SqlBuilder builder = SqlBuilder
            .delete("table_a")
            .where("column_1", Operator.GE, 10);
        System.out.println(builder.build());
        System.out.println(builder.precompileSql());
        System.out.println(Arrays.toString(builder.precompileArgs()));
    }
}
