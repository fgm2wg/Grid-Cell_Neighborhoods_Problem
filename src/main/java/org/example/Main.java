package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    /**
     * Main function to run the example scenarios and visualize the results.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // --- Run Example 1: One positive cell fully contained; N=3 ---
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(5, 5)); // (row, col), 1-indexed
        ArrayList<ArrayList<Integer>> grid = generateGrid(11, 11, pos_val_coords);
        int n = 3;

        GridCellNeighborhoods solver = new GridCellNeighborhoods(grid, n);
        int result = solver.numCellsWithinNSteps();
        System.out.println("Result: " + result);
        ArrayList<ArrayList<Boolean>> visited_grid = solver.getVisitedGrid();
        printVisitedGrid(grid, visited_grid);
        System.out.println();

        // --- Run Example 2: One positive cell near an edge; N=3 ---
        pos_val_coords = List.of(Arrays.asList(5, 1)); // (row, col), 1-indexed
        grid = generateGrid(11, 11, pos_val_coords);

        solver = new GridCellNeighborhoods(grid, n);
        result = solver.numCellsWithinNSteps();
        System.out.println("Result: " + result);
        visited_grid = solver.getVisitedGrid();
        printVisitedGrid(grid, visited_grid);
        System.out.println();

        // --- Run Example 3: Two positive values with disjoint neighborhoods; N=2 ---
        pos_val_coords = List.of(Arrays.asList(3, 7), Arrays.asList(7, 3)); // (row, col), 1-indexed
        grid = generateGrid(11, 11, pos_val_coords);
        n = 2;

        solver = new GridCellNeighborhoods(grid, n);
        result = solver.numCellsWithinNSteps();
        System.out.println("Result: " + result);
        visited_grid = solver.getVisitedGrid();
        printVisitedGrid(grid, visited_grid);
        System.out.println();

        // --- Run Example 4: Two positive values with overlapping neighborhoods; N=2 ---
        pos_val_coords = List.of(Arrays.asList(7, 3), Arrays.asList(6, 5)); // (row, col), 1-indexed
        grid = generateGrid(11, 11, pos_val_coords);

        solver = new GridCellNeighborhoods(grid, n);
        result = solver.numCellsWithinNSteps();
        System.out.println("Result: " + result);
        visited_grid = solver.getVisitedGrid();
        printVisitedGrid(grid, visited_grid);
        System.out.println();
    }

    /**
     * Function to generate a random grid with n rows x m columns of values ranging from -10 to 10.
     *
     * @param numRows The number of rows to make the grid have.
     * @param numCols The number of columns to make the grid have.
     * @return n x m grid of random integers between -10 and 10.
     */
    public static ArrayList<ArrayList<Integer>> generateRandomGrid(int numRows, int numCols) {
        // Initialize empty grid and random number generator
        ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
        Random random = new Random();

        // Iterate over all rows and columns adding a random number between -10 and 10 to the grid
        for (int i = 0; i < numRows; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < numCols; j++)
                row.add(random.nextInt(-10, 10));
            grid.add(row);
        }

        // Return the constructed grid
        return grid;
    }

    /**
     * Function to generate an n x m grid of negative values except for the coordinates specified to be positive.
     *
     * @param numRows        The number of rows to make the grid have.
     * @param numCols        The number of columns to make the grid have.
     * @param pos_val_coords The list of row, column 1-indexed coordinates to place positive numbers.
     * @return An n x m grid of values.
     */
    public static ArrayList<ArrayList<Integer>> generateGrid(int numRows, int numCols, List<List<Integer>> pos_val_coords) {
        // Initialize empty grid
        ArrayList<ArrayList<Integer>> grid = new ArrayList<>();

        // Iterate over all rows and columns adding -1 to all cells
        for (int i = 0; i < numRows; i++) {
            ArrayList<Integer> row = new ArrayList<>();

            for (int j = 0; j < numCols; j++)
                row.add(-1);

            grid.add(row);
        }

        // Iterate over the positive value coordinates given and set those cells to have the value 1
        for (List<Integer> coord : pos_val_coords) {
            int i = coord.get(0), j = coord.get(1);
            grid.get(i).set(j, 1);
        }

        // Return the constructed grid
        return grid;
    }

    /**
     * Print out the visualized graph with positive numbers being green and all visited neighbors as blue.
     *
     * @param grid         The n x m grid to visualize.
     * @param visited_grid The accompanying n x m grid with cells visited information.
     */
    public static void printVisitedGrid(ArrayList<ArrayList<Integer>> grid, ArrayList<ArrayList<Boolean>> visited_grid) {
        // ASCII colors for visuals
        final String RESET = "\u001B[0m";
        final String GREEN = "\u001B[32m";
        final String BLUE = "\u001B[34m";

        // Iterate over entire grid
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.getFirst().size(); j++) {
                String cell;
                // If the number is positive, mark as green ascii box
                if (grid.get(i).get(j) > 0)
                    cell = GREEN + "█" + RESET;
                // If the number is visited, mark as blue ascii box
                else if (visited_grid.get(i).get(j))
                    cell = BLUE + "█" + RESET;
                // Otherwise, mark as dotted box for contrast
                else
                    cell = "░";
                // Print cell with 2 gap spaces to look better
                System.out.print(cell + "  ");
            }
            // Print newline for next row
            System.out.println();
        }
    }
}
