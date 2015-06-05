package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * JOIN
 */
class JoinBlock extends Block {

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

    JoinBlock(QueryBuilderOptions options) {
        super(options);
    }

    /**
     * Add a JOIN with the given table.
     * @param table Name of the table to setJoin with.
     * @param alias Optional alias for the table name.
     * @param condition Optional condition (containing an SQL expression) for the JOIN.
     * @param type Join Type.
     */
    void setJoin(String table, String alias, String condition, JoinType type) {
        table = sanitizeTable(table);
        alias = sanitizeTableAlias(alias);
        doJoin(table, alias, condition, type);
    }

    void setJoin(String table, String alias, Expression condition, JoinType type) {
        table = sanitizeTable(table);
        alias = sanitizeTableAlias(alias);
        doJoin(table, alias, condition, type);
    }

    void setJoin(QueryBuilder table, String alias, String condition, JoinType type) {
        String tableName = sanitizeTable(table);
        alias = sanitizeTableAlias(alias);
        doJoin(tableName, alias, condition, type);
    }

    void setJoin(QueryBuilder table, String alias, Expression condition, JoinType type) {
        String tableName = sanitizeTable(table);
        alias = sanitizeTableAlias(alias);
        doJoin(tableName, alias, condition, type);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mJoins.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();
        for (JoinNode j : mJoins) {
            if (sb.length() > 0) {
                sb.append(" ");
            }

            sb.append(j.type);
            sb.append(" JOIN ");

            if(j.table instanceof String) {
                sb.append((String)j.table);
            } else {
                sb.append("(");
                sb.append(j.table.toString());
                sb.append(")");
            }

            if (!Util.isEmpty(j.alias)) {
                sb.append(" ");
                sb.append(j.alias);
            }

            String conditionStr;
            if(j.condition instanceof String) {
                conditionStr = (String)j.condition;
            } else {
                conditionStr = ((Expression)j.condition).toString();
            }

            if (!Util.isEmpty(conditionStr)) {
                sb.append(" ON (");
                sb.append(conditionStr);
                sb.append(")");
            }
        }

        return sb.toString();
    }

    private void doJoin(Object table, String alias, Object condition, JoinType type) {
        if (type == null) {
            type = JoinType.INNER;
        }

        mJoins.add(new JoinNode(table, alias, condition, type));
    }
}
