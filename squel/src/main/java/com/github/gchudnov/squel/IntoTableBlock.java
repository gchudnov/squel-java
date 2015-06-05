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
        table = Validator.sanitizeTable(table, mOptions);
        mTable = table;
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        assert mTable != null;
        return "INTO " + mTable;
    }
}
