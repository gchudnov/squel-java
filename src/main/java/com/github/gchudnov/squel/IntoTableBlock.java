package com.github.gchudnov.squel;

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
        // do not allow nested table to be the target
        //@table = @_sanitizeTable(table, false)
        mTable = table;
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        // if not @table then throw new Error "into() needs to be called"
        return "INTO " + mTable;
    }
}
