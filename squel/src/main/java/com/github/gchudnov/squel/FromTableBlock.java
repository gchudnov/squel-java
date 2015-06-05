package com.github.gchudnov.squel;

/**
 * FROM table
 */
class FromTableBlock extends TableBlockBase {

    FromTableBlock(QueryBuilderOptions options) {
        super(options);
    }

    void setFrom(String table, String alias) {
        super.setTable(table, alias);
    }

    void setFrom(QueryBuilder table, String alias) {
        super.setTable(table, alias);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        assert !mTables.isEmpty();
        String tables = super.buildStr(queryBuilder);
        return "FROM " + tables;
    }
}
