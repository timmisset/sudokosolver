package com.misset.field;

import com.misset.FieldCollectionTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest extends FieldCollectionTest {

    @Test
    void setGetValue() {
        Field field = fields.getRow(1).getColumn(1).get(0);
        field.setValue(1);
        assertEquals(1, field.getValue());
    }

    @Test
    void getValueThrowsWhenNotSet() {
        Field field = fields.getRow(1).getColumn(1).get(0);
        assertThrows(RuntimeException.class, field::getValue);
    }

    @Test
    void isSolved() {
        Field field = fields.getRow(1).getColumn(1).get(0);
        assertFalse(field.isSolved());
        field.setValue(1);
        assertTrue(field.isSolved());
    }

    @Test
    void excludeAndGetValidOptions() {
        Field field = fields.getRow(1).getColumn(1).get(0);
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), field.getValidOptions());
        field.exclude(1);
        assertEquals(List.of(2, 3, 4, 5, 6, 7, 8, 9), field.getValidOptions());
    }

    @Test
    void getPositions() {
        Field field = fields.getRow(4).getColumn(7).get(0);
        assertEquals(4, field.getyPos());
        assertEquals(7, field.getxPos());
    }

    @Test
    void toStringContainsPositionInformation() {
        Field field = fields.getRow(4).getColumn(7).get(0);
        assertEquals("7.4 = [1, 2, 3, 4, 5, 6, 7, 8, 9]", field.toString());
    }
}
