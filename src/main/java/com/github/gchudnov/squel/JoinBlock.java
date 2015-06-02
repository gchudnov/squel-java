package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * JOIN
 */
public class JoinBlock extends Block {

    class JoinNode {
        String type;
        String table;
        String alias;
        String condition;

        JoinNode(String type, String table, String alias, String condition) {
            this.type = type;
            this.table = table;
            this.alias = alias;
            this.condition = condition;
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
    public JoinBlock join(String table, String alias, String condition, String type) {
        if (Util.isEmpty(type)) {
            type = "INNER";
        }

//        table = @_sanitizeTable(table, true)
//        alias = @_sanitizeTableAlias(alias) if alias
//        condition = @_sanitizeCondition(condition) if condition

        mJoins.add(new JoinNode(type, table, alias, condition));
        return this;
    }

    // Add a LEFT JOIN with the given table.
    public JoinBlock left_join(String table, String alias, String condition) {
        return this.join(table, alias, condition, "LEFT");
    }

    // Add a RIGHT JOIN with the given table.
    public JoinBlock right_join(String table, String alias, String condition) {
        return this.join(table, alias, condition, "RIGHT");
    }

    // Add a OUTER JOIN with the given table.
    public JoinBlock outer_join(String table, String alias, String condition) {
        return this.join(table, alias, condition, "OUTER");
    }

    // Add a LEFT JOIN with the given table.
    public JoinBlock left_outer_join(String table, String alias, String condition) {
        return this.join(table, alias, condition, "LEFT OUTER");
    }

    // Add an FULL JOIN with the given table.
    public JoinBlock full_join(String table, String alias, String condition) {
        return this.join(table, alias, condition, "FULL");
    }

    // Add an CROSS JOIN with the given table.
    public JoinBlock cross_join(String table, String alias, String condition) {
        return this.join(table, alias, condition, "CROSS");
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        String joins = "";
        for (JoinNode j : mJoins) {
            if (!Util.isEmpty(joins)) {
                joins += " ";
            }

            // TODO:  if "string" is typeof j.table

            joins += j.type + " JOIN ";
            joins += j.table;

            if (!Util.isEmpty(j.alias)) {
                joins += " " + j.alias;
            }

            if (!Util.isEmpty(j.condition)) {
                joins += " ON (" + j.condition + ")";
            }
        }

        return joins;
    }
}
