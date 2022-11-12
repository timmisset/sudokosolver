package com.misset;

import org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class FieldCollection extends ArrayList<Field> {

    public FieldCollection(Collection<Field> collect) {
        super(collect);
    }
    public FieldCollection() { }

    public FieldCollection getGrid(int xMin, int xMax, int yMin, int yMax) {
        Range<Integer> xRange = Range.between(xMin, xMax);
        Range<Integer> yRange = Range.between(yMin, yMax);
        return new FieldCollection(this.stream()
                .filter(field -> xRange.contains(field.getxPos()) &&
                        yRange.contains(field.getyPos()))
                .collect(Collectors.toList()));
    }

    public FieldCollection getRow(int row) {
        return getGrid(xMin(), xMax(), row, row);
    }
    public FieldCollection getColumn(int col) {
        return getGrid(col, col, yMin(), yMax());
    }

    public int xMin() {
        return this.stream().map(Field::getxPos).min(Integer::compare).orElse(-1);
    }

    public int xMax() {
        return this.stream().map(Field::getxPos).max(Integer::compare).orElse(-1);
    }

    public int yMin() {
        return this.stream().map(Field::getyPos).min(Integer::compare).orElse(-1);
    }

    public int yMax() {
        return this.stream().map(Field::getyPos).max(Integer::compare).orElse(-1);
    }

    @Override
    public String toString() {
        return xMin() + "." + yMin() + "-" + xMax() + "." + yMax();
    }

}
