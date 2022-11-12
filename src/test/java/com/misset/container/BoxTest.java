package com.misset.container;

import com.misset.FieldCollection;
import com.misset.FieldCollectionTest;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest extends FieldCollectionTest {

    @Test
    void createIntersectsReturns6intersect() {
        FieldCollection boxFields = fields.getGrid(1, 1, 3, 3);
        Box box = new Box(boxFields);
        Collection<BoxLineIntersect> intersects = box.createIntersects();

        assertEquals(6, intersects.size());
    }
}
