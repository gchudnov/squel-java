package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Validator & Sanitizer
 */
class Validator {

    static String sanitizeFieldAlias(String value, QueryBuilderOptions options) {
        return (options.autoQuoteAliasNames ? options.fieldAliasQuoteCharacter + value + options.fieldAliasQuoteCharacter : value);
    }

    static String sanitizeField(QueryBuilder value) {
        return "(" + value.toString() + ")";
    }

    static String sanitizeField(String value, QueryBuilderOptions options) {
        if(options.autoQuoteFieldNames) {
            String quoteChar = options.nameQuoteCharacter;
            if(options.ignorePeriodsForFieldNameQuotes) {
                // a.b.c -> `a.b.c`
                value = quoteChar + value + quoteChar;
            } else {
                // a.b.c -> `a`.`b`.`c`
                String[] parts = value.split(".");
                List<String> newParts = new ArrayList<>();
                for(String part : parts) {
                    // treat '*' as special case
                    if(part.equals("*")) {
                        newParts.add(part);
                    } else {
                        newParts.add(quoteChar + part + quoteChar);
                    }
                }
                value = Util.join(".", newParts);
            }
        }

        return value;
    }

    static String sanitizeTable(QueryBuilder name) {
        return ("(" + name.toString() + ")");
    }

    static String sanitizeTable(String name, QueryBuilderOptions options) {
        return (options.autoQuoteTableNames ? options.nameQuoteCharacter + name + options.nameQuoteCharacter : name);
    }

    static String sanitizeTableAlias(String value, QueryBuilderOptions options) {
        return (value != null ? (options.autoQuoteAliasNames ? options.tableAliasQuoteCharacter + value + options.tableAliasQuoteCharacter : value) : null);
    };

    static String escapeValue(String value, QueryBuilderOptions options) {
        return (options.replaceSingleQuotes ? value.replaceAll("'", options.singleQuoteReplacement) : value);
    }

    static String formatIterable(Iterable<?> values, QueryBuilderOptions options) {
        List<String> results = new ArrayList<>();
        for(Object value: values) {
            results.add(formatValue(value, options));
        }
        return "(" + Util.join(", ", results) + ")";
    }

    static String formatArray(Object[] values, QueryBuilderOptions options) {
        return formatIterable(Arrays.asList(values), options);
    }

    static String formatValue(Object value, QueryBuilderOptions options) {
        if(value == null) {
            return formatNull();
        } else {
            if(value instanceof Number) {
                return formatNumber((Number) value);
            } else if(value instanceof String) {
                return formatString((String) value, options);
            } else if(value instanceof Boolean) {
                return formatBoolean((boolean) value);
            } else if(value instanceof QueryBuilder) {
                return formatQueryBuilder((QueryBuilder) value);
            } else if(value instanceof Expression) {
                return formatExpression((Expression) value);
            } else if(value instanceof Object[]) {
                return formatArray((Object[]) value, options);
            } else if(value instanceof Iterable<?>) {
                return formatIterable((Iterable<?>)value, options);
            }
        }

        return value.toString();
    }

    private static String formatNull() {
        return "NULL";
    }

    private static String formatBoolean(boolean value) {
        return (value ? "TRUE" : "FALSE");
    }

    private static String formatNumber(Number value) {
        return String.valueOf(value);
    }

    private static String formatString(String value, QueryBuilderOptions options) {
        if(!options.dontQuote) {
            return "'" + escapeValue(value, options) + "'";
        }

        return value;
    }

    private static String formatQueryBuilder(QueryBuilder value) {
        return "(" + value.toString() + ")";
    }

    private static String formatExpression(Expression value) {
        return "(" + value.toString() + ")";
    }
}
