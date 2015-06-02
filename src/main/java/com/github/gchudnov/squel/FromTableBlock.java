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

    public void from(String table) {
        super._table(table, null);
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        // if 0 >= @tables.length then throw new Error "from() needs to be called"
        String tables = super.buildStr(queryBuilder);
        return "FROM " + tables;
    }
}
