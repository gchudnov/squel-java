package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * (INSERT INTO) ... setField ... value
 */
class InsertFieldValueBlock extends SetFieldBlockBase {

    InsertFieldValueBlock(QueryBuilderOptions options) {
        super(options);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mFields.isEmpty()) {
            return "";
        }

        String fields = Util.join(", ", buildFieldNames(mFields));
        String values = Util.join(", ", buildFieldValues(mFields));

        return "(" + fields + ") VALUES (" + values + ")";
    }

    private List<String> buildFieldNames(List<SetNode> nodes) {
        List<String> names = new ArrayList<>();
        for(SetNode n : nodes) {
            names.add(n.field);
        }
        return names;
    }

    private List<String> buildFieldValues(List<SetNode> nodes) {
        List<String> values = new ArrayList<>();
        for(SetNode n : nodes) {
            values.add(Validator.formatValue(n.value));
        }
        return values;
    }
}
