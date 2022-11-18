package club.kingon.sql.builder.util;

import club.kingon.sql.builder.SqlBuilder;
import club.kingon.sql.builder.Tuple2;
import club.kingon.sql.builder.entry.Column;
import club.kingon.sql.builder.entry.Constants;
import club.kingon.sql.builder.enums.Operator;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dragons
 * @date 2021/11/10 10:45
 */
public class ConditionUtils {

    private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static String parseConditionColumn(String column1, Operator option, String column2) {
        return column1 + " " + option.getOptions().get(0) + " " + column2;
    }

    public static Tuple2<String, Object[]> parsePrecompileCondition(String column, Operator option, Object ...value) {
        if (option == Operator.IN || option == Operator.NOT_IN) {
            List<Object> vs = Arrays.stream(value).filter(Objects::nonNull)
                .flatMap(ConditionUtils::handleMoreFlatMap).collect(Collectors.toList());
            if (vs.isEmpty()) {
                return Tuple2.of("", Constants.EMPTY_OBJECT_ARRAY);
            }
            StringBuilder precompileTemplateBuilder = new StringBuilder(column + " " + option.getOptions().get(0));
            Object[] precompileArgs = null;
            for (int i = 0; i < vs.size(); i++) {
                if (i > 0) {
                    precompileTemplateBuilder.append(", ");
                }
                if (vs.get(i) instanceof SqlBuilder) {
                    precompileTemplateBuilder.append(((SqlBuilder) vs.get(i)).precompileSql());
                    precompileArgs = ((SqlBuilder) vs.get(i)).precompileArgs();
                    // nested queries should exit directly.
                    break;
                } else if (vs.get(i) instanceof Column) {
                    precompileTemplateBuilder.append(((Column)vs.get(i)).getName());
                }
                else {
                    precompileTemplateBuilder.append("?");
                }
            }
            precompileTemplateBuilder.append(option.getOptions().get(1));
            if (precompileArgs == null) {
                precompileArgs = vs.toArray();
            }
            return Tuple2.of(precompileTemplateBuilder.toString(), precompileArgs);
        } else if (option == Operator.BETWEEN_AND || option == Operator.NOT_BETWEEN_AND) {
            String precompileTemplate = column + " " + option.getOptions().get(0) + " ? " + option.getOptions().get(1) + " ?";
            List<Object> vs = Arrays.stream(value).filter(Objects::nonNull).flatMap(ConditionUtils::handleMoreFlatMap).collect(Collectors.toList());
            return Tuple2.of(precompileTemplate, new Object[]{vs.get(0), vs.get(1)});
        } else if (option == Operator.LLIKE || option == Operator.NOT_LLIKE) {
            return Tuple2.of(column + " " + option.getOptions().get(0) + " ?", new Object[]{"%" + value[0]});
        } else if (option == Operator.RLIKE || option == Operator.NOT_RLIKE) {
            return Tuple2.of(column + " " + option.getOptions().get(0) + " ?", new Object[]{value[0] + "%"});
        } else if (option == Operator.LRLIKE || option == Operator.NOT_LRLIKE || option == Operator.LIKE || option == Operator.NOT_LIKE) {
            return Tuple2.of(column + " " + option.getOptions().get(0) + " ?", new Object[]{"%" + value[0] + "%"});
        } else if (option == Operator.IS_NULL || option == Operator.NOT_NULL) {
            return Tuple2.of(column + " " + option.getOptions().get(0), Constants.EMPTY_OBJECT_ARRAY);
        } else if (option == Operator.EXISTS || option == Operator.NOT_EXISTS) {
            if (value[0] instanceof SqlBuilder) {
                return Tuple2.of(option.getOptions().get(0) + ((SqlBuilder) value[0]).precompileSql() + option.getOptions().get(1), ((SqlBuilder) value[0]).precompileArgs());
            }
            // original sql.
            return Tuple2.of(option.getOptions().get(0) + value[0] + option.getOptions().get(1), Constants.EMPTY_OBJECT_ARRAY);
        }
        if (value[0] instanceof SqlBuilder) {
            return Tuple2.of(column + " " + option.getOptions().get(0) + " (" + ((SqlBuilder) value[0]).precompileSql() + ")", ((SqlBuilder) value[0]).precompileArgs());
        }
        if (value[0] instanceof Column) {
            return Tuple2.of(column + " " + option.getOptions().get(0) + " " + ((Column)value[0]).getName(), Constants.EMPTY_OBJECT_ARRAY);
        }
        return Tuple2.of(column + " " + option.getOptions().get(0) + " ?", new Object[]{value[0]});
    }

    public static Stream<?> handleMoreFlatMap(Object e) {
        if (e instanceof Collection) return ((Collection) e).stream();
        else if (e.getClass().isArray()) return Stream.of((Object[])e);
        return Stream.of(e);
    }

    public static String parseValue(Object value) {
        if (value instanceof CharSequence) return "'" + value.toString().replace("'", "\\'") + "'";
        else if (value instanceof Column) return ((Column) value).getName();
        else if (value instanceof SqlBuilder) return "(" + ((SqlBuilder) value).build() + ")";
        else if (value instanceof Date) {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_PATTERN);
            return parseValue(formatter.format(value));
        } else if (value instanceof TemporalAccessor) {
            return parseValue(DATE_TIME_FORMATTER.format((TemporalAccessor) value));
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        }
        else if (value instanceof Collection) {
            // multi support
            return((Collection<?>) value).stream().map(ConditionUtils::parseValue).collect(Collectors.joining(", "));
        }
        return String.valueOf(value);
    }

    public static String parseTemplate(String template, Object ...values) {
        if (values.length == 0) return template;
        // support Collection
        values = Arrays.stream(values).flatMap(e -> {
            if (e instanceof Collection) return ((Collection) e).stream();
            return Stream.of(e);
        }).toArray();
        int index, offset = 0;
        List<Tuple2<Integer, Object>> valueCacheList = new ArrayList<>();
        for (Object value : values) {
            if (offset >= template.length()) break;
            if ((index = template.substring(offset).indexOf("?")) >= 0)  {
                valueCacheList.add(Tuple2.of(index + offset, value));
                offset += index + 1;
            }
            else if (valueCacheList.isEmpty()) return template;
        }
        int peek = 0;
        for (Tuple2<Integer, Object> indexAndValue : valueCacheList) {
            int length = template.length();
            template = parseTemplateValue(template, indexAndValue._1 + peek, indexAndValue._2);
            peek += template.length() - length;
        }
        return template;
    }

    private static String parseTemplateValue(String template, int index, Object value) {
        if (index == 0) template = parseValue(value) + template.substring(1);
        else if (index == template.length() - 1) template = template.substring(0, template.length() - 1) + parseValue(value);
        else template = template.substring(0, index) + parseValue(value) + template.substring(index + 1);
        return template;
    }

    private static String parseTemplateColumn(String template, int index, Object value) {
        if (index == 0) template = value + template.substring(2);
        else if (index == template.length() - 2) template = template.substring(0, template.length() - 2) + value;
        else template = template.substring(0, index) + value + template.substring(index + 2);
        return template;
    }
}
