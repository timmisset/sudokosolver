package com.misset;

import com.misset.container.Box;
import com.misset.container.BoxLineIntersect;
import com.misset.container.Container;
import com.misset.container.Line;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public void setValue(int col, int row, int value) {
        setValue(getField(col, row), value);
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

    public String getResults() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 1; row <= 9; row++) {
            for (int col = 1; col <= 9; col++) {
                Field field = getField(col, row);
                stringBuilder.append(field.isSolved() ? field.getValue() : ".");
                if (col % 3 == 0) stringBuilder.append("|");
            }
            stringBuilder.append(System.lineSeparator());
            if (row % 3 == 0) stringBuilder.append(" -----------").append(System.lineSeparator());
        }
        stringBuilder.append(System.lineSeparator());
        return stringBuilder.toString();
    }

    private Field getField(int col, int row) {
        return fields.stream().filter(field -> field.getxPos() == col && field.getyPos() == row).findFirst().orElse(null);
    }

    public void setValue(Field field, int value) {
        field.setValue(value);
        containers.forEach(container -> excludeOtherFields(container, field, value));
    }

    public List<Box> getBoxes() {
        return containers.stream().filter(Box.class::isInstance)
                .map(Box.class::cast)
                .collect(Collectors.toList());
    }

    public void trySolve() {
        int numberOfOptions = numberOfOptions();
        boolean tryToSolve = true;
        while (tryToSolve) {
            containers.forEach(this::solveContainer);
            int numberSolvedAfterTry = numberOfOptions();
            if (isSolved()) {
                tryToSolve = false;
            } else if(numberSolvedAfterTry == numberOfOptions) {
                solveByPartialExclusion();
                tryToSolve = numberOfOptions() != numberOfOptions;
            }
            numberOfOptions = numberSolvedAfterTry;
        }
    }

    private void solveContainer(Container container) {
        solveIfOnlyOneFieldIsApplicableForValue(container);
        excludeFromFieldIfSolvedBySibling(container);
    }

    private void solveIfOnlyOneFieldIsApplicableForValue(Container container) {
        for (int i = 1; i <= 9; i++) {
            int finalI = i;
            if (!container.hasSolved(finalI)) {
                List<Field> list = container.getFields().stream().filter(field -> field.validOptions.contains(finalI))
                        .collect(Collectors.toList());
                if (list.size() == 1) {
                    setValue(list.get(0), finalI);
                }
            }
        }
    }

    private void excludeFromFieldIfSolvedBySibling(Container container) {
        for (Field field : container.getFields()) {
            if (!field.isSolved()) {
                Collection<Integer> collect = field.getValidOptions().stream().filter(container::hasSolved).collect(Collectors.toList());
                collect.forEach(field::exclude);
            }
        }
    }

    private void excludeOtherFields(Container container, Field solvedField, Integer solution) {
        if(container.getFields().contains(solvedField)) {
            container.getFields().stream().filter(field -> field != solvedField && !field.isSolved()).forEach(field -> {
                field.exclude(solution);
                if(field.isSolved()) {
                    excludeOtherFields(container, field, field.getValue());
                }
            });
        }
    }

    public boolean isSolved() {
        return fields.stream().allMatch(Field::isSolved);
    }

    private int numberOfOptions() {
        return fields.stream().map(field -> field.getValidOptions().size())
                .reduce(Integer::sum)
                .orElse(0);
    }

    /**
     * If a box is certain that a value must appear at a certain column/row part we can assert that the remainder of that
     * column/row that intersects other boxes cannot carry that value
     */
    private void solveByPartialExclusion() {
        for (BoxLineIntersect boxLineIntersect : boxLineIntersects) {
            for (int i = 1; i <= 9; i++) {
                if (boxlineCanAndSiblingCannotContain(boxLineIntersect, sibling -> sibling.isBoxSibling(boxLineIntersect), i)) {
                    excludeFromSibling(sibling -> sibling.isLineSibling(boxLineIntersect), i);
                }

                if (boxlineCanAndSiblingCannotContain(boxLineIntersect, sibling -> sibling.isLineSibling(boxLineIntersect), i)) {
                    excludeFromSibling(sibling -> sibling.isBoxSibling(boxLineIntersect), i);
                }
            }
        }
    }

    private boolean boxlineCanAndSiblingCannotContain(BoxLineIntersect boxLineIntersect, Predicate<BoxLineIntersect> isSibling, int i) {
        return boxLineIntersect.mustContain(i) || boxLineIntersect.canContain(i) && siblingCannotContain(isSibling, i);
    }

    private void excludeFromSibling(Predicate<BoxLineIntersect> isSibling, int value) {
        boxLineIntersects.stream().filter(isSibling).forEach(sibling -> {
            sibling.exclude(value);
            containers.forEach(this::solveContainer);
        });
    }

    private boolean siblingCannotContain(Predicate<BoxLineIntersect> isSibling, int value) {
        return boxLineIntersects.stream().noneMatch(sibling -> isSibling.test(sibling) && sibling.canContain(value));
    }

    public void validate() {
        getBoxes().forEach(Container::validate);
    }
}
