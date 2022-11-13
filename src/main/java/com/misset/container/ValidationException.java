package com.misset.container;

import com.misset.field.FieldCollection;

public class ValidationException extends RuntimeException {

    public ValidationException(FieldCollection fields, int value, int presence) {
        super("Value " + value + " was present " + presence + " times in container " + fields);
    }

}
