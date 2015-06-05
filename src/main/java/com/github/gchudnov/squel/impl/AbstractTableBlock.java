package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.QueryBuilderOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Table specifier base class
 * <p>
 * Additional options
 * - singleTable - only allow one table to be specified  (default: false)
 * - allowNested - allow nested query to be specified as a table    (default: false)
 */
public class AbstractTableBlock extends Block {

    class TableNode {
        String table;
        String alias;

        public TableNode(String table, String alias) {
            this.table = table;
            this.alias = alias;
        }
    }

    protected List<TableNode> mTables = new ArrayList<>();

    public AbstractTableBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Update given table.
    //
    // An alias may also be specified for the table.
    //
    // Concrete subclasses should provide a method which calls this
    protected void _table(String table, String alias) {
        table = _sanitizeTable(table);
        alias = _sanitizeTableAlias(alias);
        doTable(table, alias);
    }

    protected void _table(QueryBuilder table, String alias) {
        String tableName = _sanitizeTable(table);
        alias = _sanitizeTableAlias(alias);
        doTable(tableName, alias);
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
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
