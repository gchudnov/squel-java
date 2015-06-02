package com.github.gchudnov.squel;

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
