package com.github.gchudnov.squel;

import com.github.gchudnov.squel.impl.Util;

import java.util.ArrayList;

/**
 * An SQL expression builder.
 *
 * SQL expressions are used in WHERE and ON clauses to filter data by various criteria.
 *
 * This builder works by building up the expression as a hierarchical tree of nodes. The toString() method then
 * traverses this tree in order to build the final expression string.
 *
 * cls.Expressions can be nested. Nested expression contains can themselves contain nested expressions.
 * When rendered a nested expression will be fully contained within brackets.
 *
 * All the build methods in this object return the object instance for chained method calling purposes.
 */
public class Expression extends BaseBuilder {

    class ExpressionNode {

        String type = null;
        ExpressionNode parent = null;
        String expr = null;

        Object param = null;

        ArrayList<ExpressionNode> nodes = new ArrayList<>();
    }

    // The expression tree.
    private ExpressionNode mTree = null;

    // The part of the expression tree we're currently working on.
    private ExpressionNode mCurrent = null;

    // Initialise the expression.
    public Expression() {
        super(new QueryBuilderOptions());
        this.mTree = new ExpressionNode();
        this.mCurrent = this.mTree;
    }


    // Begin a nested expression and combine it with the current expression using the given operator.
    private Expression _begin(String op) {
        ExpressionNode newTree = new ExpressionNode();
        newTree.type = op;
        newTree.parent = mCurrent;
        mCurrent.nodes.add(newTree);
        mCurrent = mCurrent.nodes.get(mCurrent.nodes.size() - 1);
        return this;
    }

    // Begin a nested expression and combine it with the current expression using the intersection operator (AND).
    public Expression and_begin() {
        return this._begin("AND");
    }

    // Begin a nested expression and combine it with the current expression using the union operator (OR).
    public Expression or_begin() {
        return this._begin("OR");
    }

    // End the current compound expression.
    //
    // This will throw an error if begin() hasn't been called yet.
    public Expression end() {
        assert mCurrent.parent != null; // "begin() needs to be called"

        mCurrent = mCurrent.parent;
        return this;
    }

    // Combine the current expression with the given expression using the intersection operator (AND).
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

    // Combine the current expression with the given expression using the union operator (OR).
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

    // Get the final fully constructed expression string.
    @Override
    public String toString() {
        assert mCurrent.parent == null; // "end() needs to be called"

        return this._toString(mTree);
    }

    // Get a string representation of the given expression tree node.
    private String _toString(ExpressionNode node) {
        String str = "";
        String nodeStr;
        for (ExpressionNode child : node.nodes) {
            if (child.expr != null) {
                nodeStr = child.expr;
                nodeStr = nodeStr.replace("?", this._formatValue(child.param)); // child.paramType.cast(child.param)
            } else {
                nodeStr = this._toString(child);

                // wrap nested expressions in brackets
                if (!Util.isEmpty(nodeStr)) {
                    nodeStr = "(" + nodeStr + ")";
                }
            }

            if (!Util.isEmpty(nodeStr)) {
                if (!Util.isEmpty(str)) {
                    str += " " + child.type + " ";
                }
                str += nodeStr;
            }
        }

        return str;
    }

}
