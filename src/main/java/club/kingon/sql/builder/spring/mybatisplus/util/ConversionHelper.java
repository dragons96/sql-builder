package club.kingon.sql.builder.spring.mybatisplus.util;

import club.kingon.sql.builder.LMDFunction;
import club.kingon.sql.builder.Tuple2;
import club.kingon.sql.builder.entry.Alias;
import club.kingon.sql.builder.inner.ObjectMapperUtils;
import club.kingon.sql.builder.util.LambdaUtils;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 联表辅助工具类
 * @author dragons
 * @date 2022/3/21 19:40
 */
public class ConversionHelper {

    /**
     * Map to JavaBean
     */
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

    /**
     * Batch maps to JavaBeans
     */
    public static <T>List<T> mapToBean(List<Map<String, Object>> mapList, Class<T> clazz) {
        return mapList.stream().map(e -> mapToBean(e, clazz)).collect(Collectors.toList());
    }

    private final static Map<Class<?>, Collector> CLASS_COLLECTOR_MAP = new HashMap<>();

    static {
        CLASS_COLLECTOR_MAP.put(List.class, Collectors.toList());
        CLASS_COLLECTOR_MAP.put(Set.class, Collectors.toSet());
    }

    /**
     * Batch maps to "one to one" JavaBeans
     */
    public static <ONE, ANOTHER_ONE>List<ONE> mapToBeanOne(List<Map<String, Object>> mapList, Class<ONE> oneClass, String anotherOneFieldName, Function<Map<String, Object>, ANOTHER_ONE> toAnotherOneFun) {
        return mapToBeanOne(mapList, map -> mapToBean(map, oneClass), anotherOneFieldName, toAnotherOneFun);
    }

    /**
     * Batch maps to "one to one" JavaBeans
     */
    public static <ONE, ANOTHER_ONE>List<ONE> mapToBeanOne(List<Map<String, Object>> mapList, Class<ONE> oneClass, LMDFunction<ONE, ?> anotherOneField, Function<Map<String, Object>, ANOTHER_ONE> toAnotherOneFun) {
        return mapToBeanOne(mapList, oneClass, LambdaUtils.getFieldName(anotherOneField), toAnotherOneFun);
    }

    /**
     * Batch maps to "one to one" JavaBeans
     */
    public static <ONE, ANOTHER_ONE>List<ONE> mapToBeanOne(List<Map<String, Object>> mapList, Class<ONE> oneClass, String anotherOneFieldName, Function<Map<String, Object>, ANOTHER_ONE> toAnotherOneFun, Predicate<ANOTHER_ONE> anotherOnePredicate) {
        return mapToBeanOne(mapList, map -> mapToBean(map, oneClass), anotherOneFieldName, toAnotherOneFun, anotherOnePredicate);
    }

    /**
     * Batch maps to "one to one" JavaBeans
     */
    public static <ONE, ANOTHER_ONE>List<ONE> mapToBeanOne(List<Map<String, Object>> mapList, Class<ONE> oneClass, LMDFunction<ONE, ?> anotherOneField, Function<Map<String, Object>, ANOTHER_ONE> toAnotherOneFun, Predicate<ANOTHER_ONE> anotherOnePredicate) {
        return mapToBeanOne(mapList, oneClass, LambdaUtils.getFieldName(anotherOneField), toAnotherOneFun, anotherOnePredicate);
    }


    /**
     * Batch maps to "one to one" JavaBeans
     */
    public static <ONE, ANOTHER_ONE>List<ONE> mapToBeanOne(List<Map<String, Object>> mapList, Function<Map<String, Object>, ONE> toOneFun, String anotherOneFieldName, Function<Map<String, Object>, ANOTHER_ONE> toAnotherOneFun) {
        return mapToBeanOne(mapList, toOneFun, anotherOneFieldName, toAnotherOneFun, e -> true);
    }

