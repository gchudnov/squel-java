package com.github.gchudnov.squel;

import java.util.ArrayList;

/**
 * SQL expression builder.
 */
public final class Expression extends BaseBlock {

    class ExpressionNode {
        String type = null;
        ExpressionNode parent = null;
        String expr = null;

        Object param = null;

        ArrayList<ExpressionNode> nodes = new ArrayList<>();
    }

    private ExpressionNode mTree = null;
    private ExpressionNode mCurrent = null;

    public Expression() {
        this(null);
    }

    public Expression(QueryBuilderOptions options) {
        super(options);
        this.mTree = new ExpressionNode();
        this.mCurrent = this.mTree;
    }

    /**
     * Begin AND nested expression
     * @return Expression
     */
    public Expression and_begin() {
        return this.doBegin("AND");
    }

    /**
     * Begin OR nested expression
     * @return Expression
     */
    public Expression or_begin() {
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
     * @param expr Expression to combine with
     * @return Expression
     */
    public Expression and(String expr) {
        return this.and(expr, null);
    }

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
                nodeStr = child.expr.replace("?", this._formatValue(child.param));
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
