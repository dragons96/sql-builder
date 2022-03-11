package club.kingon.sql.builder;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author dragons
 * @date 2022/3/10 15:57
 */
@FunctionalInterface
public interface LMDFunction<T, R> extends Function<T, R>, Serializable {
}
