package com.srrmlwn.puzzles._0hn0;

/**
 * Created by sriram on 3/8/15.
 */
public class PuzzleReader {

    public static Grid toGrid(String gridRepresentation)
    {
        final int gridCells = gridRepresentation.length();
        if (!isPerfectSquare(gridCells)) throw new IllegalArgumentException("Grid of size " + gridCells + " is not a perfect square!");
        int gridSize = (int) Math.sqrt(gridCells);
        Tile[][] grid = new Tile[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                char c = gridRepresentation.charAt(i * gridSize + j);
                grid[i][j] = new Tile(c, i, j);
            }
        }
        return new Grid(grid);
    }


    private static boolean isPerfectSquare (int x)
    {
        int sqrt = (int) Math.sqrt(x);
        return sqrt * sqrt == x;
    }
}
