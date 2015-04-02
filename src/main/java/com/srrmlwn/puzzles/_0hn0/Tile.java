package com.srrmlwn.puzzles._0hn0;

import java.util.Comparator;

/**
 * Created by sriram on 3/8/15.
 */
public class Tile {
    private Type type;
    private final int originalValue;
    private final int row;
    private final int column;

    public static final Comparator<Tile> VALUE_COMPARATOR = new Comparator<Tile>() {
        @Override
        public int compare(Tile o1, Tile o2) {
            return Integer.compare(o1.originalValue, o2.originalValue);
        }
    };

    public Tile(char value, int row, int column) {
        if (Character.isDigit(value))
        {
            this.type = Type.VALUE;
            this.originalValue = (int) value - (int) '0';
        }
        else {
            this.type = Type.of(value);
            this.originalValue = 0;
        }
        this.row = row;
        this.column = column;
    }

    public Tile(Tile tile) {
        this.type = tile.type;
        this.originalValue = tile.originalValue;
        this.row = tile.row;
        this.column = tile.column;
    }

    public boolean isValueTile() {
        return this.originalValue != 0;
    }

    public int getOriginalValue(){
        return this.originalValue;
    }

    public boolean isEmpty() {
        return this.type.isEmpty();
    }

    public boolean isBlue() {
        return this.type.isBlue() || this.isValueTile();
    }

    public boolean isRed() {
        return this.type.isRed();
    }

    public void markAsBlue() {
        this.type = Type.BLUE;
    }

    public void markAsRed() {
        this.type = Type.RED;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String toString()
    {
        return valueAsString() + " [" + this.row + ", " + this.column + "]";
    }

    public String valueAsString() {
        return this.isValueTile() ? String.valueOf(this.originalValue) : String.valueOf(this.type.c);
    }


    private static enum Type {
        BLUE('b'), RED('r'), EMPTY('-'), VALUE('0');
        private final char c;

        Type(char c) {
            this.c = c;
        }

        private boolean isBlue() {
            return this == BLUE;
        }

        private boolean isEmpty() {
            return this == EMPTY;
        }

        private boolean isRed() {
            return this == RED;
        }

        private static Type of(char c)
        {
            for (Type type : values())
            {
                if (type.c == c)
                {
                    return type;
                }
            }
            throw new IllegalStateException("Unknown input " + c);
        }
    }
}
