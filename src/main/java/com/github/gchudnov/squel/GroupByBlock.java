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

    // Add a GROUP BY transformation for the given field.
    void group(String field) {
        field = _sanitizeField(field);
        mGroups.add(field);
    }

    @Override
    String buildStr(QueryBuilder queryBuilder) {
        String str = Util.join(", ", mGroups.toArray(new String[mGroups.size()]));
        return (!Util.isEmpty(str) ? "GROUP BY " + str : "");
    }
}
