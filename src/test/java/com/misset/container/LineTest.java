package com.misset.container;

import com.misset.FieldCollectionTest;
import com.misset.field.FieldCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LineTest extends FieldCollectionTest {

    @Test
    void testCanBeInstantiated() {
        FieldCollection rowFields = fields.getGrid(1, 9, 1, 1);
        Line line = new Line(rowFields);
        assertNotNull(line);
    }

}
