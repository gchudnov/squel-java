package com.github.gchudnov.squel;

/**
 * Functions Exposed.
 */
public final class Squel {

    public static Expression expr() {
        return new Expression();
    }

    public static QueryBuilder select() {
        return select(null);
    }

    public static QueryBuilder update() {
        return update(null);
    }

    public static QueryBuilder insert() {
        return  insert(null);
    }

    public static QueryBuilder delete() {
        return delete(null);
    }

    public static QueryBuilder select(QueryBuilderOptions options) {
        return new Select(options);
    }

    public static QueryBuilder update(QueryBuilderOptions options) {
        return new Update(options);
    }

    public static QueryBuilder insert(QueryBuilderOptions options) {
        return new Insert(options);
    }

    public static QueryBuilder delete(QueryBuilderOptions options) {
        return new Delete(options);
    }
}
