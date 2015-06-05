package com.github.gchudnov.squel;

import java.util.Arrays;

/**
 * DELETE query builder.
 */
public final class Delete extends QueryBuilder {

    Delete(QueryBuilderOptions options) {
        super(options, Arrays.asList(
                new StringBlock(options, "DELETE"),
                new FromTableBlock(options),  // 1
                new JoinBlock(options),       // 2
                new WhereBlock(options),      // 3
                new OrderByBlock(options),    // 4
                new LimitBlock(options)       // 5
        ));
    }

    @Override
    public QueryBuilder from(String table, String alias) {
        FromTableBlock block = (FromTableBlock) mBlocks.get(1);
        block.from(table, alias);
        return this;
    }

    @Override
    public QueryBuilder from(QueryBuilder table, String alias) {
        FromTableBlock block = (FromTableBlock) mBlocks.get(1);
        block.from(table, alias);
        return this;
    }

    @Override
    public <P> QueryBuilder where(String condition, P param) {
        WhereBlock block = (WhereBlock) mBlocks.get(3);
        block.where(condition, param);
        return this;
    }

    @Override
    public <P> QueryBuilder where(Expression condition, P param) {
        WhereBlock block = (WhereBlock) mBlocks.get(3);
        block.where(condition, param);
        return this;
    }

    @Override
    public QueryBuilder join(String table, String alias, String condition, JoinType type) {
        JoinBlock block = (JoinBlock) mBlocks.get(2);
        block.join(table, alias, condition, type);
        return this;
    }

    public QueryBuilder join(QueryBuilder table, String alias, String condition, JoinType type) {
        JoinBlock block = (JoinBlock) mBlocks.get(2);
        block.join(table, alias, condition, type);
        return this;
    }

    public QueryBuilder join(String table, String alias, Expression condition, JoinType type) {
        JoinBlock block = (JoinBlock) mBlocks.get(2);
        block.join(table, alias, condition, type);
        return this;
    }

    public QueryBuilder join(QueryBuilder table, String alias, Expression condition, JoinType type) {
        JoinBlock block = (JoinBlock) mBlocks.get(2);
        block.join(table, alias, condition, type);
        return this;
    }

    @Override
    public QueryBuilder order(String field, OrderDirection dir) {
        OrderByBlock block = (OrderByBlock)mBlocks.get(4);
        block.setOrder(field, dir);
        return this;
    }

    @Override
    public QueryBuilder limit(int value) {
        LimitBlock block = (LimitBlock)mBlocks.get(5);
        block.setLimit(value);
        return this;
    }
}
