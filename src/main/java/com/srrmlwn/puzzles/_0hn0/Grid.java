package com.srrmlwn.puzzles._0hn0;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sriram on 3/8/15.
 */
public class Grid {
    private final Tile[][] tiles;
    private final List<Tile> valueTiles;

    public Grid(Tile[][] tiles) {
        this.tiles = tiles;
        this.valueTiles = new ArrayList<>();
        //noinspection ForLoopReplaceableByForEach
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (tiles[row][col].isValueTile()) {
                    this.valueTiles.add(tiles[row][col]);
                }
            }
        }
    }

    public Grid cloneWithOverrides(List<Tile> overridenTiles)
    {
        Tile[][] deepCopy = new Tile[this.tiles.length][this.tiles.length];
        for (int row = 0; row < this.tiles.length; row++) {
            for (int column = 0; column < this.tiles[row].length; column++) {
                 deepCopy[row][column] = new Tile(this.tiles[row][column]);
            }
        }

        for (Tile tile : overridenTiles)
        {
            deepCopy[tile.getRow()][tile.getColumn()] = tile;
        }
        return new Grid(deepCopy);
    }

    public Tile go(Tile tile, Direction direction) {
        return direction.go(this, tile);
    }

    public int getUnsolvedTiles() {
        int unsolvedTiles = 0;
        for (int row = 0; row < this.size(); row++) {
            for (int column = 0; column < this.size(); column++) {
                 if (this.tileAt(row, column).isEmpty())
                 {
                     unsolvedTiles++;
                 }

            }
        }
        return unsolvedTiles;
    }

    public List<Tile> getValueTiles() {
        return this.valueTiles;
    }

    public Tile tileAt(int row, int column) {
        return this.tiles[row][column];
    }

    public int size() {
        return this.tiles.length;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(this.size() * this.size());
        for (int row = 0; row < this.size(); row++) {
            for (int col = 0; col < this.size(); col++) {
                builder.append(this.tileAt(row, col).valueAsString());
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public boolean hasResidualValue(Tile tile) {
        final int residualValue = computeResidualValueOf(tile);
        return residualValue != 0;
    }

    public int computeResidualValueOf(Tile tile) {
        final int[] residualValue = {tile.getOriginalValue()};
        Direction.forAll((direction) -> {
            Tile adjacentTile = this.go(tile, direction);
            while (adjacentTile != null && adjacentTile.isBlue()) {
                residualValue[0]--;
                adjacentTile = this.go(adjacentTile, direction);
            }
        });
        return residualValue[0];
    }
}
