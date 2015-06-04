package com.github.gchudnov.squel;

import java.util.Arrays;

/**
 * SELECT query builder.
 */
public class Select extends QueryBuilder {

    public Select(QueryBuilderOptions options) {
        super(options, Arrays.asList(
                new StringBlock(options, "SELECT"),
                new DistinctBlock(options),  // 1
                new GetFieldBlock(options),  // 2
                new FromTableBlock(options), // 3
                new JoinBlock(options),      // 4
                new WhereBlock(options),     // 5
                new GroupByBlock(options),   // 6
                new OrderByBlock(options),   // 7
                new LimitBlock(options),     // 8
                new OffsetBlock(options),    // 9
                new UnionBlock(options)      // 10
        ));
    }

    @Override
    public QueryBuilder distinct() {
        DistinctBlock block = (DistinctBlock) mBlocks.get(1);
        block.distinct();
        return this;
    }

    @Override
    public QueryBuilder from(String table) {
        return from(table, null);
    }

    @Override
    public QueryBuilder from(QueryBuilder table) {
        return from(table, null);
    }

    @Override
    public QueryBuilder from(String table, String alias) {
        FromTableBlock block = (FromTableBlock) mBlocks.get(3);
        block.from(table, alias);
        return this;
    }

    @Override
    public QueryBuilder from(QueryBuilder table, String alias) {
        FromTableBlock block = (FromTableBlock) mBlocks.get(3);
        block.from(table, alias);
        return this;
    }

    @Override
    public QueryBuilder field(String field) {
        return field(field, null);
    }

    @Override
    public QueryBuilder field(String field, String alias) {
        GetFieldBlock block = (GetFieldBlock) mBlocks.get(2);
        block.field(field, alias);
        return this;
    }

    @Override
    public QueryBuilder field(QueryBuilder field, String alias) {
        GetFieldBlock block = (GetFieldBlock) mBlocks.get(2);
        block.field(field, alias);
        return this;
    }

    @Override
    public QueryBuilder group(String field) {
        GroupByBlock block = (GroupByBlock) mBlocks.get(6);
        block.group(field);
        return this;
    }

    @Override
    public QueryBuilder join(String table, String alias, String condition, String type) {
        JoinBlock block = (JoinBlock) mBlocks.get(4);
        block.join(table, alias, condition, type);
        return this;
    }

    public QueryBuilder join(QueryBuilder table, String alias, String condition, String type) {
        JoinBlock block = (JoinBlock) mBlocks.get(4);
        block.join(table, alias, condition, type);
        return this;
    }

    @Override
    public QueryBuilder where(String condition) {
        WhereBlock block = (WhereBlock) mBlocks.get(5);
        block.where(condition);
        return this;
    }

    @Override
    public QueryBuilder where(Expression condition) {
        WhereBlock block = (WhereBlock) mBlocks.get(5);
        block.where(condition);
        return this;
    }

    @Override
    public QueryBuilder limit(int value) {
        LimitBlock block = (LimitBlock)mBlocks.get(8);
        block.setLimit(value);
        return this;
    }

    @Override
    public QueryBuilder order(String field, OrderDirection dir) {
        OrderByBlock block = (OrderByBlock)mBlocks.get(7);
        block.setOrder(field, dir);
        return this;
    }

    @Override
    public QueryBuilder offset(int value) {
        OffsetBlock block = (OffsetBlock)mBlocks.get(9);
        block.setOffset(value);
        return this;
    }

    @Override
    public QueryBuilder union(String table, UnionType unionType) {
        UnionBlock block = (UnionBlock)mBlocks.get(10);
        block.setUnion(table, unionType);
        return this;
    }

    public QueryBuilder union(QueryBuilder table, UnionType unionType) {
        UnionBlock block = (UnionBlock)mBlocks.get(10);
        block.setUnion(table, unionType);
        return this;
    }
}
