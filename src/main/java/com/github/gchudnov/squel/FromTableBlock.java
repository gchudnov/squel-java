package com.github.gchudnov.squel;

/**
 * FROM table
 */
public class FromTableBlock extends AbstractTableBlock {

    public FromTableBlock(QueryBuilderOptions options) {
        super(options);
    }

    public void from(String table, String alias) {
        super._table(table, alias);
    }

    public void from(QueryBuilder table, String alias) {
        super._table(table, alias);
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        assert !mTables.isEmpty();
        String tables = super.buildStr(queryBuilder);
        return "FROM " + tables;
    }
}
