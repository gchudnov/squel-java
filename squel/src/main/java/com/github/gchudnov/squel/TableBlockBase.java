package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * Table base class
 */
abstract class TableBlockBase extends Block {

    private class TableNode {
        final Object table; // String | QueryBuilder
        final String alias;

        public TableNode(Object table, String alias) {
            this.table = table;
            this.alias = alias;
        }
    }

    List<TableNode> mTables;

    TableBlockBase(QueryBuilderOptions options) {
        super(options);
    }

    void setTable(String table, String alias) {
        table = Validator.sanitizeTable(table, mOptions);
        alias = Validator.sanitizeTableAlias(alias, mOptions);
        doSetTable(table, alias);
    }

    void setTable(QueryBuilder table, String alias) {
        alias = Validator.sanitizeTableAlias(alias, mOptions);
        doSetTable(table, alias);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        assert mTables != null && !mTables.isEmpty();

        StringBuilder sb = new StringBuilder();
        for (TableNode tab : mTables) {
            if (sb.length() > 0) {
                sb.append(", ");
            }

            if(tab.table instanceof String) {
                sb.append((String)tab.table);
            } else {
                sb.append("(");
                sb.append(tab.table.toString());
                sb.append(")");
            }

            if (tab.alias != null) {
                sb.append(" ");
                sb.append(tab.alias);
            }
        }

        return sb.toString();
    }

    private void doSetTable(Object table, String alias) {
        if(mTables == null) {
            mTables = new ArrayList<>();
        }

        mTables.add(new TableNode(table, alias));
    }
}
