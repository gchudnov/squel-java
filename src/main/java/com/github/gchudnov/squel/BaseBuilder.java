package com.github.gchudnov.squel;

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

    protected String _sanitizeTableAlias(String value) {
        return (mOptions.autoQuoteAliasNames ? mOptions.tableAliasQuoteCharacter + value + mOptions.tableAliasQuoteCharacter : value);
    };
}
