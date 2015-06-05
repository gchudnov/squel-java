package com.github.gchudnov.squel;

import com.github.gchudnov.squel.impl.Block;

import java.util.List;

/**
 * UPDATE query builder.
 */
public class Update extends QueryBuilder {
    Update(QueryBuilderOptions options, List<Block> blocks) {
        super(options, blocks);
    }
}
