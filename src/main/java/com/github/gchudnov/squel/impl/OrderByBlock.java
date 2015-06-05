package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.OrderDirection;
import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.QueryBuilderOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * ORDER BY
 */
public class OrderByBlock extends Block {

    class OrderNode {
        String field;
        OrderDirection dir;

        public OrderNode(String field, OrderDirection dir) {
            this.field = field;
            this.dir = dir;
        }
    }

    private List<OrderNode> mOrders = new ArrayList<>();

    public OrderByBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Add an ORDER BY transformation for the given field in the given order.
    // To specify descending order pass false for the 'asc' parameter.
    public void setOrder(String field, OrderDirection dir) {
        field = _sanitizeField(field);
        mOrders.add(new OrderNode(field, dir));
    }

    public void setOrder(String field) {
        setOrder(field, OrderDirection.ASC);
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        if(mOrders.isEmpty())
            return "";

        String orders = "";
        for(OrderNode o: mOrders) {
            if(orders.length() > 0) {
                orders += ", ";
            }

            orders += o.field + " " + (o.dir == OrderDirection.ASC ? "ASC" : "DESC");
        }

        return "ORDER BY " + orders;
    }
}
