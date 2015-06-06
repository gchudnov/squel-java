package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * Query builder base class.
 * NOTE: the query builder does not check the final query string for correctness.
 */
public abstract class QueryBuilder {

    private final QueryBuilderOptions mOptions;
    final List<Block> mBlocks;

    QueryBuilder(QueryBuilderOptions options, List<Block> blocks) {
        mOptions = (options != null ? options : new QueryBuilderOptions());
        mBlocks = (blocks != null ? blocks : new ArrayList<Block>());
    }

    @Override
    public String toString() {
        List<String> results = new ArrayList<>();
        for (Block block : mBlocks) {
            results.add(block.buildStr(this));
        }

        return Util.joinNonEmpty(mOptions.separator, results);
    }

    //
    // DISTINCT
    //
    public QueryBuilder distinct() {
        throw new UnsupportedOperationException("`distinct` not implemented");
    }

    //
    // FROM
    //

    public QueryBuilder from(String table) {
        return from(table, null);
    }

    public QueryBuilder from(QueryBuilder table) {
        return from(table, null);
    }

    public QueryBuilder from(String table, String alias) {
        throw new UnsupportedOperationException("`from` not implemented");
    }

    public QueryBuilder from(QueryBuilder table, String alias) {
        throw new UnsupportedOperationException("`from` not implemented");
    }

    //
    // GET
    //

    public QueryBuilder field(String field) {
        return field(field, null);
    }

    public QueryBuilder field(String field, String alias) {
        throw new UnsupportedOperationException("`field` not implemented");
    }

    public QueryBuilder field(QueryBuilder field) {
        return field(field, null);
    }

    public QueryBuilder field(QueryBuilder field, String alias) {
        throw new UnsupportedOperationException("`field` not implemented");
    }

    public QueryBuilder fields(Iterable<String> fields) {
        throw new UnsupportedOperationException("`fields` not implemented");
    }

    //
    // GROUP
    //

    public QueryBuilder group(String field) {
        throw new UnsupportedOperationException("`group` not implemented");
    }

    //
    // JOIN
    //

    public QueryBuilder join(String table) {
        return join(table, null, (String)null, null);
    }

    public QueryBuilder join(String table, String alias) {
        return join(table, alias, (String)null, null);
    }

    public QueryBuilder join(String table, String alias, String condition) {
        return join(table, alias, condition, null);
    }

    public QueryBuilder join(String table, Expression condition) {
        return join(table, null, condition, null);
    }

    public QueryBuilder join(String table, String alias, Expression condition) {
        return join(table, alias, condition, null);
    }

    public QueryBuilder join(QueryBuilder table, String alias, String condition) {
        return join(table, alias, condition, null);
    }

    public QueryBuilder join(QueryBuilder table, String alias, Expression condition) {
        return join(table, alias, condition, null);
    }

    //

    public QueryBuilder join(String table, String alias, String condition, JoinType type) {
        throw new UnsupportedOperationException("`join` not implemented");
    }

    public QueryBuilder join(QueryBuilder table, String alias, String condition, JoinType type) {
        throw new UnsupportedOperationException("`join` not implemented");
    }

    public QueryBuilder join(String table, String alias, Expression condition, JoinType type) {
        throw new UnsupportedOperationException("`join` not implemented");
    }

    public QueryBuilder join(QueryBuilder table, String alias, Expression condition, JoinType type) {
        throw new UnsupportedOperationException("`join` not implemented");
    }

    // INNER

    public QueryBuilder innerJoin(String table) {
        return innerJoin(table, null, (String) null);
    }

    public QueryBuilder innerJoin(String table, String alias) {
        return innerJoin(table, alias, (String) null);
    }

    public QueryBuilder innerJoin(String table, String alias, String condition) {
        return join(table, alias, condition, JoinType.INNER);
    }

    public QueryBuilder innerJoin(String table, Expression condition) {
        return join(table, null, condition, JoinType.INNER);
    }

    public QueryBuilder innerJoin(String table, String alias, Expression condition) {
        return join(table, alias, condition, JoinType.INNER);
    }

    public QueryBuilder innerJoin(QueryBuilder table, String alias, String condition) {
        return join(table, alias, condition, JoinType.INNER);
    }

    public QueryBuilder innerJoin(QueryBuilder table, String alias, Expression condition) {
        return join(table, alias, condition, JoinType.INNER);
    }

    // LEFT

    public QueryBuilder leftJoin(String table) {
        return leftJoin(table, null, (String) null);
    }

    public QueryBuilder leftJoin(String table, String alias) {
        return leftJoin(table, alias, (String) null);
    }

    public QueryBuilder leftJoin(String table, String alias, String condition) {
        return join(table, alias, condition, JoinType.LEFT);
    }

    public QueryBuilder leftJoin(String table, Expression condition) {
        return join(table, null, condition, JoinType.LEFT);
    }

    public QueryBuilder leftJoin(String table, String alias, Expression condition) {
        return join(table, alias, condition, JoinType.LEFT);
    }

    public QueryBuilder leftJoin(QueryBuilder table, String alias, String condition) {
        return join(table, alias, condition, JoinType.LEFT);
    }

    public QueryBuilder leftJoin(QueryBuilder table, String alias, Expression condition) {
        return join(table, alias, condition, JoinType.LEFT);
    }

