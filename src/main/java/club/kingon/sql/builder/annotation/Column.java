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
     * For example, it is a milliseconds timestamp in database, and can be converted to LocalDateTime in java:
     * <blockquote><pre>
     *     class User {
     *         @Column(value="add_time", readMapFun=TimestampToLocalDateTimeFunction.class)
     *         LocalDateTime datetime;
     *     }
     *
     *     class TimestampToLocalDateTimeFunction implement Function<Long, LocalDateTime> {
     *
     *         public LocalDateTime apply(Long timestamp) {
     *             return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.of("+8"));
     *         }
     *
     *     }
     * </pre></blockquote>
     */
    Class<?> readMapFun() default Void.class;
}
