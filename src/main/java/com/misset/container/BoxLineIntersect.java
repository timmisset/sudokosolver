package com.misset.container;

import com.misset.field.Field;
import com.misset.field.FieldCollection;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 1x3 container of fields
 */
public class BoxLineIntersect {

    private final FieldCollection fields;

    public BoxLineIntersect(FieldCollection fields) {
        this.fields = fields;
    }

    public boolean isBoxSibling(BoxLineIntersect sibling) {
        if(this == sibling) { return false; }
        if(isColumn() && sibling.isColumn() && fields.yMin() == sibling.fields.yMin() && Math.ceil(fields.xMin() / 3f) == Math.ceil(sibling.fields.xMin() / 3f)) {
            return true;
        } else return isRow() && sibling.isRow() && fields.xMin() == sibling.fields.xMin() && Math.ceil(fields.yMin() / 3f) == Math.ceil(sibling.fields.yMin() / 3f);
    }

    public boolean isLineSibling(BoxLineIntersect sibling) {
        if(this == sibling) { return false; }
        if(isColumn() && sibling.isColumn() && fields.xMin() == sibling.fields.xMin()) {
            return true;
        } else return isRow() && sibling.isRow() && fields.yMin() == sibling.fields.yMin();
    }

    public void exclude(int i) {
        fields.forEach(field -> field.exclude(i));
    }

    private boolean isRow() {
        return fields.stream().allMatch(field -> field.getyPos() == fields.iterator().next().getyPos());
    }

    private boolean isColumn() {
        return !isRow();
    }

    public boolean canContain(int i) {
        return fields.stream().anyMatch(field -> field.getValidOptions().contains(i));
    }

    /**
     * Checks if the value must be contained in this BoxLineIntersect by the condition that
     * when:
     *   a subset of 3 fields has 2 open fields
     *        1   2   3
     *   1. [ 7 | - | - ] <-- this instance
     *   2. [ - | - | - ] <-- sibling BoxLineIntersect
     *   3. [ - | - | - ] <-- sibling BoxLineIntersect
     * and:
     *   both open fields are limited to 2 optional values where one is the provided value
     *        1   2   3
     *   1. [ 7           | 4 or 5 | 4 or 5 ] <-- this instance
     *   2. [ 4 or 5 or 8 | -      | -      ] <-- sibling BoxLineIntersect
     * then:
     *   the provided value can only be part of this BoxLineIntersect
     *        1   2   3
     *   1. [ 7 | 4 or 5 | 4 or 5 ] <-- this instance (must contain 4 or 5)
     *   2. [ 8 | -      | -      ] <-- sibling BoxLineIntersect (cannot contain 4 or 5)
     */
    public boolean mustContain(int i) {
        Collection<Field> unsolvedFields = fields.stream().filter(field -> !field.isSolved()).collect(Collectors.toList());
        return canContain(i) && unsolvedFields.stream().allMatch(field -> field.getValidOptions().size() == unsolvedFields.size());
    }
}
