package club.kingon.sql.builder.annotation;

import club.kingon.sql.builder.enums.Operator;

import java.lang.annotation.*;

/**
 * @author dragons
 * @date 2021/12/8 15:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD})
@Documented
public @interface Query {
    /**
     * Column name or field mapping expression, like ${xxx}.
     * example:
     * <code>
     * class A {
     *     private String name;
     *
     *     private String value;
     *     public A (String name, String value) {this.name = name; this.value = value;}
     * }
     * class Criteria {
     *     @Query(value="${name}", type=Operator.EQ, attr="value")
     *     private A a;
     *     public Criteria(A a) {this.a = a}
     * }
     * class Test {
     *     public static void main(String[] args) {
     *         // sql: select * from table_a where key = "val"
     *         SqlBuilder.selectAll()
     *            .from("table_a")
     *            .where(new Criteria(new A("key", "val")))
     *            .builder();
     *     }
     * }
     * </code>
     * It also support collection type like List<A>, which means only a parameter to generate multiple conditions.
     */
    String value() default "";

    /**
     * Relationship types.
     */
    Operator type() default Operator.EQ;

    /**
     * Mapping field name.
     * It that some simple scenarios can be used. Please complex scenarios use mapFun().
     * For example:
     * <blockquote><pre>
     *      class User {
     *          String phone;
     *      }
     *      class GoodsCriteria {
     *          User user;
     *      }
     * </pre></blockquote>
     * if u want to use GoodsCriteria to query user's phone, u can use it like this:
     *  <blockquote><pre>
     *      class GoodsCriteria {
     *          @query(value="u_phone", attr="phone")
     *          User user;
     *      }
     *  </pre></blockquote>
     *  that eq "SELECT ... WHERE u_phone = '${User.phone}'"
     */
    String attr() default "";

    /**
     * Mapping function.
     * The class must be implemented for java.util.Function or java.util.BiFunction and Contains empty constructor.
     * like:
     * <blockquote><pre>
     *      class User {
     *          String secret;
     *      }
     *      class GoodsCriteria {
     *          User user;
     *      }
     * </pre></blockquote>
     * if u want to use GoodsCriteria to query user's secret and do some extra processing on it, u can use it like this:
     * <blockquote><pre>
     *      class UserSecretHandlerFunction implement java.util.Function<User, String> {
     *
     *          public String apply(User user) {
     *              return "MASK" + user.getSecret() + "MASK";
     *          }
     *      }
     *
     *      class UserSecretHandlerBiFunction implement java.util.BiFunction<GoodsCriteria, User, String> {
     *
     *          public String apply(GoodsCriteria criteria, User user) {
     *              return "MASK" + user.getSecret() + "MASK";
     *          }
     *      }
     *
     *      class GoodsCriteria {
     *          @Query(value="u_sct", mapFun=UserSecretHandlerFunction.class)
     *          //@Query(value="u_sct", mapFun=UserSecretHandlerBiFunction.class) // the same as
     *          User user;
     *      }
     * </pre></blockquote>
     * PS: the first parameter is criteria itself in BiFunction.
     *
     * that eq "SELECT ... WHERE u_sct = 'MASK${User.secret}MASK'"
     */
    Class<?> mapFun() default Void.class;

    /**
     * Whether to open Boolean to constant function.
     * trueToConst(), trueToConstType(), falseToConst(), falseToConstType() methods is effective
     * only when the value is true.
     */
    boolean openBooleanToConst() default false;
    /**
     * A bool value "ture" can be converted to a constant using this method
     */
    String trueToConst() default "";

    Class<?> trueToConstType() default Void.class;

    /**
     * A bool value "false" can be converted to a constant using this method
     */
    String falseToConst() default "";

    Class<?> falseToConstType() default Void.class;
}
