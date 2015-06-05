package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * WHERE
 */
class WhereBlock extends Block {

    class WhereNode {
        String text;
        Object param;

        public WhereNode(String text, Object param) {
            this.text = text;
            this.param = param;
        }
    }

    private List<WhereNode> mWheres = new ArrayList<>();

    WhereBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Add a WHERE condition.
    //
    // When the final query is constructed all the WHERE conditions are combined using the intersection (AND) operator.
    <P> void where(String condition, P param) {
        if (!Util.isEmpty(condition)) {
            mWheres.add(new WhereNode(condition, param));
        }
    }

    <P> void where(Expression condition, P param) {
        String conditionStr = condition.toString();

        if (!Util.isEmpty(conditionStr)) {
            mWheres.add(new WhereNode(conditionStr, param));
        }
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if (mWheres.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();
        for (WhereNode where : mWheres) {
            if (sb.length() > 0) {
                sb.append(") AND (");
            }

            sb.append(where.text.replace("?", this._formatValue(where.param)));
        }

        return "WHERE (" + sb.toString() + ")";
    }
}
