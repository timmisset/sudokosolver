package com.misset.container;

import com.misset.field.Field;
import com.misset.field.FieldCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * The entire 9x9 grid
 */
public class Grid {
    private final FieldCollection fields = new FieldCollection();
    private final List<Container> containers = new ArrayList<>();
    private final List<BoxLineIntersect> boxLineIntersects = new ArrayList<>();

    public Grid() {
        createFields();
        createBoxes();
        createLines();
    }

    private void createFields() {
        for (int x = 1; x <= 9; x++) {
            for (int y = 1; y <= 9; y++) {
                fields.add(new Field(x, y));
            }
        }
    }

    private void createBoxes() {
        /*
            Box 1 = Fields [[1,1],[1,2],[1,3],[2,1],[2,2],[2,3],[3,1],[3,2],[3,3]]
            Box 2 = Fields [[4,1],[4,2],[4,3],[5,1],[5,2],[5,3],[6,1],[6,2],[6,3]]
            Box 3 = Fields [[7,1],[7,2],[7,3],[8,1],[8,2],[8,3],[9,1],[9,2],[9,3]]
            Box 4 = Fields [[1,4],[1,5],[1,6],[2,4],[2,5],[2,6],[3,4],[3,5],[3,6]]
            Box 5 = Fields [[4,4],[4,5],[4,6],[5,4],[5,5],[5,6],[6,4],[6,5],[6,6]]
            Box 6 = Fields [[7,4],[7,5],[7,6],[8,4],[8,5],[8,6],[9,4],[9,5],[9,6]]
            Box 7 = Fields [[1,7],[1,8],[1,9],[2,7],[2,8],[2,9],[3,7],[3,8],[3,9]]
            Box 8 = Fields [[4,7],[4,8],[4,9],[5,7],[5,8],[5,9],[6,7],[6,8],[6,9]]
            Box 9 = Fields [[7,7],[7,8],[7,9],[8,7],[8,8],[8,9],[9,7],[9,8],[9,9]]
         */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int xPosMin = i * 3 + 1;
                int xPosMax = xPosMin + 2;
                int yPosMin = j * 3 + 1;
                int yPosMax = yPosMin + 2;
                Box box = new Box(fields.getGrid(xPosMin, xPosMax, yPosMin, yPosMax));
                containers.add(box);
                boxLineIntersects.addAll(box.createIntersects());
            }
        }
    }

    private void createLines() {
        for (int i = 1; i <= 9; i++) {
            containers.add(new Line(fields.getGrid(1, 9, i, i)));
            containers.add(new Line(fields.getGrid(i, i, 1, 9)));
        }
    }

    public List<Container> getContainers() {
        return containers;
    }

    public List<BoxLineIntersect> getIntersections() {
        return boxLineIntersects;
    }

    public FieldCollection getFields() {
        return fields;
    }

}
