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
                new OrderByBlock(options),
                new LimitBlock(options),
                new OffsetBlock(options),
                new UnionBlock(options)
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
}
