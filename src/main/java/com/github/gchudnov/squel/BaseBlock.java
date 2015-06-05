package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all builders
 */
abstract class BaseBlock {

    protected QueryBuilderOptions mOptions;

    protected BaseBlock(QueryBuilderOptions options) {
        this.mOptions = (options != null ? options : new QueryBuilderOptions());
    }

    protected String sanitizeFieldAlias(String value) {
        return (mOptions.autoQuoteAliasNames ? mOptions.fieldAliasQuoteCharacter + value + mOptions.fieldAliasQuoteCharacter : value);
    }

    protected String sanitizeField(QueryBuilder value) {
        return "(" + value.toString() + ")";
    }

    protected String sanitizeField(String value) {
        if(mOptions.autoQuoteFieldNames) {
            String quoteChar = mOptions.nameQuoteCharacter;
            if(mOptions.ignorePeriodsForFieldNameQuotes) {
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

    protected String sanitizeTable(QueryBuilder name) {
        return ("(" + name.toString() + ")");
    }

    protected String sanitizeTable(String name) {
        return (mOptions.autoQuoteTableNames ? mOptions.nameQuoteCharacter + name + mOptions.nameQuoteCharacter : name);
    }

    protected String sanitizeTableAlias(String value) {
        return (value != null ? (mOptions.autoQuoteAliasNames ? mOptions.tableAliasQuoteCharacter + value + mOptions.tableAliasQuoteCharacter : value) : null);
    };

    private String escapeValue(String value) {
        return (mOptions.replaceSingleQuotes ? value.replaceAll("'", mOptions.singleQuoteReplacement) : value);
    }

    private String formatNull() {
        return "NULL";
    }

    private String formatBoolean(boolean value) {
        return (value ? "TRUE" : "FALSE");
    }

    private String formatNumber(Number value) {
        return String.valueOf(value);
    }

    private String formatString(String value) {
        if(!mOptions.dontQuote) {
            return "'" + this.escapeValue(value) + "'";
        }

        return value;
    }

    private String formatQueryBuilder(QueryBuilder value) {
        return "(" + value.toString() + ")";
    }

    private String formatExpression(Expression value) {
        return "(" + value.toString() + ")";
    }

    protected String formatArray(Object[] values) {
        List<String> results = new ArrayList<>();
        for(Object value: values) {
            results.add(_formatValue(value));
        }
        return "(" + Util.join(", ", results) + ")";
    }

    protected String _formatValue(Object value) {
        if(value == null) {
            return formatNull();
        } else {
            if(value instanceof Number) {
                return formatNumber((Number) value);
            } else if(value instanceof String) {
                return formatString((String) value);
            } else if(value instanceof Boolean) {
                return formatBoolean((boolean) value);
            } else if(value instanceof QueryBuilder) {
                return formatQueryBuilder((QueryBuilder) value);
            } else if(value instanceof Expression) {
                return formatExpression((Expression) value);
            } else if(value instanceof Object[]) {
                return formatArray((Object[]) value);
            }
        }

        return value.toString();
    }
}
