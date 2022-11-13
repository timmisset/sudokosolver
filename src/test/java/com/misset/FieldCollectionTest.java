package com.misset;

import com.misset.field.Field;
import com.misset.field.FieldCollection;

public abstract class FieldCollectionTest {

    protected FieldCollection fields = getFields();

    private FieldCollection getFields() {
        FieldCollection fields = new FieldCollection();
        for (int x = 1; x <= 9; x++) {
            for (int y = 1; y <= 9; y++) {
                fields.add(new Field(x, y));
            }
        }
        return fields;
    }

}
