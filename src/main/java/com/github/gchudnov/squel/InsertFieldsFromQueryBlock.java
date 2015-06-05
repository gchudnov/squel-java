package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * (INSERT INTO) ... setField ... (SELECT ... FROM ...)
 */
class InsertFieldsFromQueryBlock extends Block {

    List<String> mFields;
    QueryBuilder mQuery;

    InsertFieldsFromQueryBlock(QueryBuilderOptions options) {
        super(options);
    }

    void setFromQuery(Iterable<String> fields, QueryBuilder query) {
        mFields = new ArrayList<>();
        for(String field: fields) {
            mFields.add(sanitizeField(field));
        }

        mQuery = query;
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mFields == null || mFields.isEmpty())
            return "";

        return "(" + Util.join(", ", mFields) + ") " + "(" + mQuery.toString() + ")";
    }
}
