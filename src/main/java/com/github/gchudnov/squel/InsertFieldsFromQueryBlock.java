package com.github.gchudnov.squel;

/**
 * (INSERT INTO) ... field ... (SELECT ... FROM ...)
 */
public class InsertFieldsFromQueryBlock extends Block {

    public InsertFieldsFromQueryBlock(QueryBuilderOptions options) {
        super(options);
    }
}
