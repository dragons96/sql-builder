package club.kingon.sql.builder.example;

import club.kingon.sql.builder.SqlBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dragons
 * @date 2022/2/23 16:48
 */
public class SimpleQuerySql3 {
    public static void main(String[] args) {
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("a", "2");
        criteria.put("b", 1);
        criteria.put("c", Arrays.asList("1", "2", "3"));
        criteria.put("d", new Integer[]{1, 2, 3});
        System.out.println(
            SqlBuilder.selectAll().from("xxx")
            .where(criteria)
            .build()
        );
    }
}
