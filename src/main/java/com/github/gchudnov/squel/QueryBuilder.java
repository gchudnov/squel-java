package com.github.gchudnov.squel;

import com.github.gchudnov.squel.impl.Block;
import com.github.gchudnov.squel.impl.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Query builder base class
 * <p>
 * Note that the query builder does not check the final query string for correctness.
 * <p>
 * All the build methods in this object return the object instance for chained method calling purposes.
 */
public abstract class QueryBuilder extends BaseBuilder {

    protected List<Block> mBlocks;

    QueryBuilder(QueryBuilderOptions options, List<Block> blocks) {
        super(options);
        mBlocks = (blocks != null ? blocks : new ArrayList<Block>());
    }

    @Override
    public String toString() {
        List<String> results = new ArrayList<>();
        for (Block block : mBlocks) {
            results.add(block.buildStr(this));
        }

        return Util.joinNonEmpty(mOptions.separator, results.toArray(new String[results.size()]));
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
        throw new UnsupportedOperationException("`from` not implemented");
    }

    public QueryBuilder from(QueryBuilder table) {
        throw new UnsupportedOperationException("`from` not implemented");
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
        throw new UnsupportedOperationException("`field` not implemented");
    }

    public QueryBuilder field(String field, String alias) {
        throw new UnsupportedOperationException("`field` not implemented");
    }

    public QueryBuilder field(QueryBuilder field, String alias) {
        throw new UnsupportedOperationException("`field` not implemented");
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


    public QueryBuilder left_join(String table, String alias, String condition) {
        throw new UnsupportedOperationException("`left_join` not implemented");
    }

    public QueryBuilder right_join(String table, String alias, String condition) {
        throw new UnsupportedOperationException("`right_join` not implemented");
    }

    public QueryBuilder outer_join(String table, String alias, String condition) {
        throw new UnsupportedOperationException("`outer_join` not implemented");
    }

    public QueryBuilder left_outer_join(String table, String alias, String condition) {
        throw new UnsupportedOperationException("`left_outer_join` not implemented");
    }

    public QueryBuilder full_join(String table, String alias, String condition) {
        throw new UnsupportedOperationException("`full_join` not implemented");
    }

    public QueryBuilder cross_join(String table, String alias, String condition) {
        throw new UnsupportedOperationException("`cross_join` not implemented");
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

    public QueryBuilder order(String field, OrderDirection dir) {
        throw new UnsupportedOperationException("`order` not implemented");
    }

    public QueryBuilder order(String field) {
        return order(field, OrderDirection.ASC);
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

    public QueryBuilder union_all(String table) {
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
