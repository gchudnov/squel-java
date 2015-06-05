package com.github.gchudnov.squel.impl;

import com.github.gchudnov.squel.QueryBuilder;
import com.github.gchudnov.squel.QueryBuilderOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * GROUP BY
 */
public class GroupByBlock extends Block {

    List<String> mGroups = new ArrayList<>();

    public GroupByBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Add a GROUP BY transformation for the given field.
    public void group(String field) {
        field = _sanitizeField(field);
        mGroups.add(field);
    }

    @Override
    public String buildStr(QueryBuilder queryBuilder) {
        String str = Util.join(", ", mGroups.toArray(new String[mGroups.size()]));
        return (!Util.isEmpty(str) ? "GROUP BY " + str : "");
    }
}
