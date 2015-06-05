package com.github.gchudnov.squel;

abstract class Block {

    final QueryBuilderOptions mOptions;

    Block(QueryBuilderOptions options) {
        this.mOptions = (options != null ? options : new QueryBuilderOptions());
    }

    String buildStr(QueryBuilder queryBuilder) {
        return "";
    }
}
