package com.github.gchudnov.squel;

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

    private List<TableNode> mTables = new ArrayList<>();

    public AbstractTableBlock(QueryBuilderOptions options) {
        super(options);
    }

    // com.github.gchudnov.squel.Update given table.
    //
    // An alias may also be specified for the table.
    //
    // Concrete subclasses should provide a method which calls this
    protected void _table(String table, String alias) {
        if(alias != null) {
            alias = _sanitizeTableAlias(alias);
        }
        table = _sanitizeTable(table);

        mTables.add(new TableNode(table, alias));
    }

    protected void _table(QueryBuilder table, String alias) {
        String tableName = "(" + table.toString() + ")";
        _table(tableName, alias);
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        assert !mTables.isEmpty();

        String tables = "";
        for (TableNode table : mTables) {
            if (!Util.isEmpty(tables)) {
                tables += ", ";
            }
            tables += table.table;

            // building a nested query -- TODO

            if (table.alias != null) {
                tables += " " + table.alias;
            }
        }

        return tables;
    }
}
