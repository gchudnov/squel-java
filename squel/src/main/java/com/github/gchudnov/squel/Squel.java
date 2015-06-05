package com.github.gchudnov.squel;

/**
 * API-functions exposed.
 */
public final class Squel {

    private Squel() {
    }

    /**
     * Starts a new expression.
     * @return Expression
     */
    public static Expression expr() {
        return expr(null);
    }

    /**
     * Starts a new expression with the provided options.
     * @param options Options to use for expression generation.
     * @return Expression
     */
    public static Expression expr(QueryBuilderOptions options) {
        return new Expression(options);
    }

    /**
     * Starts the SELECT-query chain.
     * @return QueryBuilder
     */
    public static QueryBuilder select() {
        return select(null);
    }

    /**
     * Starts the SELECT-query chain with the provided options
     * @param options Options to use for query generation.
     * @return QueryBuilder
     */
    public static QueryBuilder select(QueryBuilderOptions options) {
        return new Select(options);
    }

    /**
     * Starts the UPDATE-query.
     * @return QueryBuilder
     */
    public static QueryBuilder update() {
        return update(null);
    }

    /**
     * Starts the UPDATE-query.
     * @param options Options to use for query generation.
     * @return QueryBuilder
     */
    public static QueryBuilder update(QueryBuilderOptions options) {
        return new Update(options);
    }

    /**
     * Starts the INSERT-query.
     * @return QueryBuilder
     */
    public static QueryBuilder insert() {
        return  insert(null);
    }

    /**
     * Starts the INSERT-query with the provided options.
     * @param options Options to use for query generation.
     * @return QueryBuilder
     */
    public static QueryBuilder insert(QueryBuilderOptions options) {
        return new Insert(options);
    }

    /**
     * Starts the DELETE-query.
     * @return QueryBuilder
     */
    public static QueryBuilder delete() {
        return delete(null);
    }

    /**
     * Starts the DELETE-query with the provided options.
     * @param options Options to use for query generation.
     * @return QueryBuilder
     */
    public static QueryBuilder delete(QueryBuilderOptions options) {
        return new Delete(options);
    }
}
