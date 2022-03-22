package club.kingon.sql.builder.example;

import club.kingon.sql.builder.SqlBuilder;
import club.kingon.sql.builder.annotation.Column;
import club.kingon.sql.builder.annotation.Query;
import club.kingon.sql.builder.annotation.Table;
import club.kingon.sql.builder.config.GlobalConfig;
import club.kingon.sql.builder.entry.Alias;
import club.kingon.sql.builder.enums.Operator;

import java.util.Arrays;
import java.util.List;

@Table("users")
class U {
    private Integer id;

    @Column("name")
    private String username;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}

@Table("orders")
class O {
    private Integer id;

    private Integer uid;

    private String info;

    public Integer getId() {
        return id;
    }

    public Integer getUid() {
        return uid;
    }

    public String getInfo() {
        return info;
    }
}

class UserOrderCriteria {

    @Query(value = "users.id", type = Operator.GE)
    private Integer userId;

    @Query(value = "orders.id", type = Operator.IN)
    private List<Integer> orderIds;

    @Query(value = "users.name", type = Operator.LIKE)
    private String username;

    public UserOrderCriteria(Integer userId, List<Integer> orderIds, String username) {
        this.userId = userId;
        this.orderIds = orderIds;
        this.username = username;
    }
}

/**
 * @author dragons
 * @date 2022/3/14 15:48
 */
public class SimpleQuerySql6 {
    public static void main(String[] args) {
        GlobalConfig.OPEN_LAMBDA_TABLE_NAME_MODE = true;

        // SELECT users.id, users.name FROM users WHERE users.id >= 1
        System.out.println(
            SqlBuilder.model(U.class)
                .where(new UserOrderCriteria(1, null, null))
                .orderByDesc(User::getId)
                .build()
        );

        // SELECT users.id, users.name FROM users WHERE users.name LIKE '%dragons%'
        System.out.println(
            SqlBuilder.model(U.class)
                .where(new UserOrderCriteria(null, null, "dragons"))
                .build()
        );
        // SELECT users.id, users.name, orders.id as order_id FROM users JOIN orders ON users.id = orders.uid WHERE orders.id IN (1, 2, 3) ORDER BY users.id ASC, orders.id DESC
        System.out.println(
            SqlBuilder.select(U.class).addColumn(Alias.of(O::getId, "order_id"))
                .from(U.class)
                .join(O.class)
                .onEq(U::getId, club.kingon.sql.builder.entry.Column.as(O::getUid))
                .where(new UserOrderCriteria(null, Arrays.asList(1, 2, 3), null))
                .orderByAsc(U::getId).addDesc(O::getId)
                .build()
        );
        // SELECT users.id, users.name, orders.id as order_id FROM users JOIN orders ON users.id = orders.uid WHERE orders.id IN (1, 2, 3) AND users.name LIKE '%dragons%' ORDER BY users.id ASC, orders.id DESC LIMIT 0, 10
        System.out.println(
            SqlBuilder.select(U.class).addColumn(Alias.of(O::getId, "order_id"))
                .from(U.class)
                .join(O.class)
                .onEq(U::getId, club.kingon.sql.builder.entry.Column.as(O::getUid))
                .where(new UserOrderCriteria(null, Arrays.asList(1, 2, 3), "dragons"))
                .orderByAsc(U::getId).addDesc(O::getId)
                .limit(0, 10)
                .build()
        );
    }
}
