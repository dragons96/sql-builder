package club.kingon.sql.builder.spring.util;

import club.kingon.sql.builder.spring.ObjectRowMapper;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dragons
 * @date 2021/12/6 18:26
 */
public class MapperUtils {

    private MapperUtils() {}

    public static final Map<Class, ObjectRowMapper> OBJECT_ROW_MAPPER_MAP = new ConcurrentHashMap<>();

    private final static Object[] LOCKS = new Object[]{new Object(), new Object(), new Object(), new Object(), new Object(), new Object(), new Object(), new Object()};

    public static <T> ObjectRowMapper<T> getMapper(Class<T> clazz) {
        ObjectRowMapper<T> mapper = OBJECT_ROW_MAPPER_MAP.get(clazz);
        if (mapper == null) {
            synchronized (LOCKS[Math.abs(Objects.hash(clazz.getName())) % LOCKS.length]) {
                mapper = OBJECT_ROW_MAPPER_MAP.get(clazz);
                if (mapper == null) {
                    mapper = new ObjectRowMapper<>(clazz);
                    OBJECT_ROW_MAPPER_MAP.put(clazz, mapper);
                }
            }
        }
        return mapper;
    }
}
