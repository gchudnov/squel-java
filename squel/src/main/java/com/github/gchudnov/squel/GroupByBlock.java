package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * GROUP BY
 */
class GroupByBlock extends Block {

    List<String> mGroups;

    GroupByBlock(QueryBuilderOptions options) {
        super(options);
    }

    void setGroup(String field) {
        if(mGroups == null) {
            mGroups = new ArrayList<>();
        }

        field = Validator.sanitizeField(field, mOptions);
        mGroups.add(field);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        if(mGroups == null || mGroups.isEmpty()) {
            return "";
        }

        return "GROUP BY " + Util.join(", ", mGroups);
    }
}
