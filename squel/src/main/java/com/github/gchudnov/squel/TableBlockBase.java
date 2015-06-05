package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * Table base class
 */
abstract class TableBlockBase extends Block {

    private class TableNode {
        final String table;
        final String alias;

        public TableNode(String table, String alias) {
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
        String tableName = Validator.sanitizeTable(table);
        alias = Validator.sanitizeTableAlias(alias, mOptions);
        doSetTable(tableName, alias);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        assert !mTables.isEmpty();

        StringBuilder sb = new StringBuilder();
        for (TableNode table : mTables) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(table.table);

            if (table.alias != null) {
                sb.append(" ");
                sb.append(table.alias);
            }
        }

        return sb.toString();
    }

    private void doSetTable(String table, String alias) {
        if(mTables == null) {
            mTables = new ArrayList<>();
        }

        mTables.add(new TableNode(table, alias));
    }
}
