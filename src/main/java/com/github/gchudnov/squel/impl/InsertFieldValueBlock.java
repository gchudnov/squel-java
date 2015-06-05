package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.QueryBuilderOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * (INSERT INTO) ... field ... value
 */
public class InsertFieldValueBlock extends AbstractSetFieldBlock {

    public InsertFieldValueBlock(QueryBuilderOptions options) {
        super(options);
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        if(mFields.isEmpty()) {
            return "";
        }

        String fields = Util.join(", ", buildFieldNames(mFields).toArray(new String[mFields.size()]));
        String values = Util.join(", ", buildFieldValues(mFields).toArray(new String[mFields.size()]));

        return "(" + fields + ") VALUES (" + values + ")";
    }

    private List<String> buildFieldNames(List<SetNode> nodes) {
        List<String> names = new ArrayList<>();
        for(SetNode n : mFields) {
            names.add(n.field);
        }
        return names;
    }

    private List<String> buildFieldValues(List<SetNode> nodes) {
        List<String> values = new ArrayList<>();
        for(SetNode n : mFields) {
            values.add(_formatValue(n.value));
        }
        return values;
    }
}
