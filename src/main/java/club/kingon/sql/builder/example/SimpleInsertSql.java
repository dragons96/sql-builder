package club.kingon.sql.builder.example;

import club.kingon.sql.builder.SqlBuilder;

import java.util.Arrays;

/**
 * @author dragons
 * @date 2021/11/11 12:37
 */
public class SimpleInsertSql {
    public static void main(String[] args) {
        String sql1 = SqlBuilder.insertInto("t1", "c1", "c2", "c3")
            .values()
            .addValue("v1", "v2", "v3")
            .addValue("v11", "v22", "v33")
            .addValues(Arrays.asList(new Object[]{"v111", "v222", "v333"}, new Object[]{"v1111", "v2222", "v3333"}))
            .build();
        System.out.println(sql1);

        String sql2 = SqlBuilder.replaceInto("t2", "id", "c1", "c2", "c3")
            .selectAll()
            .from("t1")
            .limit(10)
            .build();
        System.out.println(sql2);
    }
}
