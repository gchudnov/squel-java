package com.github.gchudnov.squel;

/**
 * com.github.gchudnov.squel.Update Table
 */
public class UpdateTableBlock extends AbstractTableBlock {

    public UpdateTableBlock(QueryBuilderOptions options) {
        super(options);
    }

    public void table(String table, String alias) {
        super._table(table, alias);
    }

    public void table(String table) {
        super._table(table, null);
    }
}
