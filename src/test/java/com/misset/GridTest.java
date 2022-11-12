package com.misset;

import com.misset.container.Box;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    @Test
    void testGetBoxesReturn9Boxes() {
        Grid grid = new Grid();
        Collection<Box> boxes = grid.getBoxes();
        assertEquals(9, boxes.size());
    }

    @Test
    void testBoxesContainAllDistinctFields() {
        Grid grid = new Grid();
        Collection<Box> boxes = grid.getBoxes();
        for(Box box : boxes) {
            for(Field field : box.getFields()) {
                for(Box otherBox : boxes) {
                    assertEquals(otherBox == box, otherBox.contains(field));
                }
            }
        }
    }
}
