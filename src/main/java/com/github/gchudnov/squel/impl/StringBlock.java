package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.QueryBuilderOptions;

/**
 * A String which always gets output
 */
public class StringBlock extends Block {

    private String mStr;

    public StringBlock(QueryBuilderOptions options, String str) {
        super(options);
        mStr = str;
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        return mStr;
    }
}
