package club.kingon.sql.builder.inner;

import club.kingon.sql.builder.LambdaException;
import club.kingon.sql.builder.SqlBuilderException;
import club.kingon.sql.builder.Tuple2;
import club.kingon.sql.builder.annotation.Column;
import club.kingon.sql.builder.annotation.Primary;
import club.kingon.sql.builder.annotation.Table;
import club.kingon.sql.builder.entry.Alias;

import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 内部专用
 * @author dragons
 * @date 2021/12/7 14:09
 */
public class ObjectMapperUtils {

    private ObjectMapperUtils() {}

    private static String sep = "_";

    private final static Map<Class<?>, ClassMetadata> CLASS_METADATA_MAP = new ConcurrentHashMap<>();

    private final static Map<Class<?>, List<Alias>> PRIMARY_MAP = new ConcurrentHashMap<>();

    private final static Map<String, Constructor> CONSTRUCTOR_MAP = new ConcurrentHashMap<>();

    private final static Map<String, Object> SINGLE_OBJECT_MAP = new ConcurrentHashMap<>();

    private final static Map<String, String> CLASS_FIELD_COLUMN_MAP = new ConcurrentHashMap<>();

    public static String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            return humpNameToUnderlinedName(clazz.getSimpleName(), sep);
        }
        return table.value();
    }

    public static List<String> getColumns(Class<?> clazz) {
        return getColumnFields(clazz).stream().map(Alias::getOrigin).collect(Collectors.toList());
    }

    public static <T extends Annotation> T getAnnotation(Class<?> clazz, String fieldName, Class<T> annotationClass) {
        ClassMetadata meta = getClassMeta(clazz, Collections.emptyList(), Collections.emptyList());
        Field field = meta.getField(fieldName);
        T annotation = null;
        if (field != null) {
            annotation = field.getAnnotation(annotationClass);
        }
        if (annotation == null) {
            Method getterMethod = meta.getGetterMethod(fieldName);
            if (getterMethod != null) {
                annotation = getterMethod.getAnnotation(annotationClass);
            }
            if (annotation == null) {
                Method setterMethod = meta.getSetterMethod(fieldName);
                if (setterMethod != null) {
                    annotation = setterMethod.getAnnotation(annotationClass);
                }
            }
        }
        return annotation;
    }

    public static List<Alias> getColumnFields(Class<?> clazz) {
        ClassMetadata meta = getClassMeta(clazz, Collections.emptyList(), Collections.emptyList());
        if (meta == null) return Collections.emptyList();
        List<Alias> columns = new ArrayList<>();
        meta.getFields().stream().filter(e -> !Modifier.isFinal(e.getModifiers())).forEach(e -> {
            String columnName = getColumnName(clazz, e.getName()), aliasName = e.getName();
            columns.add(Alias.of(columnName, aliasName));
        });
        return columns;
    }

    public static Object getAttr(Object obj, String[] attr, int index) throws IllegalAccessException, InvocationTargetException {
        if (obj == null) {
            return null;
        }
        Object result = obj;
        if (obj instanceof Collection) {
            Collection o = (Collection) obj;
            if (o.isEmpty()) return null;
            result = o.stream().map(e -> ObjectMapperUtils.handleSubGetAttr(e, attr, index)).filter(Objects::nonNull).collect(Collectors.toList());
        } else if (obj instanceof Map) {
            result = ((Map<?, ?>) obj).get(attr);
        } else {
            result = getFieldValue(obj, attr[index]);
        }
        if (index == attr.length - 1) {
            return result;
        }
        return getAttr(result, attr, index + 1);
    }

    private static Object handleSubGetAttr(Object obj, String[] attr, int index) {
        try {
            return getAttr(obj, attr, index);
        } catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
            throw new SqlBuilderException("Attribute acquisition failure, type: \"" + obj.getClass().getName() + "\", attr: " + Arrays.toString(attr), illegalAccessException);
        }
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        ClassMetadata classMeta = getClassMeta(clazz, Collections.emptyList(), Collections.emptyList());
        if (classMeta == null) return null;
        return classMeta.getField(fieldName);
    }

    public static <T, V> V getFieldValue(T obj, String fieldName) throws IllegalAccessException, InvocationTargetException {
        ClassMetadata classMeta = getClassMeta(obj.getClass(), Collections.emptyList(), Collections.emptyList());
        if (classMeta == null) return null;

        Field field = classMeta.getField(fieldName);

        if (field == null) {
            Method method = classMeta.getGetterMethod(fieldName);
            if (method != null) {
                return (V) method.invoke(obj);
            } else {
                return null;
            }
        }

        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return (V) field.get(obj);
    }

    public static String getColumnName(Class<?> clazz, String fieldName) {
        String classFieldId = clazz.getName() + "@" + fieldName;
        String columnName = CLASS_FIELD_COLUMN_MAP.get(classFieldId);
        if (columnName == null) {
            Column column = getAnnotation(clazz, fieldName, Column.class);
            if (column != null) {
                columnName = column.value();
            } else {
                columnName = humpNameToUnderlinedName(fieldName, sep);
            }
            CLASS_FIELD_COLUMN_MAP.put(classFieldId, columnName);
        }
        return columnName;
    }

    public static<T> T getInstance(Class<T> clazz, Class<?>[] parameterTypes, Object ...params) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = getConstructor(clazz, parameterTypes);
        return constructor.newInstance(params);
    }

    public static Object getSingleObject(Class<?> clazz, Object ...args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String key = clazz.getName() + "@" + Arrays.stream(args).map(e -> e.getClass().getName()).collect(Collectors.joining("#"));
        Object obj = SINGLE_OBJECT_MAP.get(key);
        if (obj == null) {
            synchronized (SINGLE_OBJECT_MAP) {
                obj = SINGLE_OBJECT_MAP.get(key);
                if (obj == null) {
                    if (args.length == 0) {
                        obj = clazz.getConstructor().newInstance();
                    } else {
                        obj = clazz.getConstructor(Arrays.stream(args).map(Object::getClass).toArray(Class[]::new));
                    }
                    SINGLE_OBJECT_MAP.put(key, obj);
                }
            }
        }
        return obj;
    }

    public static List<Alias> getPrimaries(Class<?> clazz) {
        List<Alias> primaries = PRIMARY_MAP.get(clazz);
        if (primaries == null) {
            synchronized (PRIMARY_MAP) {
                primaries = PRIMARY_MAP.get(clazz);
                if (primaries == null) {
                    ClassMetadata classMeta = getClassMeta(clazz, Collections.emptyList(), Collections.emptyList());
                    if (classMeta == null) return null;
                    List<Field> fields = classMeta.getFields();
                    primaries = fields.stream().filter(e -> getAnnotation(clazz, e.getName(), Primary.class) != null).map(e -> Alias.of(getColumnName(clazz, e.getName()), e.getName())).distinct().collect(Collectors.toList());
                    PRIMARY_MAP.put(clazz, primaries);
                }
            }
        }
        return primaries;
    }

    public static<T> Map<String, Object> getColumnAndValues(T obj, List<Alias> fields) throws IllegalAccessException, InvocationTargetException {
        if (fields == null || fields.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Object> fieldMapping = new HashMap<>();
        for (Alias field : fields) {
            Object value = getFieldValue(obj, field.getAlias());
            if (value != null) {
                fieldMapping.put(field.getOrigin(), value);
            }
        }
        return fieldMapping;
    }

    public static String humpNameToUnderlinedName(String name, String sep) {
        StringBuilder nameBuilder = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                if (nameBuilder.length() > 0) {
                    nameBuilder.append(sep);
                }
                nameBuilder.append((char) (c + 32));
            } else {
                nameBuilder.append(c);
            }
        }
        return nameBuilder.toString();
    }

    public static Tuple2<String, String> getLambdaImplementClassAndMethodName(Object lambda) {
        Method method;
        try {
            method = lambda.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda)method.invoke(lambda);
            return Tuple2.of(serializedLambda.getImplClass().replace("/", "."), serializedLambda.getImplMethodName());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new LambdaException("lambda cannot extract valid implementation method name.", e);
        }
    }

    protected static<T> Constructor<T> getConstructor(Class<T> clazz, Class<?>[] parameterTypes) throws NoSuchMethodException {
        String key = clazz.getName() + "@" + Arrays.stream(parameterTypes).map(Class::getName).collect(Collectors.joining("#"));
        Constructor<T> constructor = CONSTRUCTOR_MAP.get(key);
        if (constructor == null) {
            synchronized (CONSTRUCTOR_MAP) {
                constructor = CONSTRUCTOR_MAP.get(key);
                if (constructor == null) {
                    constructor = clazz.getDeclaredConstructor(parameterTypes);
                    if (!constructor.isAccessible()) {
                        constructor.setAccessible(true);
                    }
                    CONSTRUCTOR_MAP.put(key, constructor);
                }
            }
        }
        return constructor;
    }

    protected static ClassMetadata getClassMeta(Class<?> clazz, List<String> ignoreFields, List<String> ignoreMethods) {
        ClassMetadata classMetadata = CLASS_METADATA_MAP.get(clazz);
        if (classMetadata == null) {
            synchronized (CLASS_METADATA_MAP) {
                classMetadata = CLASS_METADATA_MAP.get(clazz);
                if (classMetadata == null) {
                    classMetadata = new ClassMetadata(clazz, ignoreFields, ignoreMethods);
                    CLASS_METADATA_MAP.put(clazz, classMetadata);
                }
            }
        }
        return classMetadata;
    }
}
