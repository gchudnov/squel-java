package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * JOIN
 */
public class JoinBlock extends Block {

    class JoinNode {
        JoinType type;
        Object table; // String or QueryBuilder
        String alias;
        Object condition; // String or Expression

        JoinNode(Object table, String alias, Object condition, JoinType type) {
            this.table = table;
            this.alias = alias;
            this.condition = condition;
            this.type = type;
        }
    }

    private List<JoinNode> mJoins = new ArrayList<>();

    public JoinBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Add a JOIN with the given table.
    //
    // 'table' is the name of the table to join with.
    //
    // 'alias' is an optional alias for the table name.
    //
    // 'condition' is an optional condition (containing an SQL expression) for the JOIN. If this is an instance of
    // an expression builder then it gets evaluated straight away.
    //
    // 'type' must be either one of INNER, OUTER, LEFT or RIGHT. Default is 'INNER'.
    //
    public void join(String table, String alias, String condition, JoinType type) {
        table = _sanitizeTable(table);
        alias = _sanitizeTableAlias(alias);
        doJoin(table, alias, condition, type);
    }

    public void join(String table, String alias, Expression condition, JoinType type) {
        table = _sanitizeTable(table);
        alias = _sanitizeTableAlias(alias);
        doJoin(table, alias, condition, type);
    }

    public void join(QueryBuilder table, String alias, String condition, JoinType type) {
        String tableName = _sanitizeTable(table);
        alias = _sanitizeTableAlias(alias);
        doJoin(tableName, alias, condition, type);
    }

    public void join(QueryBuilder table, String alias, Expression condition, JoinType type) {
        String tableName = _sanitizeTable(table);
        alias = _sanitizeTableAlias(alias);
        doJoin(tableName, alias, condition, type);
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        String joins = "";
        for (JoinNode j : mJoins) {
            if (!Util.isEmpty(joins)) {
                joins += " ";
            }

            joins += j.type + " JOIN ";

            if(j.table instanceof String) {
                joins += (String)j.table;
            } else {
                joins += "(" + j.table.toString() + ")";
            }

            if (!Util.isEmpty(j.alias)) {
                joins += " " + j.alias;
            }

            String conditionStr;
            if(j.condition instanceof String) {
                conditionStr = (String)j.condition;
            } else {
                conditionStr = ((Expression)j.condition).toString();
            }

            if (!Util.isEmpty(conditionStr)) {
                joins += " ON (" + conditionStr + ")";
            }
        }

        return joins;
    }

    private void doJoin(Object table, String alias, Object condition, JoinType type) {
        if (type == null) {
            type = JoinType.INNER;
        }

        mJoins.add(new JoinNode(table, alias, condition, type));
    }
}
