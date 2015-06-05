package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for setting fields to values (used for INSERT and UPDATE queries)
 */
abstract class AbstractSetFieldBlock extends Block {

    class SetNode {
        String field;
        Object value;

        SetNode(String field, Object value) {
            this.field = field;
            this.value = value;
        }
    }

    protected List<SetNode> mFields = new ArrayList<>();

    AbstractSetFieldBlock(QueryBuilderOptions options) {
        super(options);
    }

    // Update the given field with the given value.
    <T> void set(String field, T value) {
        field = _sanitizeField(field);
        mFields.add(new SetNode(field, value));
    }
}
