package club.kingon.sql.builder.annotation;

import java.lang.annotation.*;

/**
 * @author dragons
 * @date 2021/12/21 13:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD})
@Documented
public @interface Queries {
    Query[] value() default {};
}
