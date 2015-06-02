package com.github.gchudnov.squel;

/**
 * DISTINCT
 */
public class DistinctBlock extends Block {

    private boolean mUseDistinct;

    public DistinctBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Add the DISTINCT keyword to the query.
    public void distinct() {
        mUseDistinct = true;
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        return (mUseDistinct ? "DISTINCT" : "");
    }
}
