package com.github.gchudnov.squel;

/**
 * DISTINCT
 */
class DistinctBlock extends Block {

    private boolean mUseDistinct;

    DistinctBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Add the DISTINCT keyword to the query.
    void distinct() {
        mUseDistinct = true;
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        return (mUseDistinct ? "DISTINCT" : "");
    }
}
