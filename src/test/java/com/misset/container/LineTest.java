package com.misset.container;

import com.misset.FieldCollection;
import com.misset.FieldCollectionTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest extends FieldCollectionTest {

    @Test
    void testCanBeInstantiated() {
        FieldCollection rowFields = fields.getGrid(1, 9, 1, 1);
        Line line = new Line(rowFields);
        assertNotNull(line);
    }

}
