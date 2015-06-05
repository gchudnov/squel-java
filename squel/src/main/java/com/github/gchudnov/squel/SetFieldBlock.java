package com.github.gchudnov.squel;

/**
 * (UPDATE) SET setField=value
 */
class SetFieldBlock extends SetFieldBlockBase {

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
            sb.append(Validator.formatValue(n.value, mOptions));
        }

        return "SET " + sb.toString();
    }
}
