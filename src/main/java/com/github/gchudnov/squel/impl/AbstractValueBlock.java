package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.QueryBuilderOptions;

/**
 * A values which gets output as is
 */
public class AbstractValueBlock<T> extends Block {

    private T mT;

    public AbstractValueBlock(QueryBuilderOptions options) {
        super(options);
        mT = null;
    }

    public void _setValue(T val) {
        this.mT = val;
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        return (mT == null ? "" : mT.toString());
    }
}
