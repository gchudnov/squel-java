package com.github.gchudnov.squel;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for setting fields to values (used for INSERT and UPDATE queries)
 */
abstract class SetFieldBlockBase extends Block {

    class SetNode {
        final String field;
        final Object value;

        SetNode(String field, Object value) {
            this.field = field;
            this.value = value;
        }
    }

    List<SetNode> mFields;

    SetFieldBlockBase(QueryBuilderOptions options) {
        super(options);
    }

    /**
     * Update the given field with the given value.
     * @param field Field to set value for.
     * @param value Value to set.
     * @param <T> Type of the Value.
     */
    <T> void setFieldValue(String field, T value) {
        if(mFields == null) {
            mFields = new ArrayList<>();
        }

        field = Validator.sanitizeField(field, mOptions);
        mFields.add(new SetNode(field, value));
    }
}
