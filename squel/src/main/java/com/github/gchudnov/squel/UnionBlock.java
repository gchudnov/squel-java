package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * UNION
 */
class UnionBlock extends Block {

    private class UnionNode {
        final Object table; // String or QueryBuilder
        final UnionType unionType;

        public UnionNode(String table, UnionType unionType) {
            this.table = table;
            this.unionType = unionType;
        }

        public UnionNode(QueryBuilder table, UnionType unionType) {
            this.table = table;
            this.unionType = unionType;
        }
    }

    private List<UnionNode> mUnions;

    UnionBlock(QueryBuilderOptions options) {
        super(options);
    }

    /**
     * Add a UNION with the given table/query.
     * @param table Name of the table or query to union with.
     * @param unionType Type of the union.
     */
    void setUnion(String table, UnionType unionType) {
        table = Validator.sanitizeTable(table, mOptions);
        ensureUnionsList();
        mUnions.add(new UnionNode(table, unionType));
    }

    void setUnion(QueryBuilder table, UnionType unionType) {
        ensureUnionsList();
        mUnions.add(new UnionNode(table, unionType));
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mUnions == null || mUnions.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();
        for(UnionNode j: mUnions) {
            if(sb.length() > 0) {
                sb.append(" ");
            }

            sb.append(UnionType.toSql(j.unionType));
            sb.append(" ");

            if(j.table instanceof String) {
                sb.append(j.table);
            } else {
                sb.append("(");
                sb.append(j.table.toString());
                sb.append(")");
            }
        }

        return sb.toString();
    }

    private void ensureUnionsList() {
        if(mUnions == null) {
            mUnions = new ArrayList<>();
        }
    }
}
