package com.github.gchudnov.squel;

/**
 * A building block represents a single build-step within a query building process.
 * <p>
 * Query builders consist of one or more building blocks which get run in a particular order. Building blocks can
 * optionally specify methods to expose through the query builder interface. They can access all the input data for
 * the query builder and manipulate it as necessary, as well as append to the final query string output.
 * <p>
 * If you wish to customize how queries get built or add proprietary query phrases and content then it is recommended
 * that you do so using one or more custom building blocks.
 * <p>
 * Original idea posted in https://github.com/hiddentao/export/issues/10//issuecomment-15016427
 */
public class Block extends BaseBuilder {

    public Block(QueryBuilderOptions options) {
        super(options);
    }

    // Build this block.
    //
    // Subclasses may override this method.
    //
    // @param queryBuilder cls.com.github.gchudnov.squel.QueryBuilder a reference to the query builder that owns this block.
    //
    // @return String the string representing this block
    public String buildStr(QueryBuilder queryBuilder) {
        return "";
    }
}
