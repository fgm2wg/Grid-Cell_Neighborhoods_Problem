# Grid Cell Neighborhoods

## Main.java
- Main class to run the examples given and visualize the resulting grids.
- Once run it should output the integer count of how many cells are within N steps from a positive number in the grid as well as the visualized grid with blue ASCII boxes for the positive numbers, green ones for the neighboring, and dotted boxes for the other cells in the grid.
- Example output from Example 4:
- <img width="336" height="341" alt="image" src="https://github.com/user-attachments/assets/20c164cd-b8d9-4d10-803b-9847cde3ef02" />

## GridCellNeighborhoods.java
- Class responsible for solving the Grid-Cell Neighborhoods problem and returning the result.
- Constructs the n x m grid and visited_grid.
- `numCellsWithinNSteps` function that solves the problem using a BFS approach to start at positive values and go through each neighbor with BFS until reaching the max steps to find all cells within N steps of positive integers in the grid.
- `generateVisitedGrid` helper function to build the visited_grid to same size as the input grid.
- `isNeighborValid` helper function to determine if a neighbor has valid coordinates within the given grid.
- `getVisitedGrid` get function to get the visited_grid to expose for Main class to visualize resulting grid.
- The time complexity for the `numCellsWithinNSteps` function should be O(n * m) where n is the number of rows and m is the number of columns. This is because with the BFS approach we mark cells are visited once we see them, so we can at worst case only each each cell once which is O(n * m) if we must look at every cell in the grid. When we visit each cell, we look for its 4 adjacent neighbors (if it has them) which is always a constant check so that is O(4) and thus O(1). So all together it is worst case of O(n * m) * O(1) = O(n * m).
- The reason I chose to use BFS to solve this problem efficiently is because of the N steps that we must take to find the neighbors. If we only had N=1, then we would not need BFS and could simply look at its 4 adjacent neighbors. But since we can have N >= 0, we must use BFS so that once we find the neighbors of the positive integer, we can then iterate over that neighbor's neighbors and so on to effectively go through every N level. This is far more efficient than a naive approach of simply iterating over every neighboring index each time and we can keep track of cells that we have already visited to avoid any double-counting due to overlap.

## GridCellNeighborhoodsTest.java
- This class provides some simple JUnit tests for the GridCellNeighborhoods methods to ensure that they are correct and work for a variety of cases not explicity listed in the given examples, though not 100% exhaustive. All tests should pass and achieve 100% coverage for the GridCellNeighborhoods.java class.

## How to Run
- This was made using Gradle.
- First run `.\gradlew build`
- Then either manually run the Main.java class directly or `.\gradlew run`

## Other Notes
- As mentioned in the GridCellNeighborhoods.java file, I did use online resources to help me complete this problem which are listed below:
- https://www.geeksforgeeks.org/dsa/breadth-first-traversal-bfs-on-a-2d-array/
  - Found how to best implement the BFS algorithm in Java.
- https://stackoverflow.com/questions/77274736/how-to-find-neighbors-in-a-grid
  - Found how to best find adjacent neighbors in grid efficiently.
