package club.kingon.sql.builder.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author dragons
 * @date 2021/12/21 13:01
 */
public class CastUtils {

    public static <T> T strTo(String origin, Class<T> clazz) {
        if (origin == null) return null;
        if (clazz == String.class) return (T) origin;
        if (clazz == Byte.class) return (T) Byte.valueOf(origin);
        if (clazz == Short.class) return (T) Short.valueOf(origin);
        if (clazz == Integer.class) return (T) Integer.valueOf(origin);
        if (clazz == Long.class) return (T) Long.valueOf(origin);
        if (clazz == BigInteger.class) return (T) new BigInteger(origin);
        if (clazz == Double.class) return (T) Double.valueOf(origin);
        if (clazz == Float.class) return (T) Float.valueOf(origin);
        if (clazz == BigDecimal.class) return (T) new BigDecimal(origin);
        if (clazz == Boolean.class) return (T) Boolean.valueOf(origin);
        throw new UnsupportedOperationException("Converting string type to bean type is not supported");
    }

}
