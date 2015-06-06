package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL expression builder.
 * A new expression should be created using `Squel.expr()` call.
 */
public final class Expression {

    private enum ExpressionType {
        AND,
        OR
    }

    private class ExpressionNode {
        final ExpressionType type;
        final String expr;
        final Object param;

        final ExpressionNode parent;
        final List<ExpressionNode> nodes = new ArrayList<>();

        ExpressionNode() {
            this.type = null;
            this.expr = null;
            this.param = null;
            this.parent = null;
        }

        ExpressionNode(ExpressionType type, String expr, Object param) {
            this.type = type;
            this.expr = expr;
            this.param = param;
            this.parent = null;
        }

        ExpressionNode(ExpressionType type, ExpressionNode parent) {
            this.type = type;
            this.expr = null;
            this.param = null;
            this.parent = parent;
        }
    }

    private final QueryBuilderOptions mOptions;
    private ExpressionNode mTree = null;
    private ExpressionNode mCurrent = null;

    Expression(QueryBuilderOptions options) {
        this.mOptions = (options != null ? options : new QueryBuilderOptions());
        this.mTree = new ExpressionNode();
        this.mCurrent = this.mTree;
    }

    /**
     * Begin AND nested expression.
     * @return Expression
     */
    public Expression andBegin() {
        return this.doBegin(ExpressionType.AND);
    }

    /**
     * Begin OR nested expression.
     * @return Expression
     */
    public Expression orBegin() {
        return this.doBegin(ExpressionType.OR);
    }

    /**
     * End the current compound expression.
     * @return Expression
     */
    public Expression end() {
        assert mCurrent.parent != null; // "begin() needs to be called"
        mCurrent = mCurrent.parent;
        return this;
    }

    /**
     * Combine the current expression with the given expression using the intersection operator (AND).
     * @param expr Expression to combine with.
     * @return Expression
     */
    public Expression and(String expr) {
        return this.and(expr, null);
    }

    /**
     *  Combine the current expression with the given expression using the intersection operator (AND).
     * @param expr Expression to combine with.
     * @param param Value to substitute.
     * @param <P> Number|String|Boolean|QueryBuilder|Expression|Array|Iterable
     * @return Expression
     */
    public <P> Expression and(String expr, P param) {
        ExpressionNode newNode = new ExpressionNode(ExpressionType.AND, expr, param);
        mCurrent.nodes.add(newNode);
        return this;
    }

    /**
     * Combine the current expression with the given expression using the union operator (OR).
     * @param expr Expression to combine with.
     * @return Expression
     */
    public Expression or(String expr) {
        return this.or(expr, null);
    }

    /**
     * Combine the current expression with the given expression using the union operator (OR).
     * @param expr Expression to combine with.
     * @param param Value to substitute.
     * @param <P> Number|String|Boolean|QueryBuilder|Expression|Array|Iterable
     * @return Expression
     */
    public <P> Expression or(String expr, P param) {
        ExpressionNode newNode = new ExpressionNode(ExpressionType.OR, expr, param);
        mCurrent.nodes.add(newNode);
        return this;
    }

    /**
     * Get the Expression string.
     * @return A String representation of the expression.
     */
    @Override
    public String toString() {
        assert mCurrent.parent == null; // "end() needs to be called"
        return this.doString(mTree);
    }

    /**
     * Begin a nested expression
     * @param op Operator to combine with the current expression
     * @return Expression
     */
    private Expression doBegin(ExpressionType op) {
        ExpressionNode newTree = new ExpressionNode(op, mCurrent);

        mCurrent.nodes.add(newTree);
        mCurrent = newTree;

        return this;
    }

    /**
     * Get a string representation of the given expression tree node.
     * @param node Node to
     * @return String
     */
    private String doString(ExpressionNode node) {
        StringBuilder sb = new StringBuilder();
        String nodeStr;
        for (ExpressionNode child : node.nodes) {
            if (child.expr != null) {
                nodeStr = child.expr.replace("?", Validator.formatValue(child.param, mOptions));
            } else {
                nodeStr = this.doString(child);

                // wrap nested expressions in brackets
                if (!Util.isEmpty(nodeStr)) {
                    nodeStr = "(" + nodeStr + ")";
                }
            }

            if (!Util.isEmpty(nodeStr)) {
                if (sb.length() > 0) {
                    sb.append(" ");
                    sb.append(child.type);
                    sb.append(" ");
                }
                sb.append(nodeStr);
            }
        }

        return sb.toString();
    }
}
