package club.kingon.sql.builder.spring.mybatisplus.wrapper;

import club.kingon.sql.builder.SqlBuilder;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author dragons
 * @date 2022/1/28 12:21
 */
public class SimpleSqlBuilderQueryWrapper<T> extends Wrapper<T>
    implements Query<SimpleSqlBuilderQueryWrapper<T>, T, Object> {

    private final SqlBuilder sqlBuilder;

    private String exprSql;

    private final SharedString sqlSelect = new SharedString();

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
    public SimpleSqlBuilderQueryWrapper<T> select(Object... columnOrAlias) {
        if (ArrayUtils.isNotEmpty(columnOrAlias)) {
            this.sqlSelect.setStringValue(Arrays.stream(columnOrAlias).map(Object::toString).collect(Collectors.joining(StringPool.COMMA)));
        }
        return this;
    }

    @Override
    public SimpleSqlBuilderQueryWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        this.sqlSelect.setStringValue(TableInfoHelper.getTableInfo(entityClass).chooseSelect(predicate));
        return this;
    }

    @Override
    public String getSqlSelect() {
        return sqlSelect.getStringValue();
    }
}
