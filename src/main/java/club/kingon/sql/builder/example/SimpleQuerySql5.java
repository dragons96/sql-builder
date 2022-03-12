package club.kingon.sql.builder.example;

import club.kingon.sql.builder.SqlBuilder;
import club.kingon.sql.builder.annotation.Column;
import club.kingon.sql.builder.annotation.Table;
import club.kingon.sql.builder.config.GlobalConfig;
import club.kingon.sql.builder.enums.Operator;

@Table("users")
class User {
    @Column("name")
    private String username;

    private Long id;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
@Table("orders")
class Order {

    private Long id;

    private Long uid;

    public Long getId() {
        return id;
    }

    public Long getUid() {
        return uid;
    }
}

/**
 * @author dragons
 * @date 2022/3/10 15:59
 */
public class SimpleQuerySql5 {
    public static void main(String[] args) {
        System.out.println(
            SqlBuilder.model(User.class)
                .whereLike(User::getUsername, "dragons")
                .andEq(User::getId, 3)
                .build()
        );
        // 开启lambda字段表名模式
        GlobalConfig.OPEN_LAMBDA_TABLE_NAME_MODE = true;
        System.out.println(
            SqlBuilder.select(User::getId)
            .from(User.class)
            .join(Order.class)
            .on(User::getId, Operator.EQ, club.kingon.sql.builder.entry.Column.as(Order::getUid))
            .build()
        );
        // select users.id from users join orders on users.id = orders.uid
    }
}
