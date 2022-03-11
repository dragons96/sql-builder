package club.kingon.sql.builder;


import java.util.function.Supplier;

/**
 * @author dragons
 * @date 2021/11/10 10:40
 */
public class WhereSqlBuilder extends ConditionSqlBuilder<WhereSqlBuilder> implements SqlBuilder, GroupSqlBuilderRoute, OrderSqlBuilderRoute, LimitSqlBuilderRoute, UnionSqlBuilderRoute {


    protected WhereSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, WhereSqlBuilder b) {
        super(predicate, prefix, precompileArgs, b);
    }

    protected WhereSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs) {
        super(predicate, prefix, precompileArgs);
    }

    protected WhereSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Object... values) {
        super(predicate, prefix, precompileArgs, condition, values);
    }

    protected WhereSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, String condition, Supplier<Object[]> supplier) {
        super(predicate, prefix, precompileArgs, condition, supplier);
    }

    protected WhereSqlBuilder(Boolean predicate, String prefix, Object[] precompileArgs, Object... queryCriteria) {
        super(predicate, prefix, precompileArgs, queryCriteria);
    }
}
