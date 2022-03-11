package club.kingon.sql.builder;

import club.kingon.sql.builder.entry.Alias;
import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.InsertMode;
import club.kingon.sql.builder.enums.Operator;
import club.kingon.sql.builder.inner.ObjectMapperUtils;
import club.kingon.sql.builder.util.ConditionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Sql Builder top interface and a bootstrap.
 * @author dragons
 * @date 2021/11/9 18:28
 */
public interface SqlBuilder extends PreparedStatementSupport {

    Logger log = LoggerFactory.getLogger(SqlBuilder.class);

    /**
     * Core method. It defines how to build a sql or sql fragment.
     * @return Sql or sql fragment.
     */
    default String build() {
        return ConditionUtils.parseTemplate(precompileSql(), precompileArgs());
    }

    /**
     * @return A SelectSqlBuilder instance.
     */
    static SelectSqlBuilder selectAll() {
        return new SelectSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY, "*");
    }

    /**
     * @return A SelectSqlBuilder instance.
     */
    static SelectSqlBuilder select(String ...columns) {
        return new SelectSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY, columns);
    }

    /**
     * @return A SelectSqlBuilder instance.
     */
    static SelectSqlBuilder select(Alias ...columns) {
        return new SelectSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY, columns);
    }

    static SelectSqlBuilder select(Object ...columns) {
        return new SelectSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY, columns);
    }

    /**
     * Single table merge select and from
     * @return A FromSqlBuilder instance
     */
    static FromSqlBuilder model(Class<?> modelClass) {
        return new SelectSqlBuilder(null, Constants.EMPTY_OBJECT_ARRAY, modelClass).from(modelClass);
    }

    /**
     * @return A InsertSqlBuilder instance.
     */
    static InsertSqlBuilder insert(InsertMode mode, String table, String ...columns) {
        return new InsertSqlBuilder(table, mode, columns);
    }

    static <T> ValuesSqlBuilder insert(InsertMode mode, T ...models) {
        return insert(mode, Arrays.asList(models));
    }

    static <T> ValuesSqlBuilder insert(InsertMode mode, List<T> models) {
        if (models == null || models.isEmpty()) {
            throw new SqlBuilderException("The inserted model cannot be null.");
        }
        Class clazz = models.get(0).getClass();
        String tableName = ObjectMapperUtils.getTableName(clazz);
        List<Alias> fields = ObjectMapperUtils.getColumnFields(clazz);
        InsertSqlBuilder builder = insert(mode, tableName, fields.stream().map(Alias::getOrigin).toArray(String[]::new));
        return builder.values().addValues(
            models.stream().map(e -> {
                Object[] values = new Object[fields.size()];
                for (int i = 0; i < fields.size(); i++) {
                    try {
                        values[i] = ObjectMapperUtils.getFieldValue(e, fields.get(i).getAlias());
                    } catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
                        values[i] = null;
                        log.warn("The value of the inserted model field " + fields.get(i).getAlias() + " is not getting properly and is ignored.");
                    }
                }
                return values;
            }).collect(Collectors.toList())
        );
    }

    /**
     * @return A InsertSqlBuilder instance.
     */
    static InsertSqlBuilder insertInto(String table, String ...columns) {
        return new InsertSqlBuilder(table, InsertMode.INSERT_INTO, columns);
    }

    static <T> ValuesSqlBuilder insertInto(T ...models) {
        return insert(InsertMode.INSERT_INTO, models);
    }

    static <T> ValuesSqlBuilder insertInto(List<T> models) {
        return insert(InsertMode.INSERT_INTO, models);
    }

    /**
     * @return A InsertSqlBuilder instance.
     */
    static InsertSqlBuilder insertIgnore(String table, String ...columns) {
        return new InsertSqlBuilder(table, InsertMode.INSERT_IGNORE, columns);
    }

    static <T> ValuesSqlBuilder insertIgnore(T ...models) {
        return insert(InsertMode.INSERT_IGNORE, models);
    }

    static <T> ValuesSqlBuilder insertIgnore(List<T> models) {
        return insert(InsertMode.INSERT_IGNORE, models);
    }

    /**
     * @return A InsertSqlBuilder instance.
     */
    static InsertSqlBuilder insertOverwrite(String table, String ...columns) {
        return new InsertSqlBuilder(table, InsertMode.INSERT_OVERWRITE, columns);
    }

    static <T> ValuesSqlBuilder insertOverwrite(T ...models) {
        return insert(InsertMode.INSERT_OVERWRITE, models);
    }

    static <T> ValuesSqlBuilder insertOverwrite(List<T> models) {
        return insert(InsertMode.INSERT_OVERWRITE, models);
    }

    /**
     * @return A InsertSqlBuilder instance.
     */
    static InsertSqlBuilder replaceInto(String table, String ...columns) {
        return new InsertSqlBuilder(table, InsertMode.REPLACE_INTO, columns);
    }

    static <T> ValuesSqlBuilder replaceInto(T ...models) {
        return insert(InsertMode.REPLACE_INTO, models);
    }

    static <T> ValuesSqlBuilder replaceInto(List<T> models) {
        return insert(InsertMode.REPLACE_INTO, models);
    }

    /**
     * @return A UpdateSqlBuilder instance
     */
    static UpdateSqlBuilder update(String table) {
        return new UpdateSqlBuilder(table);
    }

    static <T> WhereSqlBuilder update(T model) {
        Class<?> clazz = model.getClass();
        String tableName = ObjectMapperUtils.getTableName(clazz);
        List<Alias> fields = ObjectMapperUtils.getColumnFields(clazz);
        List<Alias> primaries = ObjectMapperUtils.getPrimaries(clazz);
        Map<String, Object> primaryMapping = null;
        try {
            if (primaries == null || primaries.isEmpty()) {
                primaryMapping = Collections.emptyMap();
            } else {
                primaryMapping = ObjectMapperUtils.getColumnAndValues(model, primaries);
                fields.removeAll(primaries);
            }
            Map<String, Object> updateMapping = ObjectMapperUtils.getColumnAndValues(model, fields);

            if (updateMapping.isEmpty()) {
                throw new SqlBuilderException("At least one value of the updated field cannot be null.");
            }

            UpdateSqlBuilder updateSqlBuilder = update(tableName);
            SetSqlBuilder setSqlBuilder = null;
            for (Map.Entry<String, Object> entry : updateMapping.entrySet()) {
                if (setSqlBuilder == null) {
                    setSqlBuilder = updateSqlBuilder.set(entry.getKey(), entry.getValue());
                } else {
                    setSqlBuilder = setSqlBuilder.addSet(entry.getKey(), entry.getValue());
                }
            }

            WhereSqlBuilder whereSqlBuilder = null;
            if (!primaryMapping.isEmpty()) {
                for (Map.Entry<String, Object> entry : primaryMapping.entrySet()) {
                    if (whereSqlBuilder == null) {
                        whereSqlBuilder = setSqlBuilder.where(entry.getKey(), Operator.EQ, entry.getValue());
                    } else {
                        whereSqlBuilder = whereSqlBuilder.and(entry.getKey(), Operator.EQ, entry.getValue());
                    }
                }
            } else {
                whereSqlBuilder = setSqlBuilder.where("");
            }
            return whereSqlBuilder;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new SqlBuilderException("Obtaining the model value is abnormal", e);
        }
    }

    /**
     * @return A DeleteSqlBuilder instance
     */
    static DeleteSqlBuilder delete(String table) {
        return new DeleteSqlBuilder(table);
    }

    static <T> DeleteSqlBuilder delete(T model) {
        Class<?> clazz = model.getClass();
        return new DeleteSqlBuilder(ObjectMapperUtils.getTableName(clazz));
    }
}
