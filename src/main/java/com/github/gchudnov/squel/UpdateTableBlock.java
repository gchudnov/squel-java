package com.github.gchudnov.squel;

/**
 * Update table.
 */
class UpdateTableBlock extends AbstractTableBlock {

    UpdateTableBlock(QueryBuilderOptions options) {
        super(options);
    }

    void table(String table, String alias) {
        super._table(table, alias);
    }

    void table(String table) {
        super._table(table, null);
    }
}
