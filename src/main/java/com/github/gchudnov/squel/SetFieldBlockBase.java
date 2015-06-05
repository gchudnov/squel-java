package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for setting fields to values (used for INSERT and UPDATE queries)
 */
abstract class SetFieldBlockBase extends Block {

    class SetNode {
        String field;
        Object value;

        SetNode(String field, Object value) {
            this.field = field;
            this.value = value;
        }
    }

    protected List<SetNode> mFields = new ArrayList<>();

    SetFieldBlockBase(QueryBuilderOptions options) {
        super(options);
    }

    // Update the given setField with the given value.
    <T> void set(String field, T value) {
        field = sanitizeField(field);
        mFields.add(new SetNode(field, value));
    }
}
