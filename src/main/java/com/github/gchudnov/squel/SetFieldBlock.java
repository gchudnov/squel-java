package com.github.gchudnov.squel;

/**
 * (UPDATE) SET field=value
 */
class SetFieldBlock extends AbstractSetFieldBlock {

    SetFieldBlock(QueryBuilderOptions options) {
        super(options);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
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
