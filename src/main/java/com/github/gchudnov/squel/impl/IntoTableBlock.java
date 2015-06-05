package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.QueryBuilderOptions;

/**
 * INTO table
 */
public class IntoTableBlock extends Block {

    private String mTable = null;

    public IntoTableBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Into given table.
    public void into(String table) {
        table = _sanitizeTable(table);
        mTable = table;
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        return "INTO " + mTable;
    }
}
