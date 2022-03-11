package club.kingon.sql.builder.spring.mybatisplus.query;

import club.kingon.sql.builder.*;
import club.kingon.sql.builder.entry.Constants;

/**
 * @author dragons
 * @date 2022/3/9 12:31
 */
public class MybatisQuerySqlBuilder implements SqlBuilder, WhereSqlBuilderRoute,
    GroupSqlBuilderRoute, LimitSqlBuilderRoute, JoinSqlBuilderRoute, OrderSqlBuilderRoute, UnionSqlBuilderRoute {

    public final static MybatisQuerySqlBuilder I = new MybatisQuerySqlBuilder();

    private MybatisQuerySqlBuilder() {
    }

    @Override
    public String precompileSql() {
        return " ";
    }

    @Override
    public Object[] precompileArgs() {
        return Constants.EMPTY_OBJECT_ARRAY;
    }
}
