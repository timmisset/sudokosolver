package com.misset;

import com.misset.container.BoxLineIntersect;
import com.misset.container.Container;
import com.misset.container.Grid;
import com.misset.field.Field;
import com.misset.field.FieldCollection;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Solver {

    private final Collection<Container> containers;
    private final Collection<BoxLineIntersect> boxLineIntersects;
    private final FieldCollection fields;

    public Solver() {
        Grid grid = new Grid();
        containers = grid.getContainers();
        boxLineIntersects = grid.getIntersections();
        fields = grid.getFields();
    }

    public void trySolve() {
        int numberOfOptions = numberOfOptions();
        boolean tryToSolve = true;
        while (tryToSolve) {
            containers.forEach(this::solveContainer);
            int numberSolvedAfterTry = numberOfOptions();
            if (isSolved()) {
                tryToSolve = false;
            } else if (numberSolvedAfterTry == numberOfOptions) {
                solveByPartialExclusion();
                tryToSolve = numberOfOptions() != numberOfOptions;
            }
            numberOfOptions = numberSolvedAfterTry;
        }
    }

    private int numberOfOptions() {
        return fields.stream().map(field -> field.getValidOptions().size())
                .reduce(Integer::sum)
                .orElse(0);
    }

    private void solveContainer(Container container) {
        solveIfOnlyOneFieldIsApplicableForValue(container);
        excludeFromFieldIfSolvedBySibling(container);
    }

    private void solveIfOnlyOneFieldIsApplicableForValue(Container container) {
        for (int i = 1; i <= 9; i++) {
            int finalI = i;
            if (!container.hasSolved(finalI)) {
                List<Field> list = container.getFields().stream().filter(field -> field.getValidOptions().contains(finalI))
                        .collect(Collectors.toList());
                if (list.size() == 1) {
                    setValue(list.get(0), finalI);
                }
            }
        }
    }

    private void excludeOtherFields(Container container, Field solvedField, Integer solution) {
        if (container.getFields().contains(solvedField)) {
            container.getFields().stream().filter(field -> field != solvedField && !field.isSolved()).forEach(field -> {
                field.exclude(solution);
                if (field.isSolved()) {
                    excludeOtherFields(container, field, field.getValue());
                }
            });
        }
    }

    public boolean isSolved() {
        return fields.stream().allMatch(Field::isSolved);
    }

    private void excludeFromFieldIfSolvedBySibling(Container container) {
        for (Field field : container.getFields()) {
            if (!field.isSolved()) {
                Collection<Integer> collect = field.getValidOptions().stream().filter(container::hasSolved).collect(Collectors.toList());
                collect.forEach(field::exclude);
            }
        }
    }

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

    private boolean boxlineCanAndSiblingCannotContain(BoxLineIntersect boxLineIntersect,
                                                      Predicate<BoxLineIntersect> isSibling,
                                                      int i) {
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
        containers.forEach(Container::validate);
    }

    public void setValue(int col, int row, int value) {
        Field field = getField(col, row);
        if (field != null) {
            setValue(field, value);
        }
    }

    private void setValue(Field field, int value) {
        field.setValue(value);
        containers.forEach(container -> excludeOtherFields(container, field, value));
    }

    private Field getField(int col, int row) {
        return fields.stream().filter(field -> field.getxPos() == col && field.getyPos() == row).findFirst().orElse(null);
    }

    public String getResults() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 1; row <= 9; row++) {
            for (int col = 1; col <= 9; col++) {
                Field field = getField(col, row);
                stringBuilder.append(field != null && field.isSolved() ? field.getValue() : ".");
                if (col % 3 == 0) stringBuilder.append("|");
            }
            stringBuilder.append(System.lineSeparator());
            if (row % 3 == 0) stringBuilder.append(" -----------").append(System.lineSeparator());
        }
        stringBuilder.append(System.lineSeparator());
        return stringBuilder.toString();
    }

    public FieldCollection getFields() {
        return fields;
    }

}
