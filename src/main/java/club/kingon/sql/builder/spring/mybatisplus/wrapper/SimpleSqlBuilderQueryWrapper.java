package club.kingon.sql.builder.spring.mybatisplus.wrapper;

import club.kingon.sql.builder.LMDFunction;
import club.kingon.sql.builder.SelectSqlBuilder;
import club.kingon.sql.builder.SqlBuilder;
import club.kingon.sql.builder.entry.Alias;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author dragons
 * @date 2022/1/28 12:21
 */
public class SimpleSqlBuilderQueryWrapper<T> extends Wrapper<T>
    implements Query<SimpleSqlBuilderQueryWrapper<T>, T, Object> {

    private final SqlBuilder sqlBuilder;

    private String exprSql;

    private SelectSqlBuilder selectSqlBuilder;

    private Map<String, Object> paramNameValuePairs;

    private final static String SQL_EXPR_NAME_DEFAULT = "ARG_NAME";

    public SimpleSqlBuilderQueryWrapper(SqlBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }

    @Override
    public T getEntity() {
        return null;
    }

    @Override
    public MergeSegments getExpression() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean isEmptyOfWhere() {
        return true;
    }

    @Override
    public String getSqlSegment() {
        if (exprSql == null) {
            compileExprSql();
        }
        return exprSql;
    }

    public void compileExprSql() {
        String precompileSql = sqlBuilder.precompileSql();
        Object[] precompileArgs = sqlBuilder.precompileArgs();
        if (precompileArgs == null || precompileArgs.length == 0) {
            exprSql = precompileSql;
        } else {
            paramNameValuePairs = new HashMap<>();
            int qm1 = 0, qm2 = 0, count = 0;
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (; i < precompileSql.length() && count < precompileArgs.length; i++) {
                char c = precompileSql.charAt(i);
                if (qm1 == 0 && qm2 == 0 && c == '?') {
                    String expName = SQL_EXPR_NAME_DEFAULT + count;
                    sb.append("#{").append("ew.paramNameValuePairs.").append(expName).append("}");
                    paramNameValuePairs.put(expName, precompileArgs[count++]);
                } else if (i > 0) {
                    if (qm2 == 0 && c == '\'' && precompileSql.charAt(i - 1) != '\\') {
                        qm1 = 1 - qm1;
                    } else if (qm1 == 0 && c == '"' && precompileSql.charAt(i - 1) != '\\') {
                        qm2 = 1 - qm2;
                    }
                    sb.append(c);
                }
            }
            if (i < precompileSql.length()) {
                sb.append(precompileSql.substring(i));
            }
            exprSql = sb.toString();
        }
    }

    public Map<String, Object> getParamNameValuePairs() {
        if (paramNameValuePairs == null) {
            compileExprSql();
        }
        return paramNameValuePairs;
    }

    @Override
    public SimpleSqlBuilderQueryWrapper select(Object... columnOrAliasOrClass) {
        if (selectSqlBuilder == null) {
            selectSqlBuilder = SqlBuilder.select(columnOrAliasOrClass);
        } else {
            for (Object e : columnOrAliasOrClass) {
                if (e instanceof String) {
                    selectSqlBuilder.addColumn((String) e);
                } else if (e instanceof Alias) {
                    selectSqlBuilder.addColumn((Alias) e);
                } else if (e instanceof LMDFunction) {
                    selectSqlBuilder.addColumn((LMDFunction<?, ?>) e);
                }
            }
        }
        return this;
    }

    @Override
    public SimpleSqlBuilderQueryWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        if (selectSqlBuilder == null) {
            selectSqlBuilder = SqlBuilder.select(TableInfoHelper.getTableInfo(entityClass).chooseSelect(predicate));
        } else {
            this.selectSqlBuilder.addColumn(TableInfoHelper.getTableInfo(entityClass).chooseSelect(predicate));
        }
        return this;
    }

    @Override
    public String getSqlSelect() {
        if (selectSqlBuilder == null) {
            return null;
        }
        return selectSqlBuilder.build().substring(6);
    }
}
