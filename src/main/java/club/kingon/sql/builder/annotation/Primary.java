package club.kingon.sql.builder.annotation;

import java.lang.annotation.*;

/**
 * @author dragons
 * @date 2021/12/7 17:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Documented
public @interface Primary {
}