    /**
     * Batch maps to "one to one" JavaBeans
     */
    public static <ONE, ANOTHER_ONE>List<ONE> mapToBeanOne(List<Map<String, Object>> mapList, Function<Map<String, Object>, ONE> toOneFun, String anotherOneFieldName, Function<Map<String, Object>, ANOTHER_ONE> toAnotherOneFun, Predicate<ANOTHER_ONE> anotherOnePredicate) {
        if (mapList == null || mapList.isEmpty()) return Collections.emptyList();
        Class<ONE> oneClass = (Class<ONE>) toOneFun.apply(mapList.get(0)).getClass();
        List<Alias> columnFields = ObjectMapperUtils.getColumnFields(oneClass);
        return getGroupStream(mapList, columnFields, toAnotherOneFun).map(e -> {
            ONE oneBean = toOneFun.apply(e.get(0)._1);
            handleAnotherOne(oneBean, anotherOneFieldName, e.stream().map(t -> t._2).filter(anotherOnePredicate));
            return oneBean;
        }).collect(Collectors.toList());
    }

    /**
     * Batch maps to "one to one" JavaBeans
     */
    public static <ONE, ANOTHER_ONE>List<ONE> mapToBeanOne(List<Map<String, Object>> mapList, Function<Map<String, Object>, ONE> toOneFun, LMDFunction<ONE, ?> anotherOneField, Function<Map<String, Object>, ANOTHER_ONE> toAnotherOneFun, Predicate<ANOTHER_ONE> anotherOnePredicate) {
        return mapToBeanOne(mapList, toOneFun, LambdaUtils.getFieldName(anotherOneField), toAnotherOneFun, anotherOnePredicate);
    }


    /**
     * Batch maps to "one to one" JavaBeans
     */
    public static <ONE, ANOTHER_ONE>List<ONE> mapToBeanOne(List<Map<String, Object>> mapList, Function<Map<String, Object>, ONE> toOneFun, LMDFunction<ONE, ?> anotherOneField, Function<Map<String, Object>, ANOTHER_ONE> toAnotherOneFun) {
        return mapToBeanOne(mapList, toOneFun, LambdaUtils.getFieldName(anotherOneField), toAnotherOneFun);
    }

    /**
     * Batch maps to "one to many" JavaBeans
     */
    public static <ONE, MANY> List<ONE> mapToBeanMany(List<Map<String, Object>> mapList, Class<ONE> oneClass, String manyFieldName, Function<Map<String, Object>, MANY> toManyFun) {
        return mapToBeanMany(mapList, map -> mapToBean(map, oneClass), manyFieldName, toManyFun);
    }

    /**
     * Batch maps to "one to many" JavaBeans
     */
    public static <ONE, MANY> List<ONE> mapToBeanMany(List<Map<String, Object>> mapList, Class<ONE> oneClass, LMDFunction<ONE, ?> manyField, Function<Map<String, Object>, MANY> toManyFun) {
        return mapToBeanMany(mapList, oneClass, LambdaUtils.getFieldName(manyField), toManyFun);
    }

    /**
     * Batch maps to "one to many" JavaBeans
     */
    public static <ONE, MANY> List<ONE> mapToBeanMany(List<Map<String, Object>> mapList, Class<ONE> oneClass, String manyFieldName, Function<Map<String, Object>, MANY> toManyFun, Predicate<MANY> manyPredicate) {
        return mapToBeanMany(mapList, map -> mapToBean(map, oneClass), manyFieldName, toManyFun, manyPredicate);
    }

    /**
     * Batch maps to "one to many" JavaBeans
     */
    public static <ONE, MANY> List<ONE> mapToBeanMany(List<Map<String, Object>> mapList, Class<ONE> oneClass, LMDFunction<ONE, ?> manyField, Function<Map<String, Object>, MANY> toManyFun, Predicate<MANY> manyPredicate) {
        return mapToBeanMany(mapList, oneClass, LambdaUtils.getFieldName(manyField), toManyFun, manyPredicate);
    }


    /**
     * Batch maps to "one to many" JavaBeans
     */
    public static <ONE, MANY> List<ONE> mapToBeanMany(List<Map<String, Object>> mapList, Function<Map<String, Object>, ONE> toOneFun, String manyFieldName, Function<Map<String, Object>, MANY> toManyFun) {
        return mapToBeanMany(mapList, toOneFun, manyFieldName, toManyFun, e -> true);
    }

