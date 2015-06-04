package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * WHERE
 */
public class WhereBlock extends Block {

    class WhereNode {
        String text;
        Object param;

        public WhereNode(String text, Object param) {
            this.text = text;
            this.param = param;
        }
    }

    List<WhereNode> mWheres = new ArrayList<>();

    public WhereBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Add a WHERE condition.
    //
    // When the final query is constructed all the WHERE conditions are combined using the intersection (AND) operator.
    public <P> void where(String condition, P param) {
        if (!Util.isEmpty(condition)) {
            mWheres.add(new WhereNode(condition, param));
        }
    }

    public <P> void where(Expression condition, P param) {
        String conditionStr = condition.toString();

        if (!Util.isEmpty(conditionStr)) {
            mWheres.add(new WhereNode(conditionStr, param));
        }
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        if (mWheres.isEmpty())
            return "";

        String whereStr = "";
        for (WhereNode where : mWheres) {
            if (!Util.isEmpty(whereStr)) {
                whereStr += ") AND (";
            }

            String newText = where.text.replace("?", this._formatValue(where.param));
            whereStr += newText;
        }

        return "WHERE (" + whereStr + ")";
    }
}
