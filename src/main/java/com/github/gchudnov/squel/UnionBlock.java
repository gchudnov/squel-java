package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * UNION
 */
class UnionBlock extends Block {

    class UnionNode {
        Object table; // String or QueryBuilder
        UnionType unionType;

        public UnionNode(String table, UnionType unionType) {
            this.table = table;
            this.unionType = unionType;
        }

        public UnionNode(QueryBuilder table, UnionType unionType) {
            this.table = table;
            this.unionType = unionType;
        }
    }

    List<UnionNode> mUnions = new ArrayList<>();

    UnionBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Add a UNION with the given table/query.
    //
    // 'table' is the name of the table or query to union with.
    //
    //
    // 'type' must be either one of UNION or UNION ALL.... Default is 'UNION'.
    //
    void setUnion(String table, UnionType unionType) {
        table = _sanitizeTable(table);
        mUnions.add(new UnionNode(table, unionType));
    }

    void setUnion(QueryBuilder table, UnionType unionType) {
        mUnions.add(new UnionNode(table, unionType));
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mUnions.isEmpty())
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
}
