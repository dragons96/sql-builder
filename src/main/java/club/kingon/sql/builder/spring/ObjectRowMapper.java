package club.kingon.sql.builder.spring;

import club.kingon.sql.builder.annotation.Column;
import club.kingon.sql.builder.inner.ObjectMapperUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


/**
 * @author dragons
 * @date 2021/11/12 18:27
 */
public class ObjectRowMapper<T> extends BeanPropertyRowMapper<T> {

    private final static Map<Class, Map<String, Field>> CLASS_FIELD_MAP = new ConcurrentHashMap<>();

    private final static Map<Field, String> CACHE_FIELD_COLUMN_NAME = new ConcurrentHashMap<>();

    private static Field UNDEFINED_FIELD;

    private final Class<T> mapperClass;

    static {
        try {
            UNDEFINED_FIELD = ObjectRowMapper.class.getDeclaredField("CLASS_FIELD_MAP");
        } catch (NoSuchFieldException e) {
        }
    }

    public ObjectRowMapper(Class<T> mappedClass) {
        super(mappedClass);
        this.mapperClass = mappedClass;
    }

    @Override
    protected String underscoreName(String name) {
        Class<T> mappedClass = getMappedClass();
        Map<String, Field> fieldMap = getFieldMap(mappedClass);
        Field field = fieldMap.get(name);
        if (field == null) {
            field = getField(mappedClass, name);
            if (field != null) {
                fieldMap.put(name, field);
                return getColumnName(field, mappedClass);
            } else {
                fieldMap.put(name, UNDEFINED_FIELD);
            }
        } else if (field != UNDEFINED_FIELD) {
            return getColumnName(field, mappedClass);
        }
        return super.underscoreName(name);
    }

    @Override
    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
        Field field = ObjectMapperUtils.getField(mapperClass, pd.getName());
        Column column = null;
        // begin field
        if (field != null) {
            column = field.getAnnotation(Column.class);
        }
        // if field has not @Column, get from Method.
        if (column == null) {
            Method getterMethod = pd.getReadMethod(), setterMethod = pd.getWriteMethod();
            if ((column = getterMethod.getAnnotation(Column.class)) == null) {
                column = setterMethod.getAnnotation(Column.class);
            }
        }
        // @Column has effective Function.
        if (column != null && Function.class.isAssignableFrom(column.readMapFun())) {
            Object val = JdbcUtils.getResultSetValue(rs, index, Object.class);
            try {
                Function fun = (Function) ObjectMapperUtils.getSingleObject(column.readMapFun());
                return fun.apply(val);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("@Column readMapFun() invoke fail.", e);
            }
        }
        return super.getColumnValue(rs, index, pd);
    }

    protected String getColumnName(Field field, Class<?> clazz) {
        String columnName = getColumnAnnotationName(field, clazz);
        if (columnName != null) return columnName;
        return super.underscoreName(field.getName());
    }

    private String getColumnAnnotationName(Field field, Class<?> clazz) {
        String columnName = CACHE_FIELD_COLUMN_NAME.get(field);
        if (columnName == null) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                CACHE_FIELD_COLUMN_NAME.put(field, column.value());
                return column.value();
            } else {
                PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, field.getName());
                if (pd != null) {
                    Method readMethod = pd.getReadMethod(), writeMethod = pd.getWriteMethod();
                    if (readMethod != null && (column = readMethod.getAnnotation(Column.class)) != null) {
                        CACHE_FIELD_COLUMN_NAME.put(field, column.value());
                        return column.value();
                    }
                    if (writeMethod != null && (column = writeMethod.getAnnotation(Column.class)) != null) {
                        CACHE_FIELD_COLUMN_NAME.put(field, column.value());
                        return column.value();
                    }
                }
                // save empty string different from null.
                CACHE_FIELD_COLUMN_NAME.put(field, "");
                return null;
            }
        } else if (columnName.length() > 0) return columnName;
        return null;
    }

    private Field getField(Class<?> clazz, String fieldName) {
        while (clazz != Object.class) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    private Map<String, Field> getFieldMap(Class<?> clazz) {
        Map<String, Field> fieldMap = CLASS_FIELD_MAP.get(clazz);
        if (fieldMap == null) {
            synchronized (CLASS_FIELD_MAP) {
                fieldMap = CLASS_FIELD_MAP.get(clazz);
                if (fieldMap == null) {
                    fieldMap = new ConcurrentHashMap<>();
                    CLASS_FIELD_MAP.put(clazz, fieldMap);
                }
            }
        }
        return fieldMap;
    }
}
