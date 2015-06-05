package com.github.gchudnov.squel;

/**
 * API-functions exposed.
 */
public final class Squel {

    private Squel() {
    }

    /**
     * Start a new expression.
     * @return Expression
     */
    public static Expression expr() {
        return expr(null);
    }

    /**
     * Start a new expression with the provided options.
     * @param options Options to use for expression generation.
     * @return Expression
     */
    public static Expression expr(QueryBuilderOptions options) {
        return new Expression(options);
    }

    /**
     * Start the SELECT-query chain.
     * @return QueryBuilder
     */
    public static QueryBuilder select() {
        return select(null);
    }

    /**
     * Start the SELECT-query chain with the provided options
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
     * Start the UPDATE-query.
     * @param options Options to use for query generation.
     * @return QueryBuilder
     */
    public static QueryBuilder update(QueryBuilderOptions options) {
        return new Update(options);
    }

    /**
     * Start the INSERT-query.
     * @return QueryBuilder
     */
    public static QueryBuilder insert() {
        return  insert(null);
    }

    /**
     * Start the INSERT-query with the provided options.
     * @param options Options to use for query generation.
     * @return QueryBuilder
     */
    public static QueryBuilder insert(QueryBuilderOptions options) {
        return new Insert(options);
    }

    /**
     * Start the DELETE-query.
     * @return QueryBuilder
     */
    public static QueryBuilder delete() {
        return delete(null);
    }

    /**
     * Start the DELETE-query with the provided options.
     * @param options Options to use for query generation.
     * @return QueryBuilder
     */
    public static QueryBuilder delete(QueryBuilderOptions options) {
        return new Delete(options);
    }
}
