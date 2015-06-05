package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * (SELECT) field
 */
class GetFieldBlock extends Block {

    private class FieldNode {
        String name;
        String alias;

        public FieldNode(String name, String alias) {
            this.name = name;
            this.alias = alias;
        }
    }

    private List<FieldNode> mFields = new ArrayList<>();
    private HashMap<String, String> mFieldAliases = new HashMap<>();

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
        String fieldValue = sanitizeField(field);
        String aliasValue = alias != null ? sanitizeFieldAlias(alias) : null;

        doField(fieldValue, aliasValue);
    }

    void setField(QueryBuilder field, String alias) {
        String fieldName = sanitizeField(field);
        String aliasValue = alias != null ? sanitizeFieldAlias(alias) : null;

        doField(fieldName, aliasValue);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mFields.isEmpty())
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

    private void doField(String field, String alias) {
        if (mFieldAliases.containsKey(field) && mFieldAliases.get(field).equals(alias)) {
            return;
        }

        mFieldAliases.put(field, alias);
        mFields.add(new FieldNode(field, alias));
    }
}
