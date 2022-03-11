package club.kingon.sql.builder.example;

import club.kingon.sql.builder.SqlBuilder;
import club.kingon.sql.builder.annotation.Query;
import club.kingon.sql.builder.enums.Operator;

import java.util.Arrays;
import java.util.List;

class Item {
    Integer id;
    String name;

    public Item(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

class QueryCriteria {
    @Query
    private Integer id;
    @Query(type = Operator.LRLIKE)
    private String name;
    @Query(value = "item_id", type = Operator.IN, attr = "id")
    private List<Item> items;

    public QueryCriteria(Integer id, String name, List<Item> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }
}

/**
 * @author dragons
 * @date 2022/1/18 11:24
 */
public class SimpleQuerySql2 {
    public static void main(String[] args) {
        System.out.println(
            SqlBuilder.selectAll().from("demo").where(new QueryCriteria(null, "dragons",
                Arrays.asList(new Item(null, null), new Item(null, null))))
                .build()
        );
    }
}
