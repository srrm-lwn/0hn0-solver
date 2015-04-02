package com.srrmlwn.puzzles._0hn0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by sriram on 3/11/15.
 */
public class GridOperations {

    public static void fillOpenTileWithRed(Grid grid, Tile tile) {
        Direction.forAll((direction) -> {
            Tile adjacentTile = grid.go(tile, direction);
            while (adjacentTile != null) {
                if (adjacentTile.isBlue()) {
                    adjacentTile = grid.go(adjacentTile, direction);
                    continue;
                }
                if (adjacentTile.isRed()) {
                    break;
                }
                if (adjacentTile.isEmpty()) {
                    adjacentTile.markAsRed();
                    break;
                }

            }
        });
    }

    public static List<Tile> intersection(List<List<Tile>> validCombinations) {
        Map<Tile, Integer> tileOccurences = new HashMap<>();
        for (List<Tile> validCombination : validCombinations) {
            for (Tile tile : validCombination) {
                if (!tileOccurences.containsKey(tile)) {
                    tileOccurences.put(tile, 0);
                }
                tileOccurences.put(tile, tileOccurences.get(tile) + 1);
            }
        }

        return tileOccurences.keySet().stream().filter(tile -> tileOccurences.get(tile) == validCombinations.size()).collect(Collectors.toList());
    }


    public static List<List<Tile>> validCombinations(Grid grid, Tile tile) {
        List<List<Tile>> validCombinations = new ArrayList<>();
        List<int[]> indexPermutations = indexPermutations(grid, tile);

        for (int[] indexPermutation : indexPermutations) {
            List<Tile> combination = new ArrayList<>();
            addToCombination(grid, tile, combination, indexPermutation[0], Direction.LEFT);
            addToCombination(grid, tile, combination, indexPermutation[1], Direction.RIGHT);
            addToCombination(grid, tile, combination, indexPermutation[2], Direction.ABOVE);
            addToCombination(grid, tile, combination, indexPermutation[3], Direction.BELOW);
            combination.add(tile);
            if (isValidCombination(grid, combination)) {
                validCombinations.add(combination);
            }
        }

        return validCombinations;
    }

    public static List<int[]> indexPermutations(Grid grid, Tile tile) {
        List<int[]> indexPermutations = new ArrayList<>();
        final int leftRange = Math.min(tile.getColumn(), tile.getOriginalValue());
        final int rightRange = Math.min(grid.size() - tile.getColumn() - 1, tile.getOriginalValue());
        final int aboveRange = Math.min(tile.getRow(), tile.getOriginalValue());
        final int belowRange = Math.min(grid.size() - tile.getRow() - 1, tile.getOriginalValue());
        for (int l = 0; l <= leftRange; l++) {
            for (int r = 0; r <= rightRange; r++) {
                for (int a = 0; a <= aboveRange; a++) {
                    for (int b = 0; b <= belowRange; b++) {
                        if (l + r + a + b == tile.getOriginalValue()) {
                            indexPermutations.add(new int[]{l, r, a, b});
                        }
                    }
                }
            }
        }
        return indexPermutations;
    }

    private static void addToCombination(Grid grid, Tile tile, List<Tile> combination, int maxRange, Direction direction) {
        int index = 0;
        Tile adjacentTile = grid.go(tile, direction);
        while (adjacentTile != null && index < maxRange) {
            index++;
            combination.add(adjacentTile);
            adjacentTile = grid.go(adjacentTile, direction);
        }
    }

    public static boolean isValidCombination(Grid grid, List<Tile> combination) {
        List<Tile> overriddenTiles = new ArrayList<>(combination.size());
        boolean valueCheckRequired = false;
        for (Tile combinationTile : combination) {
            if (combinationTile.isRed()) return false;
            Tile overriddenTile = new Tile(combinationTile);
            if (overriddenTile.isEmpty()) {
                overriddenTile.markAsBlue();
            }
            valueCheckRequired |= overriddenTile.isValueTile();
            overriddenTiles.add(overriddenTile);
        }

        if (valueCheckRequired) {
            Grid clonedGrid = grid.cloneWithOverrides(overriddenTiles);
            for (Tile overriddenTile : overriddenTiles) {
                if (overriddenTile.isValueTile() && clonedGrid.computeResidualValueOf(overriddenTile) < 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
