package com.misset.field;

public class FieldIsNotSolvedException extends RuntimeException {

    public FieldIsNotSolvedException(Field field) {
        super("Field " + field + " is not yet solved");
    }
}
