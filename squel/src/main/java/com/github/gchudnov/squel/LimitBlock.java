package com.github.gchudnov.squel;

/**
 * LIMIT
 */
class LimitBlock extends Block {

    private Integer mLimit;

    LimitBlock(QueryBuilderOptions options) {
        super(options);
    }

    void setLimit(int value) {
        assert value >= 0;
        mLimit = value;
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        return (mLimit != null ? "LIMIT " + mLimit.toString() : "");
    }
}
