package com.srrmlwn.puzzles._0hn0;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by sriram on 3/22/15.
 */
public interface Direction {

    public Tile go(Grid grid, Tile tile);

    public static Direction LEFT = (grid, tile) -> tile.getColumn() - 1 >= 0 ? grid.tileAt(tile.getRow(), tile.getColumn() - 1) : null;

    public static Direction RIGHT = (grid, tile) -> tile.getColumn() + 1 >= grid.size() ? null : grid.tileAt(tile.getRow(), tile.getColumn() + 1);

    public static Direction ABOVE = (grid, tile) -> tile.getRow() - 1 >= 0 ? grid.tileAt(tile.getRow() - 1, tile.getColumn()) : null;

    public static Direction BELOW = (grid, tile) -> tile.getRow() + 1 >= grid.size() ? null : grid.tileAt(tile.getRow() + 1, tile.getColumn());

    public static List<Direction> ALL = Arrays.asList(LEFT, RIGHT, ABOVE, BELOW);

    public static void forAll(Consumer<Direction> action)
    {
        ALL.stream().forEach(action);
    }
}
