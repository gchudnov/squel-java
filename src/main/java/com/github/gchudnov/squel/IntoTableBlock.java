package com.github.gchudnov.squel;

/**
 * INTO table
 */
class IntoTableBlock extends Block {

    private String mTable;

    IntoTableBlock(QueryBuilderOptions options) {
        super(options);
    }

    void setInto(String table) {
        table = sanitizeTable(table);
        mTable = table;
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        assert mTable != null;
        return "INTO " + mTable;
    }
}
