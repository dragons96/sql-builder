package club.kingon.sql.builder.entry;

import java.util.function.Supplier;

/**
 * @author dragons
 * @date 2021/11/10 17:00
 */
public interface Constants {

    String EMPTY_STRING = "";

    Supplier<Boolean> TRUE_PREDICATE = () -> true;

    Supplier<Boolean> FALSE_PREDICATE = () -> false;

    Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    Class[] CONDITION_CONSTRUCTOR_PARAMETER_TYPES = new Class[]{Boolean.class, String.class, Object[].class, String.class, Object[].class};
}
