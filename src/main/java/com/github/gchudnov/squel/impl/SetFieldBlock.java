package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.QueryBuilderOptions;

/**
 * (UPDATE) SET field=value
 */
public class SetFieldBlock extends AbstractSetFieldBlock {

    public SetFieldBlock(QueryBuilderOptions options) {
        super(options);
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        String result = "";
        for(SetNode n : mFields) {
            if(!result.isEmpty()) {
                result += ", ";
            }

            result += n.field + " = " + _formatValue(n.value);
        }

        return "SET " + result;
    }
}
