package com.github.gchudnov.squel;

import java.util.List;

/**
 * UPDATE query builder.
 */
public class Update extends QueryBuilder {
    Update(QueryBuilderOptions options, List<Block> blocks) {
        super(options, blocks);
    }
}
