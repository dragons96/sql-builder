package club.kingon.sql.builder.example;

import club.kingon.sql.builder.Conditions;
import club.kingon.sql.builder.SqlBuilder;
import club.kingon.sql.builder.enums.Operator;
import club.kingon.sql.builder.enums.Order;

import java.util.Arrays;

/**
 * @author dragons
 * @date 2021/11/11 10:31
 */
public class SimpleQuerySql {
    public static void main(String[] args) {
        SqlBuilder builder = SqlBuilder
            .select("t1.*", "t2.*")
            .from("t1")
            .join("t2")
            .on("t1.a = t2.a")
            .where("t1.b", Operator.GE, 10)
            .orLe("t2.b", 5)
            .or(Conditions.where("t1.c", Operator.IN, 3, 4, 5).and("t2.c", Operator.NOT_BETWEEN_AND, 5, 10))
            .andLe("t1.d", SqlBuilder.select("count(*)").from("t3").where("t3.a < ?", 100))
            .andLike("t1.b", 1)
            .and(true, Conditions.whereLe("id", 10), Conditions.whereLe("ip", 20))
            .groupBy("t1.z")
            .having("count(1)", Operator.GE, 100)
            .orderBy("t1.z", Order.ASC)
            .limit(10, 100);
        System.out.println(builder.build());
        System.out.println(builder.precompileSql());
        System.out.println(Arrays.toString(builder.precompileArgs()));
    }
}
