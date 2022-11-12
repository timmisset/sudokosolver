package com.misset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Field {
    private final int xPos;
    private final int yPos;

    List<Integer> validOptions = new ArrayList<>();

    public Field(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        validOptions.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    public void setValue(Integer integer) {
        validOptions = List.of(integer);
    }

    public boolean isSolved() {
        return validOptions.size() == 1;
    }

    public Integer getValue() {
        if (isSolved()) {
            return validOptions.get(0);
        } else {
            throw new RuntimeException("Field is not yet solved");
        }
    }

    public List<Integer> getValidOptions() {
        return Collections.unmodifiableList(validOptions);
    }

    public void exclude(Integer integer) {
        if (!isSolved()) {
            validOptions.remove(integer);
        }
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    @Override
    public String toString() {
        return xPos + "." + yPos + " = " + getValidOptions();
    }
}
