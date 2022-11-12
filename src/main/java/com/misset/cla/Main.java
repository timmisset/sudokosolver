package com.misset.cla;

import com.misset.Grid;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Sudoku Solver. Enter a grid position and value: x, y, number. For example 1, 2, 3 enters " +
                "value 3 for column 1 and row 2. At any time, enter q to quit.");
        Scanner scanner = new Scanner(System.in);
        Grid grid = new Grid();
        String userInput = scanner.nextLine();
        while(!userInput.equals("q")) {
            String[] split = userInput.split(", ");
            if(split.length == 3) {
                int x = getInteger(split[0], "x");
                int y = getInteger(split[1], "y");
                int value = getInteger(split[2], "value");
                if(x > -1 && y > -1 && value > -1) {
                    grid.setValue(x, y, value);
                    grid.getResults();
                }
            }
            userInput = scanner.nextLine();
        }
    }


    private static int getInteger(String input, String description) {
        try{
            int i = Integer.parseInt(input);
            if(i < 0 || i > 9) {
                throw new NumberRangeException();
            }
            return i;
        }
        catch (NumberFormatException | NumberRangeException e) {
            System.out.println("Oops, expected an integer between 1 and 9 for " + description + " but got '" + input + "'");
            return -1;
        }
    }
}
