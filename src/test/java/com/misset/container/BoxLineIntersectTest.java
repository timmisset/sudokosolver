package com.misset.container;

import com.misset.FieldCollectionTest;
import com.misset.field.FieldCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoxLineIntersectTest extends FieldCollectionTest {



    @Test
    void isBoxSiblingTrueWhenRowInSameBox() {
        BoxLineIntersect siblingA = new BoxLineIntersect(fields.getGrid(1, 3, 1, 1));
        BoxLineIntersect siblingB = new BoxLineIntersect(fields.getGrid(1, 3, 2, 2));
        assertTrue(siblingA.isBoxSibling(siblingB));
        assertTrue(siblingB.isBoxSibling(siblingA));
    }

    @Test
    void isBoxSiblingFalseWhenRowInDifferentBox() {
        BoxLineIntersect siblingA = new BoxLineIntersect(fields.getGrid(1, 3, 1, 1));
        BoxLineIntersect siblingB = new BoxLineIntersect(fields.getGrid(1, 3, 4, 4));
        assertFalse(siblingA.isBoxSibling(siblingB));
        assertFalse(siblingB.isBoxSibling(siblingA));
    }

    @Test
    void isBoxSiblingTrueWhenColumnInSameBox() {
        BoxLineIntersect siblingA = new BoxLineIntersect(fields.getGrid(1, 1, 1, 3));
        BoxLineIntersect siblingB = new BoxLineIntersect(fields.getGrid(2, 2, 1, 3));
        assertTrue(siblingA.isBoxSibling(siblingB));
        assertTrue(siblingB.isBoxSibling(siblingA));
    }

    @Test
    void isBoxSiblingFalseWhenColumnInDifferentBox() {
        BoxLineIntersect siblingA = new BoxLineIntersect(fields.getGrid(1, 1, 1, 3));
        BoxLineIntersect siblingB = new BoxLineIntersect(fields.getGrid(4, 4, 1, 3));
        assertFalse(siblingA.isBoxSibling(siblingB));
        assertFalse(siblingB.isBoxSibling(siblingA));
    }

    @Test
    void isLineSiblingIsTrueWhenRowPartOnSameRow() {
        BoxLineIntersect siblingA = new BoxLineIntersect(fields.getGrid(1, 3, 1, 1));
        BoxLineIntersect siblingB = new BoxLineIntersect(fields.getGrid(4, 6, 1, 1));
        assertTrue(siblingA.isLineSibling(siblingB));
        assertTrue(siblingB.isLineSibling(siblingA));
    }

    @Test
    void isLineSiblingIsFalseWhenRowPartOnDifferentRow() {
        BoxLineIntersect siblingA = new BoxLineIntersect(fields.getGrid(1, 3, 1, 1));
        BoxLineIntersect siblingB = new BoxLineIntersect(fields.getGrid(4, 6, 2, 2));
        assertFalse(siblingA.isLineSibling(siblingB));
        assertFalse(siblingB.isLineSibling(siblingA));
    }

    @Test
    void isLineSiblingIsTrueWhenColumnPartOnSameColumn() {
        BoxLineIntersect siblingA = new BoxLineIntersect(fields.getGrid(1, 1, 1, 3));
        BoxLineIntersect siblingB = new BoxLineIntersect(fields.getGrid(1, 1, 4, 6));
        assertTrue(siblingA.isLineSibling(siblingB));
        assertTrue(siblingB.isLineSibling(siblingA));
    }

    @Test
    void isLineSiblingIsFalseWhenColumnPartOnDifferentColumn() {
        BoxLineIntersect siblingA = new BoxLineIntersect(fields.getGrid(1, 1, 1, 3));
        BoxLineIntersect siblingB = new BoxLineIntersect(fields.getGrid(2, 2, 4, 6));
        assertFalse(siblingA.isLineSibling(siblingB));
        assertFalse(siblingB.isLineSibling(siblingA));
    }

    @Test
    void excludeCallsExcludeOnAllFields() {
        FieldCollection column = fields.getGrid(1, 1, 1, 3);
        BoxLineIntersect boxLineIntersect = new BoxLineIntersect(column);
        boxLineIntersect.exclude(1);
        assertTrue(column.stream().noneMatch(field -> field.getValidOptions().contains(1)));
        assertTrue(column.stream().allMatch(field -> field.getValidOptions().contains(2)));
    }

    @Test
    void canContain() {
        FieldCollection column = fields.getGrid(1, 1, 1, 3);
        BoxLineIntersect boxLineIntersect = new BoxLineIntersect(column);
        boxLineIntersect.exclude(1);
        assertFalse(boxLineIntersect.canContain(1));
        assertTrue(boxLineIntersect.canContain(2));
    }

    @Test
    void mustContainReturnsTrueWhenOpenOptionsMatchesEmptyFields() {
        FieldCollection column = fields.getGrid(1, 1, 1, 3);
        BoxLineIntersect boxLineIntersect = new BoxLineIntersect(column);
        for(int i = 1; i <= 7; i++) {
            boxLineIntersect.exclude(i);
        }
        column.get(0).setValue(1);
        assertTrue(boxLineIntersect.mustContain(8));
        assertTrue(boxLineIntersect.mustContain(9));
    }




}
