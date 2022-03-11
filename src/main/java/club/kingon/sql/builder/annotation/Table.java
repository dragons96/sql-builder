package club.kingon.sql.builder.annotation;

import java.lang.annotation.*;

/**
 * @author dragons
 * @date 2021/12/8 18:27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface Table {
    /**
     * The mapping table name.
     */
    String value() default "";
}
