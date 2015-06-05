package com.github.gchudnov.squel;

/**
 * OFFSET x
 */
class OffsetBlock extends Block {

    private Integer mOffset;

    OffsetBlock(QueryBuilderOptions options) {
        super(options);
    }

    void setOffset(int value) {
        assert value >= 0;
        mOffset = value;
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        return (mOffset != null ? "OFFSET " + mOffset.toString() : "");
    }
}
