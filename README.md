# 0hn0-solver
Solver for 0hn0 logic puzzles [http://0hn0.com]

* Solution finds all possible valid combinations for a given value tile, and proceeds by filling tiles common to every valid combination.
* Best case scenario is when only one valid combination is found for a given value tile, in which case, all the tiles in that combination can be filled.
* Worst case scenario is when no common tile is present across all valid combinations for a given value tile, in which case, no tile is filled and the next value tile is moved on to.

* Puzzle input/output
  * `-` represents an empty tile
  * `n` where n is a number > 0 represents a value tile that can only have n tiles visible to it
  * `r` represents a red tile
  * `b` represents a blue tile (only present in the output)

Sample 5x5 puzzle: `---r---34--4--2-3r3---1r-`

**Usage**
```
java com.srrmlwn.puzzles._0hn0.Solver <puzzle>
```

**Example**
```
> java com.srrmlwn.puzzles._0hn0.Solver ---r---34--4--2-3r3---1r-
Input:
---r-
--34-
-4--2
-3r3-
--1r-

Output:
rrbrr
rb34r
b4rb2
r3r3b
rb1rr

```