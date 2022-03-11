package club.kingon.sql.builder;


/**
 * @author dragons
 * @date 2021/11/11 12:00
 */
public class ValueSqlBuilder extends ValuesSqlBuilder {

    protected ValueSqlBuilder(String prefix, Object[] precompileArgs) {
        super(prefix, precompileArgs);
        sign = "VALUE";
    }

}
