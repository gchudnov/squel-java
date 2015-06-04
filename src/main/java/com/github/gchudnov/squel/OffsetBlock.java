package com.github.gchudnov.squel;

/**
 * OFFSET x
 */
public class OffsetBlock extends Block {

    private Integer mOffset;

    public OffsetBlock(QueryBuilderOptions options) {
        super(options);
    }

    public void setOffset(int value) {
        assert value >= 0;
        mOffset = value;
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        return (mOffset != null ? "OFFSET " + mOffset.toString() : "");
    }
}
