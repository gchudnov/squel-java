package com.github.gchudnov.squel;

import java.util.Arrays;

/**
 * An INSERT query builder.
 */
class Insert extends QueryBuilder {

    Insert(QueryBuilderOptions options) {
        super(options, Arrays.asList(
                new StringBlock(options, "INSERT"),
                new IntoTableBlock(options),            // 1
                new InsertFieldValueBlock(options),     // 2
                new InsertFieldsFromQueryBlock(options) // 3
        ));
    }

    @Override
    public QueryBuilder into(String table) {
        IntoTableBlock block = (IntoTableBlock) mBlocks.get(1);
        block.setInto(table);
        return this;
    }

    @Override
    public <T> QueryBuilder set(String field, T value) {
        InsertFieldValueBlock block = (InsertFieldValueBlock) mBlocks.get(2);
        block.setFieldValue(field, value);
        return this;
    }

    @Override
    public QueryBuilder fromQuery(Iterable<String> fields, QueryBuilder query) {
        InsertFieldsFromQueryBlock block = (InsertFieldsFromQueryBlock) mBlocks.get(3);
        block.setFromQuery(fields, query);
        return this;
    }
}
