package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * JOIN
 */
class JoinBlock extends Block {

    private class JoinNode {
        final JoinType type;
        final Object table; // String | QueryBuilder
        final String alias;
        final Object condition; // String | Expression

        JoinNode(Object table, String alias, Object condition, JoinType type) {
            this.table = table;
            this.alias = alias;
            this.condition = condition;
            this.type = type;
        }
    }

    private List<JoinNode> mJoins;

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
        table = Validator.sanitizeTable(table, mOptions);
        alias = Validator.sanitizeTableAlias(alias, mOptions);
        doJoin(table, alias, condition, type);
    }

    void setJoin(String table, String alias, Expression condition, JoinType type) {
        table = Validator.sanitizeTable(table, mOptions);
        alias = Validator.sanitizeTableAlias(alias, mOptions);
        doJoin(table, alias, condition, type);
    }

    void setJoin(QueryBuilder table, String alias, String condition, JoinType type) {
        alias = Validator.sanitizeTableAlias(alias, mOptions);
        doJoin(table, alias, condition, type);
    }

    void setJoin(QueryBuilder table, String alias, Expression condition, JoinType type) {
        alias = Validator.sanitizeTableAlias(alias, mOptions);
        doJoin(table, alias, condition, type);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mJoins == null || mJoins.isEmpty())
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
                conditionStr = j.condition.toString();
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
        if(mJoins == null) {
            mJoins = new ArrayList<>();
        }

        if (type == null) {
            type = JoinType.INNER;
        }

        mJoins.add(new JoinNode(table, alias, condition, type));
    }
}
