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
        assert !mFields.isEmpty();

        StringBuilder sb = new StringBuilder();
        for(SetNode n : mFields) {
            if(sb.length() > 0) {
                sb.append(", ");
            }

            sb.append(n.field);
            sb.append(" = ");
            sb.append(_formatValue(n.value));
        }

        return "SET " + sb.toString();
    }
}
