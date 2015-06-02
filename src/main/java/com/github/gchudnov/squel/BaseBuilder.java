package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all builders
 */
public class BaseBuilder {

    protected QueryBuilderOptions mOptions;

    // Constructor
    //
    // options is an Object overriding one or more of cls.DefaultQueryBuilderOptions
    //
    public BaseBuilder(QueryBuilderOptions options) {
        if (options == null) {
            options = new QueryBuilderOptions();
        }

        this.mOptions = options;
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
}
