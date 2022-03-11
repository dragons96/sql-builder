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
     */
    String value() default "";

    /**
     * Relationship types.
     */
    Operator type() default Operator.EQ;

    /**
     * Mapping field name.
     * It that some simple scenarios can be used. Please complex scenarios use mapFun().
     */
    String attr() default "";

    /**
     * Mapping function.
     * The class must be implemented for java.util.Function or java.util.BiFunction and Contains empty constructor.
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
