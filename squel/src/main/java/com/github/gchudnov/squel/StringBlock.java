package com.github.gchudnov.squel;

/**
 * A String which always gets output
 */
class StringBlock extends Block {

    private final String mText;

    StringBlock(QueryBuilderOptions options, String str) {
        super(options);
        mText = str;
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        assert mText != null;
        return mText;
    }
}
