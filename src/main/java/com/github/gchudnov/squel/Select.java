package com.github.gchudnov.squel;

import java.util.Arrays;

/**
 * SELECT query builder.
 */
public class Select extends QueryBuilder {

    public Select(QueryBuilderOptions options) {
        super(options, Arrays.asList(
                new StringBlock(options, "SELECT"),
                new DistinctBlock(options),
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
    public boolean isNestable() {
        return true;
    }

    public QueryBuilder from(String table) {
        return from(table, null);
    }

    public QueryBuilder from(String table, String alias) {
        FromTableBlock block = (FromTableBlock) mBlocks.get(3);
        block.from(table, alias);
        return this;
    }

    public QueryBuilder field(String field) {
        return field(field, null);
    }

    public QueryBuilder field(String field, String alias) {
        GetFieldBlock block = (GetFieldBlock) mBlocks.get(2);
        block.field(field, alias);
        return this;
    }

    public QueryBuilder group(String field) {
        GroupByBlock block = (GroupByBlock) mBlocks.get(6);
        block.group(field);
        return this;
    }

    public QueryBuilder join(String table, String alias, String condition, String type) {
        JoinBlock block = (JoinBlock) mBlocks.get(4);
        block.join(table, alias, condition, type);
        return this;
    }

    public QueryBuilder where(String condition) {
        WhereBlock block = (WhereBlock) mBlocks.get(5);
        block.where(condition);
        return this;
    }

    public QueryBuilder where(Expression condition) {
        WhereBlock block = (WhereBlock) mBlocks.get(5);
        block.where(condition);
        return this;
    }
}
