package club.kingon.sql.builder.spring.mybatisplus.util;

import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 联表辅助工具类
 * @author dragons
 * @date 2022/3/21 19:40
 */
public class MergeMapHelper {

    public static <T>T mapToBean(Map<String, Object> map, Class<T> clazz) {
        try {
            Constructor<T> c = clazz.getDeclaredConstructor();
            if (!c.isAccessible()) {
                c.setAccessible(true);
            }
            T obj = c.newInstance();
            BeanMap beanMap = BeanMap.create(obj);
            beanMap.putAll(map);
            return obj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Class '" + clazz.getName() + "' failed to create object through no arguments construction method", e);
        }
    }

    public static <T>List<T> mapToBean(List<Map<String, Object>> mapList, Class<T> clazz) {
        return mapList.stream().map(e -> mapToBean(e, clazz)).collect(Collectors.toList());
    }
}
