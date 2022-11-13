package com.misset.cla;

import com.misset.Solver;

import java.io.Console;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    private static final Console CONSOLE = System.console();

    public static void main(String[] args) {

        write("Welcome to the Sudoku Solver. Enter a grid position and value: x, y, number. For example 1, 2, 3 enters " +
                "value 3 for column 1 and row 2. At any time, enter q to quit.");
        Scanner scanner = new Scanner(System.in);
        Solver solver = new Solver();
        String userInput = scanner.nextLine();
        while (!userInput.equals("q")) {
            String[] split = userInput.split(", ");
            if (split.length == 3) {
                int x = getInteger(split[0], "x");
                int y = getInteger(split[1], "y");
                int value = getInteger(split[2], "value");
                if (x > -1 && y > -1 && value > -1) {
                    solver.setValue(x, y, value);
                    solver.getResults();
                }
            }
            userInput = scanner.nextLine();
        }
    }

    private static int getInteger(String input, String description) {
        try {
            int i = Integer.parseInt(input);
            if (i < 0 || i > 9) {
                throw new NumberRangeException();
            }
            return i;
        } catch (NumberFormatException | NumberRangeException e) {
            write("Oops, expected an integer between 1 and 9 for " + description + " but got '" + input + "'");
            return -1;
        }
    }

    private static void write(String message) {
        try (PrintWriter writer = CONSOLE.writer()) {
            writer.println(message);
        }
    }
}
