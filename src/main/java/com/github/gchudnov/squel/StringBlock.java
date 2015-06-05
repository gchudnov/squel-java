package com.github.gchudnov.squel;

/**
 * A String which always gets output
 */
class StringBlock extends Block {

    private String mStr;

    StringBlock(QueryBuilderOptions options, String str) {
        super(options);
        mStr = str;
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        return mStr;
    }
}
