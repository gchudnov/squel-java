package com.github.gchudnov.squel;

/**
 * Functions Exposed.
 */
public class Squel {

    public static Expression expr() {
        return new Expression();
    }

    public static QueryBuilder select() {
        return select(null);
    }

    public static QueryBuilder select(QueryBuilderOptions options) {
        return new Select(options);
    }
}
