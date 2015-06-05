package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * ORDER BY
 */
class OrderByBlock extends Block {

    class OrderNode {
        String field;
        OrderDirection dir;

        public OrderNode(String field, OrderDirection dir) {
            this.field = field;
            this.dir = dir;
        }
    }

    private List<OrderNode> mOrders = new ArrayList<>();

    OrderByBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Add an ORDER BY transformation for the given field in the given order.
    // To specify descending order pass false for the 'asc' parameter.
    void setOrder(String field, OrderDirection dir) {
        field = _sanitizeField(field);
        mOrders.add(new OrderNode(field, dir));
    }

    void setOrder(String field) {
        setOrder(field, OrderDirection.ASC);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mOrders.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();
        for(OrderNode o: mOrders) {
            if(sb.length() > 0) {
                sb.append(", ");
            }

            sb.append(o.field);
            sb.append(" ");
            sb.append(o.dir == OrderDirection.ASC ? "ASC" : "DESC");
        }

        return "ORDER BY " + sb.toString();
    }
}
