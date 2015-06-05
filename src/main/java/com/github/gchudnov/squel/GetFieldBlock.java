package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * (SELECT) Get field
 */
class GetFieldBlock extends Block {

    class FieldNode {
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

    // Add the given fields to the final result set.
    //
    // The parameter is an Object containing field names (or database functions) as the keys and aliases for the fields
    // as the values. If the value for a key is null then no alias is set for that field.
    //
    // Internally this method simply calls the field() method of this block to add each individual field.
    //
    // options.ignorePeriodsForFieldNameQuotes - whether to ignore period (.) when automatically quoting the field name
    void fields(List<String> fields) {
        for (String field : fields) {
            this.field(field, null);
        }
    }

    // Add the given field to the final result set.
    //
    // The 'field' parameter does not necessarily have to be a fieldname. It can use database functions too,
    // e.g. DATE_FORMAT(a.started, "%H")
    //
    // An alias may also be specified for this field.
    //
    // options.ignorePeriodsForFieldNameQuotes - whether to ignore period (.) when automatically quoting the field name
    void field(String field, String alias) {
        String fieldValue = _sanitizeField(field);
        String aliasValue = alias != null ? _sanitizeFieldAlias(alias) : null;

        doField(fieldValue, aliasValue);
    }

    void field(QueryBuilder field, String alias) {
        String fieldName = _sanitizeField(field);
        String aliasValue = alias != null ? _sanitizeFieldAlias(alias) : null;

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
