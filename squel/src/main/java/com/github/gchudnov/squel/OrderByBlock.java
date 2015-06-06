package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * ORDER BY
 */
class OrderByBlock extends Block {

    private class OrderNode {
        final String field;
        final SortOrder dir;

        public OrderNode(String field, SortOrder dir) {
            this.field = field;
            this.dir = dir;
        }
    }

    private List<OrderNode> mOrders;

    OrderByBlock(QueryBuilderOptions options) {
        super(options);
    }

    /**
     * Add an ORDER BY transformation for the given setField in the given order.
     * @param field Field
     * @param dir Order
     */
    void setOrder(String field, SortOrder dir) {
        if(mOrders == null) {
            mOrders = new ArrayList<>();
        }
        field = Validator.sanitizeField(field, mOptions);
        mOrders.add(new OrderNode(field, dir));
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mOrders == null || mOrders.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();
        for(OrderNode o: mOrders) {
            if(sb.length() > 0) {
                sb.append(", ");
            }

            sb.append(o.field);
            sb.append(" ");
            sb.append(o.dir == SortOrder.ASC ? "ASC" : "DESC");
        }

        return "ORDER BY " + sb.toString();
    }
}
