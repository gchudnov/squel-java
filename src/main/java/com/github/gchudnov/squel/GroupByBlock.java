package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * GROUP BY
 */
class GroupByBlock extends Block {

    List<String> mGroups = new ArrayList<>();

    GroupByBlock(QueryBuilderOptions options) {
        super(options);
    }

    void setGroup(String field) {
        field = sanitizeField(field);
        mGroups.add(field);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mGroups.isEmpty()) {
            return "";
        }

        return "GROUP BY " + Util.join(", ", mGroups);
    }
}
