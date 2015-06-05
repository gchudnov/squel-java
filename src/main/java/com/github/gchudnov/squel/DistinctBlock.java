package com.github.gchudnov.squel;

/**
 * DISTINCT
 */
class DistinctBlock extends Block {

    private boolean mIsDistinct;

    DistinctBlock(QueryBuilderOptions options) {
        super(options);
    }

    void setDistinct() {
        mIsDistinct = true;
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        return (mIsDistinct ? "DISTINCT" : "");
    }
}
