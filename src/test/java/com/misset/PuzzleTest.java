package com.misset;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PuzzleTest {

    @Test
    void testPuzzle4Stars() {
        String[] values = new String[] {
                "- - - - 1 - - - -",
                "- 5 4 2 3 - 6 - -",
                "- 8 - 4 - - 7 - -",
                "- 4 6 - - - - - -",
                "- 3 - - 9 - 4 - -",
                "8 - 9 - - - 1 - -",
                "- - 5 - - 2 - 9 7",
                "- - - - - 5 3 - -",
                "7 - - - - 9 - 2 -"
        };
        Grid grid = solve(values);
        assertTrue(grid.isSolved());
    }

    @Test
    void testPuzzle5Stars() {
        String[] values = new String[] {
                "- - - - 8 - 7 - 1",
                "- - - - - - - 3 -",
                "- - - 1 - - - 2 -",
                "3 5 - 8 9 - - - -",
                "- - - - - - 5 - 8",
                "9 2 - - 7 - - - -",
                "5 9 - - 2 - - - 6",
                "- - - - - - 1 - -",
                "- 3 - - - 6 - 7 2"
        };
        Grid grid = solve(values);
        assertTrue(grid.isSolved());
    }

    @Test
    void testPuzzle5Stars2() {
        String[] values = new String[] {
                "- - 6 1 - 4 8 9 -",
                "4 - - - - 5 - 7 -",
                "- - 1 8 - - - - -",
                "- - - - - - 4 - -",
                "- - 2 - 7 - - - -",
                "8 9 - - 5 6 - - -",
                "- - - - - - - - 5",
                "- 3 7 - 2 - - 8 -",
                "- - - - - 8 2 - -"
        };
        Grid grid = solve(values);
        assertTrue(grid.isSolved());
        System.out.println(grid.getResults());
    }

    @Test
    void testPuzzle5Stars3() {
        String[] values = new String[] {
                "1 - - 4 - - - 6 -",
                "- 4 - - - 3 - - -",
                "- - 8 - - - 9 - -",
                "3 - - - 1 - - - 7",
                "- - - 9 - 8 6 - -",
                "9 - 7 - 5 - - - -",
                "- - - - - 5 2 - -",
                "8 - - - 6 - 5 - -",
                "2 - - - 4 - - - 9"
        };
        Grid grid = solve(values);
        assertTrue(grid.isSolved());
        System.out.println(grid.getResults());
    }

    private Grid solve(String[] startingValues) {
        Grid grid = new Grid();
        for(int y = 1; y <= 9; y++) {
            String rowValues = startingValues[y-1];
            String[] values = rowValues.replace(" ", "").split("");
            for(int x = 1; x <= values.length; x++) {
                String valueAsString = values[x-1];
                if(!valueAsString.equals("-")) {
                    int value = Integer.parseInt(valueAsString);
                    grid.setValue(x, y, value);
                }
            }
        }
        grid.trySolve();
        grid.validate();
        return grid;
    }

}
