package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all builders
 */
class BaseBlock {

    protected QueryBuilderOptions mOptions;

    BaseBlock(QueryBuilderOptions options) {
        this.mOptions = (options != null ? options : new QueryBuilderOptions());
    }

    protected String _sanitizeFieldAlias(String value) {
        return (mOptions.autoQuoteAliasNames ? mOptions.fieldAliasQuoteCharacter + value + mOptions.fieldAliasQuoteCharacter : value);
    }

    protected String _sanitizeField(QueryBuilder value) {
        return "(" + value.toString() + ")";
    }

    protected String _sanitizeField(String value) {
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
                value = Util.join(".", newParts.toArray(new String[newParts.size()]));
            }
        }

        return value;
    }

    protected String _sanitizeTable(QueryBuilder name) {
        return ("(" + name.toString() + ")");
    }

    protected String _sanitizeTable(String name) {
        return (mOptions.autoQuoteTableNames ? mOptions.nameQuoteCharacter + name + mOptions.nameQuoteCharacter : name);
    }

    protected String _sanitizeTableAlias(String value) {
        return (value != null ? (mOptions.autoQuoteAliasNames ? mOptions.tableAliasQuoteCharacter + value + mOptions.tableAliasQuoteCharacter : value) : null);
    };

    // Escape a string value, e.g. escape quotes and other characters within it.
    private String _escapeValue(String value) {
        return (mOptions.replaceSingleQuotes ? value.replaceAll("'", mOptions.singleQuoteReplacement) : value);
    }

    private String _formatNull() {
        return "NULL";
    }

    private String _formatBoolean(boolean value) {
        return (value ? "TRUE" : "FALSE");
    }

    private String _formatNumber(Number value) {
        return String.valueOf(value);
    }

    private String _formatString(String value) {
        if(!mOptions.dontQuote) {
            return "'" + this._escapeValue(value) + "'";
        }

        return value;
    }

    private String _formatQueryBuilder(QueryBuilder value) {
        return "(" + value.toString() + ")";
    }

    private String _formatExpression(Expression value) {
        return "(" + value.toString() + ")";
    }

    protected String _formatArray(Object[] values) {
        List<String> results = new ArrayList<>();
        for(Object value: values) {
            results.add(_formatValue(value));
        }
        return "(" + Util.join(", ", results.toArray(new String[results.size()])) + ")";
    }

    protected String _formatValue(Object value) {
        if(value == null) {
            return _formatNull();
        } else {
            if(value instanceof Number) {
                return _formatNumber((Number)value);
            } else if(value instanceof String) {
                return _formatString((String)value);
            } else if(value instanceof Boolean) {
                return _formatBoolean((boolean)value);
            } else if(value instanceof QueryBuilder) {
                return _formatQueryBuilder((QueryBuilder)value);
            } else if(value instanceof Expression) {
                return _formatExpression((Expression)value);
            } else if(value instanceof Object[]) {
                return _formatArray((Object[])value);
            }
        }

        return value.toString();
    }
}
