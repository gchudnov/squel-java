package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.QueryBuilderOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * (INSERT INTO) ... field ... (SELECT ... FROM ...)
 */
public class InsertFieldsFromQueryBlock extends Block {

    List<String> mFields;
    QueryBuilder mQuery;

    public InsertFieldsFromQueryBlock(QueryBuilderOptions options) {
        super(options);
    }

    public void fromQuery(Iterable<String> fields, QueryBuilder query) {
        mFields = new ArrayList<>();
        for(String field: fields) {
            mFields.add(_sanitizeField(field));
        }

        mQuery = query;
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        if(mFields == null || mFields.isEmpty())
            return "";

        return "(" + Util.join(", ", mFields.toArray(new String[mFields.size()])) + ") " + "(" + mQuery.toString() + ")";
    }
}
