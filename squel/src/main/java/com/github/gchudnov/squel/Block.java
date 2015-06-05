package com.github.gchudnov.squel;

abstract class Block extends BaseBlock {

    protected Block(QueryBuilderOptions options) {
        super(options);
    }

    String buildStr(QueryBuilder queryBuilder) {
        return "";
    }
}
