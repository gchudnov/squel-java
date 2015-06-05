package com.github.gchudnov.squel;

/**
 * INTO table
 */
class IntoTableBlock extends Block {

    private String mTable = null;

    IntoTableBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Into given table.
    void into(String table) {
        table = _sanitizeTable(table);
        mTable = table;
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        return "INTO " + mTable;
    }
}
