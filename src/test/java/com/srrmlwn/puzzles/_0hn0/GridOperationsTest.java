package com.srrmlwn.puzzles._0hn0;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class GridOperationsTest {

    @Test
    public void isValidCombination() {
        String gridRepresentation =
                "1---bb"
                        + "------"
                        + "2----3"
                        + "----1-"
                        + "r-b-33"
                        + "------";
        Grid grid = PuzzleReader.toGrid(gridRepresentation);
        Assert.assertTrue(GridOperations.isValidCombination(grid, Arrays.asList(grid.tileAt(0, 0), grid.tileAt(0, 1))));
        Assert.assertFalse(GridOperations.isValidCombination(grid, Arrays.asList(grid.tileAt(0, 0), grid.tileAt(1, 0), grid.tileAt(2, 0))));
        Assert.assertFalse(GridOperations.isValidCombination(grid, Arrays.asList(grid.tileAt(3, 0), grid.tileAt(4, 0), grid.tileAt(2, 0))));
        Assert.assertFalse(GridOperations.isValidCombination(grid, Arrays.asList(grid.tileAt(0, 5), grid.tileAt(1, 5), grid.tileAt(2, 5), grid.tileAt(3, 5))));
    }

    @Test
    public void validCombinations() {
        String gridRepresentation =
                "1---bb"
                        + "------"
                        + "2----3"
                        + "----1-"
                        + "r-r-33"
                        + "------";
        Grid grid = PuzzleReader.toGrid(gridRepresentation);
        assertListOfTiles(Arrays.asList(Arrays.<Tile>asList(grid.tileAt(0, 1), grid.tileAt(0, 0))), GridOperations.validCombinations(grid, grid.tileAt(0, 0)));
        assertListOfTiles(Arrays.asList(Arrays.<Tile>asList(grid.tileAt(4, 4), grid.tileAt(3, 4))), GridOperations.validCombinations(grid, grid.tileAt(3, 4)));
        assertListOfTiles(Arrays.asList(Arrays.asList(grid.tileAt(4, 4), grid.tileAt(3, 4))), GridOperations.validCombinations(grid, grid.tileAt(3, 4)));
        assertListOfTiles(Arrays.asList(Arrays.asList(grid.tileAt(4, 3), grid.tileAt(4, 5), grid.tileAt(3, 4), grid.tileAt(4, 4))), GridOperations.validCombinations(grid, grid.tileAt(4, 4)));
    }

    private static void assertListOfTiles(List<List<Tile>> expectedTiles, List<List<Tile>> actualTiles) {
        Assert.assertEquals("Lists do not match in size", expectedTiles.size(), actualTiles.size());
        for (int i = 0; i < actualTiles.size(); i++) {
            assertContainsAllTiles(expectedTiles.get(i), actualTiles.get(i));
        }
    }

    @Test
    public void intersection() {
        String gridRepresentation =
                          "1---bb"
                        + "------"
                        + "2----3"
                        + "----1-"
                        + "r-r-33"
                        + "--1---";
        Grid grid = PuzzleReader.toGrid(gridRepresentation);
        assertContainsAllTiles(Arrays.<Tile>asList(grid.tileAt(0, 1), grid.tileAt(0, 0)), GridOperations.intersection(GridOperations.validCombinations(grid, grid.tileAt(0, 0))));
        assertContainsAllTiles(Arrays.<Tile>asList(grid.tileAt(2, 1), grid.tileAt(2, 0)), GridOperations.intersection(GridOperations.validCombinations(grid, grid.tileAt(2, 0))));
        assertContainsAllTiles(Arrays.<Tile>asList(grid.tileAt(4, 5), grid.tileAt(4, 4)), GridOperations.intersection(GridOperations.validCombinations(grid, grid.tileAt(4, 5))));
        assertContainsAllTiles(Arrays.<Tile>asList(grid.tileAt(5, 2)), GridOperations.intersection(GridOperations.validCombinations(grid, grid.tileAt(5, 2))));
    }

    private static void assertContainsAllTiles(List<Tile> expectedTiles, List<Tile> actualTiles) {
        Assert.assertEquals("Lists do not match in size.\n" +
                "Actual List=" + actualTiles + "; Expected List=" + expectedTiles, expectedTiles.size(), actualTiles.size());
        for (int i = 0; i < actualTiles.size(); i++) {
            Tile actualTile = actualTiles.get(i);
            boolean found = false;
            for (int j = 0; !found && j < expectedTiles.size(); j++) {
                Tile expectedTile = expectedTiles.get(j);
                if (actualTile.getRow() == expectedTile.getRow() && actualTile.getColumn() == expectedTile.getColumn()) {
                    found = true;
                }
            }
            if (!found) {
                Assert.fail("Tile " + actualTile + " is not found in the expected list.\nActual List=" + actualTiles + "; Expected List=" + expectedTiles);
            }

        }
    }

    @Test
    public void indexPermutations() {
        String gridRepresentation =
                "1---bb"
                        + "------"
                        + "2----3"
                        + "----1-"
                        + "r-b-33"
                        + "------";
        Grid grid = PuzzleReader.toGrid(gridRepresentation);
        final Tile cellAt0x0 = grid.tileAt(0, 0);
        assertIntArrays(Arrays.asList(
                new int[]{0, 0, 0, 1},
                new int[]{0, 1, 0, 0}), GridOperations.indexPermutations(grid, cellAt0x0));

        final Tile cellAt2x0 = grid.tileAt(2, 0);
        assertIntArrays(Arrays.asList(
                new int[]{0, 0, 0, 2},
                new int[]{0, 0, 1, 1},
                new int[]{0, 0, 2, 0},
                new int[]{0, 1, 0, 1},
                new int[]{0, 1, 1, 0},
                new int[]{0, 2, 0, 0}), GridOperations.indexPermutations(grid, cellAt2x0));

        final Tile cellAt4x4 = grid.tileAt(4, 4);
        assertIntArrays(Arrays.asList(
                new int[]{0, 0, 2, 1},
                new int[]{0, 0, 3, 0},
                new int[]{0, 1, 1, 1},
                new int[]{0, 1, 2, 0},
                new int[]{1, 0, 1, 1},
                new int[]{1, 0, 2, 0},
                new int[]{1, 1, 0, 1},
                new int[]{1, 1, 1, 0},
                new int[]{2, 0, 0, 1},
                new int[]{2, 0, 1, 0},
                new int[]{2, 1, 0, 0},
                new int[]{3, 0, 0, 0}), GridOperations.indexPermutations(grid, cellAt4x4));

        final Tile cellAt4x5 = grid.tileAt(4, 5);
        assertIntArrays(Arrays.asList(
                new int[]{0, 0, 2, 1},
                new int[]{0, 0, 3, 0},
                new int[]{1, 0, 1, 1},
                new int[]{1, 0, 2, 0},
                new int[]{2, 0, 0, 1},
                new int[]{2, 0, 1, 0},
                new int[]{3, 0, 0, 0}), GridOperations.indexPermutations(grid, cellAt4x5));
    }

    private static void assertIntArrays(List<int[]> expectedList, List<int[]> actualList) {
        Assert.assertEquals("Lists size don't match", expectedList.size(), actualList.size());
        for (int i = 0; i < actualList.size(); i++) {
            final int[] expected = expectedList.get(i);
            final int[] actual = actualList.get(i);
            Assert.assertArrayEquals("Mismatch at Index " + i + ": Expected = " + Arrays.toString(expected) + "; Actual = " + Arrays.toString(actual), expected, actual);
        }
    }

    @Test
    public void fillOpenTileWithRed() {
        final String gridRepresentation =
                "2b--" +
                        "b---" +
                        "--1b" +
                        "----";
        Grid gridOne = PuzzleReader.toGrid(gridRepresentation);
        Tile tileOne = gridOne.tileAt(0, 0);
        GridOperations.fillOpenTileWithRed(gridOne, tileOne);
        Assert.assertEquals("2br-\nb---\nr-1b\n----\n", gridOne.toString());

        Grid gridTwo = PuzzleReader.toGrid(gridRepresentation);
        Tile tileTwo = gridTwo.tileAt(2, 2);
        GridOperations.fillOpenTileWithRed(gridTwo, tileTwo);
        Assert.assertEquals("2b--\nb-r-\n-r1b\n--r-\n", gridTwo.toString());

    }
}