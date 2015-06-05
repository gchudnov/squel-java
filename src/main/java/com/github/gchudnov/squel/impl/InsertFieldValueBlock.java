package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.QueryBuilderOptions;

/**
 * (INSERT INTO) ... field ... value
 */
public class InsertFieldValueBlock extends AbstractSetFieldBlock {

    public InsertFieldValueBlock(QueryBuilderOptions options) {
        super(options);
    }
}
