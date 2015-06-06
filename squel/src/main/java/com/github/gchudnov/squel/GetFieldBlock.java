package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * (SELECT) field
 */
class GetFieldBlock extends Block {

    private class FieldNode {
        final String name;
        final String alias;

        public FieldNode(String name, String alias) {
            this.name = name;
            this.alias = alias;
        }
    }

    private List<FieldNode> mFields;
    private HashMap<String, String> mFieldAliases;

    GetFieldBlock(QueryBuilderOptions options) {
        super(options);
    }

    /**
     * Add the given fields to the result.
     * @param fields A collection of fields to add
     */
    void setFields(Iterable<String> fields) {
        for (String field : fields) {
            this.setField(field, null);
        }
    }

    /**
     * Add the given field to the final result.
     * @param field Field to add
     * @param alias Field's alias
     */
    void setField(String field, String alias) {
        String fieldValue = Validator.sanitizeField(field, mOptions);
        String aliasValue = (alias != null ? Validator.sanitizeFieldAlias(alias, mOptions) : null);

        doSetField(fieldValue, aliasValue);
    }

    void setField(QueryBuilder field, String alias) {
        String fieldName = Validator.sanitizeField(field);
        String aliasValue = (alias != null ? Validator.sanitizeFieldAlias(alias, mOptions) : null);

        doSetField(fieldName, aliasValue);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mFields == null || mFields.isEmpty())
            return "*";

        StringBuilder sb = new StringBuilder();
        for (FieldNode field : mFields) {
            if (sb.length() > 0) {
                sb.append(", ");
            }

            sb.append(field.name);

            if (!Util.isEmpty(field.alias)) {
                sb.append(" AS ");
                sb.append(field.alias);
            }
        }

        return sb.toString();
    }

    private void doSetField(String field, String alias) {
        if(mFields == null) {
            mFields = new ArrayList<>();
        }

        if(mFieldAliases == null) {
            mFieldAliases = new HashMap<>();
        }

        if (mFieldAliases.containsKey(field) && mFieldAliases.get(field).equals(alias)) {
            return;
        }

        mFieldAliases.put(field, alias);
        mFields.add(new FieldNode(field, alias));
    }
}
