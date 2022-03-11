package club.kingon.sql.builder.annotation;

import java.lang.annotation.*;

/**
 * @author dragons
 * @date 2021/12/8 18:26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Documented
public @interface Column {
    /**
     * The mapping column name.
     */
    String value() default "";

    /**
     * A java.util.Function impl class.
     * You can Define a function to convert data values.
     */
    Class<?> readMapFun() default Void.class;
}
