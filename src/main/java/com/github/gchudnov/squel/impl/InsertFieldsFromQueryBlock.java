package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.QueryBuilderOptions;

/**
 * (INSERT INTO) ... field ... (SELECT ... FROM ...)
 */
public class InsertFieldsFromQueryBlock extends Block {

    public InsertFieldsFromQueryBlock(QueryBuilderOptions options) {
        super(options);
    }
}
