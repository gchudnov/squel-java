package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * (INSERT INTO) ... field ... (SELECT ... FROM ...)
 */
class InsertFieldsFromQueryBlock extends Block {

    List<String> mFields;
    QueryBuilder mQuery;

    InsertFieldsFromQueryBlock(QueryBuilderOptions options) {
        super(options);
    }

    void fromQuery(Iterable<String> fields, QueryBuilder query) {
        mFields = new ArrayList<>();
        for(String field: fields) {
            mFields.add(_sanitizeField(field));
        }

        mQuery = query;
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mFields == null || mFields.isEmpty())
            return "";

        return "(" + Util.join(", ", mFields.toArray(new String[mFields.size()])) + ") " + "(" + mQuery.toString() + ")";
    }
}
