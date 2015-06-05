package com.github.gchudnov.squel;

abstract class Block {

    protected QueryBuilderOptions mOptions;

    protected Block(QueryBuilderOptions options) {
        this.mOptions = (options != null ? options : new QueryBuilderOptions());
    }

    String buildStr(QueryBuilder queryBuilder) {
        return "";
    }
}
