package com.misset.container;

import com.misset.field.Field;
import com.misset.field.FieldCollection;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Container {

    protected final FieldCollection fields;

    protected Container(FieldCollection fields) {
        this.fields = fields;
    }

    public Collection<Field> getFields() {
        return Collections.unmodifiableCollection(fields);
    }

    public boolean contains(Field field) {
        return fields.contains(field);
    }

    public boolean hasSolved(int value) {
        return fields.stream().anyMatch(field -> field.isSolved() && field.getValue() == value);
    }

    public void validate() {
        for(int i = 1 ; i <= 9 ; i++) {
            Collection<Field> fieldsWithValue = getFieldsWithValue(i);
            if(fieldsWithValue.size() != 1) {
                throw new ValidationException(fields, i, fieldsWithValue.size());
            }
        }
    }


    private List<Field> getFieldsWithValue(int value) {
        return fields.stream().filter(field -> field.isSolved() && field.getValue() == value).collect(Collectors.toList());
    }
}