    // RIGHT

    public QueryBuilder rightJoin(String table) {
        return rightJoin(table, null, (String) null);
    }

    public QueryBuilder rightJoin(String table, String alias) {
        return rightJoin(table, alias, (String) null);
    }

    public QueryBuilder rightJoin(String table, String alias, String condition) {
        return join(table, alias, condition, JoinType.RIGHT);
    }

    public QueryBuilder rightJoin(String table, Expression condition) {
        return join(table, null, condition, JoinType.RIGHT);
    }

    public QueryBuilder rightJoin(String table, String alias, Expression condition) {
        return join(table, alias, condition, JoinType.RIGHT);
    }

    public QueryBuilder rightJoin(QueryBuilder table, String alias, String condition) {
        return join(table, alias, condition, JoinType.RIGHT);
    }

    public QueryBuilder rightJoin(QueryBuilder table, String alias, Expression condition) {
        return join(table, alias, condition, JoinType.RIGHT);
    }

    // FULL

    public QueryBuilder fullJoin(String table) {
        return fullJoin(table, null, (String) null);
    }

    public QueryBuilder fullJoin(String table, String alias) {
        return fullJoin(table, alias, (String) null);
    }

    public QueryBuilder fullJoin(String table, String alias, String condition) {
        return join(table, alias, condition, JoinType.FULL);
    }

    public QueryBuilder fullJoin(String table, Expression condition) {
        return join(table, null, condition, JoinType.FULL);
    }

    public QueryBuilder fullJoin(String table, String alias, Expression condition) {
        return join(table, alias, condition, JoinType.FULL);
    }

    public QueryBuilder fullJoin(QueryBuilder table, String alias, String condition) {
        return join(table, alias, condition, JoinType.FULL);
    }

    public QueryBuilder fullJoin(QueryBuilder table, String alias, Expression condition) {
        return join(table, alias, condition, JoinType.FULL);
    }

    // CROSS

    public QueryBuilder crossJoin(String table) {
        return crossJoin(table, null, (String) null);
    }

    public QueryBuilder crossJoin(String table, String alias) {
        return crossJoin(table, alias, (String) null);
    }

    public QueryBuilder crossJoin(String table, String alias, String condition) {
        return join(table, alias, condition, JoinType.CROSS);
    }

    public QueryBuilder crossJoin(String table, Expression condition) {
        return join(table, null, condition, JoinType.CROSS);
    }

    public QueryBuilder crossJoin(String table, String alias, Expression condition) {
        return join(table, alias, condition, JoinType.CROSS);
    }

    public QueryBuilder crossJoin(QueryBuilder table, String alias, String condition) {
        return join(table, alias, condition, JoinType.CROSS);
    }

    public QueryBuilder crossJoin(QueryBuilder table, String alias, Expression condition) {
        return join(table, alias, condition, JoinType.CROSS);
    }

    //
    // WHERE
    //

    public <P> QueryBuilder where(String condition, P param) {
        throw new UnsupportedOperationException("`where` not implemented");
    }

    public QueryBuilder where(String condition) {
        return where(condition, null);
    }

    public <P> QueryBuilder where (Expression condition, P param) {
        throw new UnsupportedOperationException("`where` not implemented");
    }

    public QueryBuilder where(Expression condition) {
        return where(condition, null);
    }

    //
    // LIMIT
    //

    public QueryBuilder limit(int value) {
        throw new UnsupportedOperationException("`limit` not implemented");
    }

    //
    // ORDER BY
    //

    public QueryBuilder order(String field, SortOrder dir) {
        throw new UnsupportedOperationException("`order` not implemented");
    }

    public QueryBuilder order(String field) {
        return order(field, SortOrder.ASC);
    }

    //
    // OFFSET
    //

    public QueryBuilder offset(int value) {
        throw new UnsupportedOperationException("`offset` not implemented");
    }

    //
    // UNION
    //

    public QueryBuilder union(String table, UnionType unionType) {
        throw new UnsupportedOperationException("`union` not implemented");
    }

    public QueryBuilder union(QueryBuilder table, UnionType unionType) {
        throw new UnsupportedOperationException("`union` not implemented");
    }

    public QueryBuilder union(String table) {
        return union(table, UnionType.UNION);
    }

    public QueryBuilder union(QueryBuilder table) {
        return union(table, UnionType.UNION);
    }

    public QueryBuilder unionAll(String table) {
        return union(table, UnionType.UNION_ALL);
    }

    //
    // TABLE
    //

    public QueryBuilder table(String table) {
        return table(table, null);
    }

    public QueryBuilder table(String table, String alias) {
        throw new UnsupportedOperationException("`table` not implemented");
    }

    //
    // SET
    //

    public <T> QueryBuilder set(String field, T value) {
        throw new UnsupportedOperationException("`set` not implemented");
    }

    //
    // INTO
    //

    public QueryBuilder into(String table) {
        throw new UnsupportedOperationException("`into` not implemented");
    }

    //
    // `FROM QUERY`
    //

    public QueryBuilder fromQuery(Iterable<String> fields, QueryBuilder query) {
        throw new UnsupportedOperationException("`fromQuery` not implemented");
    }
}
