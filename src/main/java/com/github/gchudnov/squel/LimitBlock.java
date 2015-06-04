package com.github.gchudnov.squel;

/**
 * LIMIT
 */
public class LimitBlock extends Block {

    private Integer mLimit;

    public LimitBlock(QueryBuilderOptions options) {
        super(options);
    }

    public void setLimit(int value) {
        assert value >= 0;
        mLimit = value;
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        return (mLimit != null ? "LIMIT " + mLimit.toString() : "");
    }
}
