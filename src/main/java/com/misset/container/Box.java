package com.misset.container;

import com.misset.FieldCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * 3x3 box
 */
public class Box extends Container {

    public Box(FieldCollection fields) {
        super(fields);
    }

    public List<BoxLineIntersect> createIntersects() {
        List<BoxLineIntersect> boxLineIntersects = new ArrayList<>();
        boxLineIntersects.add(new BoxLineIntersect(fields.getRow(fields.yMin())));
        boxLineIntersects.add(new BoxLineIntersect(fields.getRow(fields.yMin() + 1)));
        boxLineIntersects.add(new BoxLineIntersect(fields.getRow(fields.yMin() + 2)));
        boxLineIntersects.add(new BoxLineIntersect(fields.getColumn(fields.xMin())));
        boxLineIntersects.add(new BoxLineIntersect(fields.getColumn(fields.xMin() + 1)));
        boxLineIntersects.add(new BoxLineIntersect(fields.getColumn(fields.xMin() + 2)));
        return boxLineIntersects;
    }
}
