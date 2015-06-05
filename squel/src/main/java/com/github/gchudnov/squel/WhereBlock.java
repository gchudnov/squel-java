package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * WHERE
 */
class WhereBlock extends Block {

    private class WhereNode {
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

    /**
     * Add a WHERE condition.
     * @param condition Condition to add
     * @param param Parameter to add to condition.
     * @param <P> Type of the parameter to add.
     */
    <P> void setWhere(String condition, P param) {
        assert condition != null;
        mWheres.add(new WhereNode(condition, param));
    }

    <P> void setWhere(Expression condition, P param) {
        assert condition != null;
        mWheres.add(new WhereNode(condition.toString(), param));
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if (mWheres == null || mWheres.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();
        for (WhereNode where : mWheres) {
            if (sb.length() > 0) {
                sb.append(") AND (");
            }

            sb.append(where.text.replace("?", Validator.formatValue(where.param, mOptions)));
        }

        return "WHERE (" + sb.toString() + ")";
    }
}
