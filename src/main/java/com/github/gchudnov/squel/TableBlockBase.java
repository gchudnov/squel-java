package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * Table base class
 */
abstract class TableBlockBase extends Block {

    class TableNode {
        String table;
        String alias;

        public TableNode(String table, String alias) {
            this.table = table;
            this.alias = alias;
        }
    }

    protected List<TableNode> mTables = new ArrayList<>();

    TableBlockBase(QueryBuilderOptions options) {
        super(options);
    }

    protected void setTable(String table, String alias) {
        table = sanitizeTable(table);
        alias = sanitizeTableAlias(alias);
        doTable(table, alias);
    }

    protected void setTable(QueryBuilder table, String alias) {
        String tableName = sanitizeTable(table);
        alias = sanitizeTableAlias(alias);
        doTable(tableName, alias);
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

    private void doTable(String table, String alias) {
        mTables.add(new TableNode(table, alias));
    }
}
