package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * UNION
 */
public class UnionBlock extends Block {

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

    public UnionBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Add a UNION with the given table/query.
    //
    // 'table' is the name of the table or query to union with.
    //
    //
    // 'type' must be either one of UNION or UNION ALL.... Default is 'UNION'.
    //
    public void setUnion(String table, UnionType unionType) {
        table = _sanitizeTable(table);
        mUnions.add(new UnionNode(table, unionType));
    }

    public void setUnion(QueryBuilder table, UnionType unionType) {
        mUnions.add(new UnionNode(table, unionType));
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        String result = "";
        for(UnionNode j: mUnions) {
            if(!result.isEmpty()) {
                result += " ";
            }

            result += (j.unionType == UnionType.UNION ? "UNION" : "UNION ALL") + " ";

            if(j.table instanceof String) {
                result += j.table;
            } else {
                result += "(" + j.table.toString() + ")";
            }
        }
        return result;
    }
}
