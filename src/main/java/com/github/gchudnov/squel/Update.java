package com.github.gchudnov.squel;

import com.github.gchudnov.squel.impl.*;

import java.util.Arrays;

/**
 * UPDATE query builder.
 */
public class Update extends QueryBuilder {

    Update(QueryBuilderOptions options) {
        super(options, Arrays.asList(
                new StringBlock(options, "UPDATE"),
                new UpdateTableBlock(options),      // 1
                new SetFieldBlock(options),         // 2
                new WhereBlock(options),            // 3
                new OrderByBlock(options),          // 4
                new LimitBlock(options)             // 5
        ));
    }

    @Override
    public QueryBuilder table(String table, String alias) {
        UpdateTableBlock block = (UpdateTableBlock) mBlocks.get(1);
        block.table(table, alias);
        return this;
    }

    @Override
    public <T> QueryBuilder set(String field, T value) {
        SetFieldBlock block = (SetFieldBlock) mBlocks.get(2);
        block.set(field, value);
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
