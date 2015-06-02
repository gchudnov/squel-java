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

}
