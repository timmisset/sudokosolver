package com.misset.container;

import com.misset.field.FieldCollection;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GridTest {

    @Test
    void getContainersReturns9Boxes() {
        Grid grid = new Grid();
        Collection<Container> containers = grid.getContainers();
        assertEquals(9, containers.stream().filter(Box.class::isInstance).count());
    }

    @Test
    void getContainersReturns18Lines() {
        Grid grid = new Grid();
        Collection<Container> containers = grid.getContainers();
        assertEquals(18, containers.stream().filter(Line.class::isInstance).count());
    }

    @Test
    void getIntersectionsReturns54Intersections() {
        Grid grid = new Grid();
        Collection<BoxLineIntersect> intersections = grid.getIntersections();
        assertEquals(54, intersections.size());
    }

    @Test
    void getFieldsReturns81Fields() {
        Grid grid = new Grid();
        FieldCollection fields = grid.getFields();
        assertEquals(81, fields.size());
    }
}