    /**
     * Batch maps to "one to many" JavaBeans
     */
    public static <ONE, MANY> List<ONE> mapToBeanMany(List<Map<String, Object>> mapList, Function<Map<String, Object>, ONE> toOneFun, LMDFunction<ONE, ?> manyField, Function<Map<String, Object>, MANY> toManyFun, Predicate<MANY> manyPredicate) {
        return mapToBeanMany(mapList, toOneFun, LambdaUtils.getFieldName(manyField), toManyFun, manyPredicate);
    }

    /**
     * Batch maps to "one to many" JavaBeans
     */
    public static <ONE, MANY> List<ONE> mapToBeanMany(List<Map<String, Object>> mapList, Function<Map<String, Object>, ONE> toOneFun, String manyFieldName, Function<Map<String, Object>, MANY> toManyFun, Predicate<MANY> manyPredicate) {
        if (mapList == null || mapList.isEmpty()) return Collections.emptyList();
        Class<ONE> oneClass = (Class<ONE>) toOneFun.apply(mapList.get(0)).getClass();
        Field field = ObjectMapperUtils.getField(oneClass, manyFieldName);
        if (field == null) {
            throw new RuntimeException("Cannot find many field");
        }
        Class<?> declaringClass = field.getType();
        if (!Collection.class.isAssignableFrom(declaringClass)) {
            throw new RuntimeException("Field type is not a collection");
        }
        List<Alias> columnFields = ObjectMapperUtils.getColumnFields(oneClass);
        return getGroupStream(mapList, columnFields, toManyFun).map(e -> {
            ONE oneBean = toOneFun.apply(e.get(0)._1);
            handleMany(oneBean, manyFieldName, declaringClass, e.stream().map(t -> t._2).filter(manyPredicate));
            return oneBean;
        }).collect(Collectors.toList());
    }

    /**
     * Batch maps to "one to many" JavaBeans
     */
    public static <ONE, MANY> List<ONE> mapToBeanMany(List<Map<String, Object>> mapList, Function<Map<String, Object>, ONE> toOneFun, LMDFunction<ONE, ?> manyField, Function<Map<String, Object>, MANY> toManyFun) {
        return mapToBeanMany(mapList, toOneFun, LambdaUtils.getFieldName(manyField), toManyFun);
    }

    private static <Q>Stream<List<Tuple2<Map<String, Object>, Q>>> getGroupStream(List<Map<String, Object>> mapList, List<Alias> columnFields, Function<Map<String, Object>, Q> toFun) {
        return mapList.stream().map(map -> {
            // 使用 map 分组防止用户定义的 bean 未重写 equals 方法
            Map<String, Object> oneMap = new HashMap<>();
            columnFields.forEach(columnField -> oneMap.put(columnField.getOrigin(), map.get(columnField.getOrigin())));
            return Tuple2.of(oneMap, toFun.apply(map));
        }).collect(Collectors.groupingBy(t -> t._1)).values().stream();
    }

    private static <ONE, ANOTHER_ONE>void handleAnotherOne(ONE oneBean, String anotherOneFieldName, Stream<ANOTHER_ONE> anotherOneStream) {
        List<ANOTHER_ONE> list = anotherOneStream.collect(Collectors.toList());
        if (list.size() == 1) {
            try {
                ObjectMapperUtils.setFieldValue(oneBean, anotherOneFieldName, list.get(0));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Another one field cannot get assignment permission", e);
            }
        } else if (list.size() > 1){
            throw new RuntimeException("Other one field value size is greater than 1");
        }
    }

    private static <ONE, MANY>void handleMany(ONE oneBean, String manyFieldName, Class<?> manyFieldClass, Stream<MANY> manyStream) {
        if (List.class == manyFieldClass || Set.class == manyFieldClass) {
            try {
                ObjectMapperUtils.setFieldValue(oneBean, manyFieldName, manyStream.collect(CLASS_COLLECTOR_MAP.get(manyFieldClass)));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Many field cannot get assignment permission", e);
            }
        } else if (!manyFieldClass.isInterface()){
            try {
                Collection<MANY> f = (Collection<MANY>) manyFieldClass.newInstance();
                f.addAll(manyStream.collect(Collectors.toList()));
                ObjectMapperUtils.setFieldValue(oneBean, manyFieldName, f);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Collection initialization failed", e);
            }
        }
    }
}
