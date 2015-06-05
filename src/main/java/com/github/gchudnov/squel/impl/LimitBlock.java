package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.QueryBuilderOptions;

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
