package com.github.gchudnov.squel;

import java.util.List;

/**
 * An INSERT query builder.
 */
public class Insert extends QueryBuilder {
    Insert(QueryBuilderOptions options, List<Block> blocks) {
        super(options, blocks);
    }
}
