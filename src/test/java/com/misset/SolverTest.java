package com.misset;

import com.misset.field.Field;
import com.misset.field.FieldCollection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolverTest {

    @Test
    void testPuzzle4Stars() {
        String[] values = new String[]{
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
        String[] solution = new String[]{
                "6 7 3 9 1 8 2 4 5",
                "9 5 4 2 3 7 6 8 1",
                "2 8 1 4 5 6 7 3 9",
                "1 4 6 5 2 3 9 7 8",
                "5 3 7 8 9 1 4 6 2",
                "8 2 9 6 7 4 1 5 3",
                "3 6 5 1 4 2 8 9 7",
                "4 9 2 7 8 5 3 1 6",
                "7 1 8 3 6 9 5 2 4"
        };
        Solver solver = solve(values);
        assertTrue(solver.isSolved());
        assertArrayEquals(solution, result(solver));
    }

    @Test
    void testPuzzle5Stars() {
        String[] values = new String[]{
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
        String[] solution = new String[]{
                "2 4 5 3 8 9 7 6 1",
                "1 7 9 6 4 2 8 3 5",
                "6 8 3 1 5 7 4 2 9",
                "3 5 6 8 9 1 2 4 7",
                "4 1 7 2 6 3 5 9 8",
                "9 2 8 4 7 5 6 1 3",
                "5 9 1 7 2 4 3 8 6",
                "7 6 2 9 3 8 1 5 4",
                "8 3 4 5 1 6 9 7 2"
        };
        Solver solver = solve(values);
        assertTrue(solver.isSolved());
        assertArrayEquals(solution, result(solver));
    }

    @Test
    void testPuzzle5Stars2() {
        String[] values = new String[]{
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
        String[] solution = new String[]{
                "5 7 6 1 3 4 8 9 2",
                "4 8 9 2 6 5 1 7 3",
                "3 2 1 8 9 7 5 4 6",
                "7 1 5 3 8 2 4 6 9",
                "6 4 2 9 7 1 3 5 8",
                "8 9 3 4 5 6 7 2 1",
                "2 6 8 7 4 3 9 1 5",
                "1 3 7 5 2 9 6 8 4",
                "9 5 4 6 1 8 2 3 7"
        };
        Solver solver = solve(values);
        assertTrue(solver.isSolved());
        assertArrayEquals(solution, result(solver));
    }

    @Test
    void testPuzzle5Stars3() {
        String[] values = new String[]{
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
        String[] solution = new String[]{
                "1 2 3 4 9 7 8 6 5",
                "6 4 9 5 8 3 7 1 2",
                "5 7 8 1 2 6 9 3 4",
                "3 8 5 6 1 2 4 9 7",
                "4 1 2 9 7 8 6 5 3",
                "9 6 7 3 5 4 1 2 8",
                "7 9 1 8 3 5 2 4 6",
                "8 3 4 2 6 9 5 7 1",
                "2 5 6 7 4 1 3 8 9"
        };
        Solver solver = solve(values);
        assertTrue(solver.isSolved());
        assertArrayEquals(solution, result(solver));
    }

    private Solver solve(String[] startingValues) {
        Solver solver = new Solver();
        for (int y = 1; y <= 9; y++) {
            String rowValues = startingValues[y - 1];
            String[] values = rowValues.replace(" ", "").split("");
            for (int x = 1; x <= values.length; x++) {
                String valueAsString = values[x - 1];
                if (!valueAsString.equals("-")) {
                    int value = Integer.parseInt(valueAsString);
                    solver.setValue(x, y, value);
                }
            }
        }
        solver.trySolve();
        solver.validate();
        return solver;
    }

    private String[] result(Solver solver) {
        FieldCollection fields = solver.getFields();
        List<String> results = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            FieldCollection row = fields.getRow(i);
            String result = row.stream().map(Field::getValue)
                    .map(Object::toString)
                    .collect(Collectors.joining(" "));
            results.add(result);
        }
        return results.toArray(String[]::new);
    }

}
