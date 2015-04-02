package com.srrmlwn.puzzles._0hn0;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private final Grid grid;

    public Solver(Grid grid) {
        this.grid = grid;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java " + Solver.class.getName() + " <puzzle>");
            return;
        }

        String puzzle = args[0];

        final Grid input = PuzzleReader.toGrid(puzzle);
        System.out.println("Input:");
        System.out.println(input);

        Grid output = new Solver(input).solve();

        System.out.println("Output:");
        System.out.println(output);
    }

    public Grid solve() {

        List<Tile> valueTiles = this.unsolvedTilesSortedByValue();
        int previousUnsolved = Integer.MAX_VALUE;
        while (previousUnsolved > this.grid.getUnsolvedTiles()) {
            previousUnsolved = this.grid.getUnsolvedTiles();
            valueTiles.forEach(this::solveTile);
            this.markBlockedTilesAsRed();
        }

        if (this.grid.getUnsolvedTiles() > 0) {
            throw new IllegalStateException("Unable to solve puzzle! Current state: " + this.grid);
        }

        return this.grid;
    }

    private void markBlockedTilesAsRed() {
        for (int row = 0; row < this.grid.size(); row++) {
            for (int column = 0; column < this.grid.size(); column++) {
                final Tile tile = this.grid.tileAt(row, column);
                if (tile.isEmpty()) {
                    final boolean[] surroundedByRed = {true};
                    Direction.forAll((d) -> {
                        final Tile go = grid.go(tile, d);
                        surroundedByRed[0] &= (go == null || go.isRed());
                    });
                    if (surroundedByRed[0]) {
                        tile.markAsRed();
                    }
                }
            }
        }
    }

    private void solveTile(Tile tile) {
        if (!this.grid.hasResidualValue(tile)) {
            GridOperations.fillOpenTileWithRed(this.grid, tile);
            return;
        }

        // find all valid combinations for the given tile
        List<List<Tile>> validCombinations = GridOperations.validCombinations(this.grid, tile);
        // find tiles which are common to all valid combinations
        List<Tile> intersectionOfValidCombinations = GridOperations.intersection(validCombinations);
        // mark the intersection of such tiles as blue
        intersectionOfValidCombinations.stream().filter(validTile -> !validTile.isValueTile()).forEach(Tile::markAsBlue);
    }


    private List<Tile> unsolvedTilesSortedByValue() {
        List<Tile> valueTiles = new ArrayList<>(this.grid.getValueTiles());
        valueTiles.sort(Tile.VALUE_COMPARATOR);
        return valueTiles;
    }
}
