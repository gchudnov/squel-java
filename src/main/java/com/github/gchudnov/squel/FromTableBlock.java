package com.github.gchudnov.squel;

/**
 * FROM table
 */
class FromTableBlock extends AbstractTableBlock {

    FromTableBlock(QueryBuilderOptions options) {
        super(options);
    }

    void from(String table, String alias) {
        super._table(table, alias);
    }

    void from(QueryBuilder table, String alias) {
        super._table(table, alias);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        assert !mTables.isEmpty();
        String tables = super.buildStr(queryBuilder);
        return "FROM " + tables;
    }
}
