package com.github.gchudnov.squel;

import java.util.ArrayList;

/**
 * SQL expression builder.
 * A new expression should be created using `Squel.expr()` call.
 */
public final class Expression {

    private class ExpressionNode {
        String type = null;
        ExpressionNode parent = null;
        String expr = null;

        Object param = null;

        ArrayList<ExpressionNode> nodes = new ArrayList<>();
    }

    private QueryBuilderOptions mOptions;
    private ExpressionNode mTree = null;
    private ExpressionNode mCurrent = null;

    Expression() {
        this(null);
    }

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
        return this.doBegin("AND");
    }

    /**
     * Begin OR nested expression.
     * @return Expression
     */
    public Expression orBegin() {
        return this.doBegin("OR");
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
        ExpressionNode newNode = new ExpressionNode();
        newNode.type = "AND";
        newNode.expr = expr;
        newNode.param = param;

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
        ExpressionNode newNode = new ExpressionNode();
        newNode.type = "OR";
        newNode.expr = expr;
        newNode.param = param;

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
    private Expression doBegin(String op) {
        ExpressionNode newTree = new ExpressionNode();

        newTree.type = op;
        newTree.parent = mCurrent;

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
