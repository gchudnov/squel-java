package com.github.gchudnov.squel;

import java.util.List;

/**
 * DELETE query builder.
 */
public class Delete extends QueryBuilder {
    Delete(QueryBuilderOptions options, List<Block> blocks) {
        super(options, blocks);
    }
}
