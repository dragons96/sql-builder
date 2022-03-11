package club.kingon.sql.builder.example;

import club.kingon.sql.builder.SqlBuilder;
import club.kingon.sql.builder.annotation.Query;
import club.kingon.sql.builder.enums.Operator;

import java.util.Arrays;
import java.util.List;

class Tag {
    private String type;
    private List<Object> values;
    public Tag(String type, List<Object> values) {
        this.type = type;
        this.values = values;
    }

    public String getType() {
        return type;
    }
}

class TagQueryCriteria {
    @Query(value = "${type}", type = Operator.IN, attr = "values")
    private List<Tag> tags;
    public TagQueryCriteria(List<Tag> tags) {
        this.tags = tags;
    }
}

/**
 * @author dragons
 * @date 2022/3/9 12:22
 */
public class SimpleQuerySql4 {
    public static void main(String[] args) {
        // select * from table_a where id in (4, 5) and cat in ("7", "8")
        System.out.println(SqlBuilder.selectAll()
            .from("table_a")
            .where(new TagQueryCriteria(Arrays.asList(
                new Tag("id", Arrays.asList(4, 5)),
                new Tag("cat", Arrays.asList("7", "8"))
            )))
            .build()
        );
    }
}
