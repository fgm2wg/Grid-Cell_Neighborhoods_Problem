package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GridCellNeighborhoodsTest {
    /**
     * Helper method to setup the grid and run the solver.
     *
     * @param numRows        The number of rows for the grid.
     * @param numCols        The number of columns for the grid,
     * @param pos_val_coords The list of coordinates for positive values in the grid.
     * @param n              The number of steps to take around positive values.
     * @return The result from the numCellsWithinNSteps method.
     */
    private int runSolverFunction(int numRows, int numCols, List<List<Integer>> pos_val_coords, int n) {
        ArrayList<ArrayList<Integer>> grid = Main.generateGrid(numRows, numCols, pos_val_coords); // Not the best/clean using a static method from Main, but makes it easy to reuse for this simple program
        GridCellNeighborhoods solver = new GridCellNeighborhoods(grid, n);
        return solver.numCellsWithinNSteps();
    }

    @Test
    public void testExample1() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(5, 5));
        int result = runSolverFunction(11, 11, pos_val_coords, 3);
        assertEquals(25, result);
    }

    @Test
    public void testExample2() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(5, 1));
        int result = runSolverFunction(11, 11, pos_val_coords, 3);
        assertEquals(21, result);
    }

    @Test
    public void testExample3() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(3, 7), Arrays.asList(7, 3));
        int result = runSolverFunction(11, 11, pos_val_coords, 2);
        assertEquals(26, result);
    }

    @Test
    public void testExample4() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(7, 3), Arrays.asList(6, 5));
        int result = runSolverFunction(11, 11, pos_val_coords, 2);
        assertEquals(22, result);
    }

    @Test
    public void testSingleCellGrid() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(0, 0));
        int result = runSolverFunction(1, 1, pos_val_coords, 0);
        assertEquals(1, result);
    }

    @Test
    public void testSingleCellOnlyNegativeGrid() {
        List<List<Integer>> pos_val_coords = List.of();
        int result = runSolverFunction(1, 1, pos_val_coords, 0);
        assertEquals(0, result);
    }

    @Test
    public void testSingleRowGrid() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(0, 2));
        int result = runSolverFunction(1, 5, pos_val_coords, 2);
        assertEquals(5, result);
    }

    @Test
    public void testSingleColGrid() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(2, 0));
        int result = runSolverFunction(5, 1, pos_val_coords, 2);
        assertEquals(5, result);
    }

    @Test
    public void testFullOverlapGrid() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(0, 0), Arrays.asList(2, 0), Arrays.asList(0, 2), Arrays.asList(2, 2));
        int result = runSolverFunction(3, 3, pos_val_coords, 2);
        assertEquals(9, result);
    }

    @Test
    public void testNZeroGrid() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(0, 0), Arrays.asList(2, 0), Arrays.asList(0, 2), Arrays.asList(2, 2));
        int result = runSolverFunction(3, 3, pos_val_coords, 0);
        assertEquals(4, result);
    }

    @Test
    public void testNMuchBiggerThanGrid() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(1, 1));
        int result = runSolverFunction(3, 3, pos_val_coords, 100);
        assertEquals(9, result);
    }

    @Test
    public void testAllPositiveGrid() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(0, 0), Arrays.asList(1, 0), Arrays.asList(0, 1), Arrays.asList(1, 1));
        int result = runSolverFunction(2, 2, pos_val_coords, 1);
        assertEquals(4, result);
    }

    @Test
    public void testOutOfBoundsCellThrows() throws IndexOutOfBoundsException {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(0, 1)); // Illegal placement of cell in 1x1 grid
        assertThrows(IndexOutOfBoundsException.class, () -> runSolverFunction(1, 1, pos_val_coords, 1));
    }

    @Test
    public void testGetVisitedGridInitialization() {
        List<List<Integer>> pos_val_coords = List.of();
        ArrayList<ArrayList<Integer>> grid = Main.generateGrid(3, 4, pos_val_coords);
        GridCellNeighborhoods solver = new GridCellNeighborhoods(grid, 2);

        ArrayList<ArrayList<Boolean>> visited = solver.getVisitedGrid();

        assertEquals(3, visited.size());
        assertEquals(4, visited.getFirst().size());

        for (ArrayList<Boolean> row : visited)
            for (Boolean cell : row)
                assertEquals(false, cell);
    }

    @Test
    public void testGetVisitedGridAfterBFS() {
        List<List<Integer>> pos_val_coords = List.of(Arrays.asList(1, 1));
        ArrayList<ArrayList<Integer>> grid = Main.generateGrid(3, 3, pos_val_coords);
        GridCellNeighborhoods solver = new GridCellNeighborhoods(grid, 1);

        solver.numCellsWithinNSteps();
        ArrayList<ArrayList<Boolean>> visited = solver.getVisitedGrid();

        boolean[][] expected = {
                {false, true, false},
                {true, true, true},
                {false, true, false}
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(expected[i][j], visited.get(i).get(j));
            }
        }
    }
}
