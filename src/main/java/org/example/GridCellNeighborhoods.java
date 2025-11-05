/**
 * NOTE: I did use these resources as reference to help get an idea of how best to solve the problem and implement BFS algorithm.
 * I hope that it is okay that I used outside resources since I would normally use this approach when solving a new problem.
 * Sources:
 * https://www.geeksforgeeks.org/dsa/breadth-first-traversal-bfs-on-a-2d-array/
 * - Found how to best implement the BFS algorithm in Java.
 * https://stackoverflow.com/questions/77274736/how-to-find-neighbors-in-a-grid
 * - Found how to best find adjacent neighbors in grid efficiently.
 */

package org.example;

import java.util.*;

public class GridCellNeighborhoods {
    // --- Private class fields ---
    private final ArrayList<ArrayList<Integer>> grid;
    private ArrayList<ArrayList<Boolean>> visited_grid;
    private final int n, numRows, numCols;

    // --- Public class methods ---
    /**
     * Constructor for the class to setup the grid, N, and grid dimensions.
     *
     * @param grid The n x m grid of integers.
     * @param n    The number of steps to count from each positive number in the grid.
     */
    public GridCellNeighborhoods(ArrayList<ArrayList<Integer>> grid, int n) {
        this.grid = grid;
        this.n = n;

        // Get the number of rows and columns in the grid
        this.numRows = grid.size();
        this.numCols = grid.getFirst().size();

        // Generate a matching size grid for the nodes visited all initialized to false
        generateVisitedGrid();
    }

    /**
     * Function to count the number of cells within N steps of positive numbers via a BFS approach.
     * Should probably separate out pieces like the BFS portion to separate functions, but kept like this for simplicity.
     *
     * @return The total number of cells within N steps of positive numbers in the grid.
     */
    public int numCellsWithinNSteps() {
        // Declare a queue to hold the values to visit in the grid for BFS
        Queue<ArrayList<Integer>> queue = new LinkedList<>();

        // Iterate over the entire grid
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                // If positive number found, then add its location and distance initialized to 0 to the queue and mark node as visited
                if (grid.get(i).get(j) > 0) {
                    queue.add(new ArrayList<>(Arrays.asList(i, j, 0)));
                    visited_grid.get(i).set(j, true);
                }
            }
        }

        // Create the direction offsets we need to find 4 adjacent neighboring cells
        List<List<Integer>> directions = List.of(Arrays.asList(-1, 0), Arrays.asList(0, -1), Arrays.asList(1, 0), Arrays.asList(0, 1));

        // Perform BFS for the positive values in the queue
        while (!queue.isEmpty()) {
            // Dequeue the list of values for the positive integer and split into coordinates and distance
            ArrayList<Integer> values = queue.poll();
            int i = values.get(0), j = values.get(1), dist = values.get(2);

            // If the distance is equal to number of steps we can take, then skip it
            if (dist == n) continue;

            // Iterate over the four directions to find adjacent cells
            for (List<Integer> direction : directions) {
                // Get and apply the offsets to find the neighboring indices
                int offset_i = direction.get(0), offset_j = direction.get(1);
                int neighbor_i = i + offset_i, neighbor_j = j + offset_j;
                // If the neighbor has valid coordinates and has not been visited, then mark as visited and add it to the queue
                if (isNeighborValid(neighbor_i, neighbor_j) && !visited_grid.get(neighbor_i).get(neighbor_j)) {
                    visited_grid.get(neighbor_i).set(neighbor_j, true);
                    queue.add(new ArrayList<>(Arrays.asList(neighbor_i, neighbor_j, dist + 1)));
                }
            }
        }

        // Initialize count of cells to 0
        int count = 0;

        // Iterate over entire grid
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                // If cell is marked as visited, then increment the count
                if (visited_grid.get(i).get(j)) count++;
            }
        }

        // Return the final count
        return count;
    }

    /**
     * Function to generate the grid to store whether cells were visited or not in BFS.
     */
    public void generateVisitedGrid() {
        // Initialize the visited grid
        visited_grid = new ArrayList<>();

        // Construct the grid to be the same size as the input grid
        for (int i = 0; i < numRows; i++) {
            ArrayList<Boolean> row = new ArrayList<>();

            // Initialize all values to false
            for (int j = 0; j < numCols; j++)
                row.add(false);

            // Add row to grid
            visited_grid.add(row);
        }
    }

    /**
     * Function to check if a neighbor's coordinates are valid within the grid bounds.
     *
     * @param neighbor_i The row coordinate for the neighbor.
     * @param neighbor_j The column coordinate for the neighbor.
     * @return Boolean, true if neighbor is valid; false otherwise.
     */
    public Boolean isNeighborValid(int neighbor_i, int neighbor_j) {
        // True if i within [0, numRows-1] and j within [0, numCols-1]
        return neighbor_i >= 0 && neighbor_i < numRows && neighbor_j >= 0 && neighbor_j < numCols;
    }

    /**
     * Returns the visited_grid.
     *
     * @return ArrayList<ArrayList < Boolean>>, the visited_grid.
     */
    public ArrayList<ArrayList<Boolean>> getVisitedGrid() {
        return visited_grid;
    }
}
