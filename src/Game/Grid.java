package Game;

import java.awt.*;
import java.util.Arrays;

/**
 * The Grid class represents a grid-based game board.
 */
public class Grid {

    /** Represents an empty cell in the grid. */
    public static final int Empty = 0;
    /** Represents a cell containing an eatable entity in the grid. */
    public static final int Eatable = 1;
    /** Represents a cell containing an obstacle entity in the grid. */
    public static final int Obstacle = 2;

    private final int _width;
    private final int _height;

    private final int[][] _tiles;

    /**
     * Constructs a new Grid with the specified width and height.
     *
     * @param width  The width of the grid.
     * @param height The height of the grid.
     */
    public Grid(int width, int height) {
        _width = width;
        _height = height;

        _tiles = new int[width][height];
    }

    /**
     * Gets the center point of the grid.
     *
     * @return The center point of the grid.
     */
    public Point GetCenter() {
        return new Point(_width / 2, _height / 2);
    }

    /**
     * Gets the width of the grid.
     *
     * @return The width of the grid.
     */
    public int getWidth() {
        return _width;
    }

    /**
     * Gets the height of the grid.
     *
     * @return The height of the grid.
     */
    public int getHeight() {
        return _height;
    }

    /**
     * Clears the grid, setting all cells to empty.
     */
    public void clear() {
        for (int[] tile : _tiles) {
            Arrays.fill(tile, Empty);
        }
    }

    /**
     * Marks the specified position in the grid as occupied with the given type.
     *
     * @param position The position to mark as occupied.
     * @param type     The type of entity occupying the position.
     */
    public void markAsOccupied(Point position, int type) {
        _tiles[position.x][position.y] = type;
    }

    /**
     * Checks if the specified position in the grid is empty.
     *
     * @param position The position to check.
     * @return True if the position is empty, otherwise false.
     */
    public boolean isSpotEmpty(Point position) {
        return _tiles[position.x][position.y] == Empty;
    }

    /**
     * Checks if the specified position in the grid is a valid position.
     *
     * @param position     The position to check.
     * @param allowEatables True if eatable entities are allowed, otherwise false.
     * @return True if the position is valid, otherwise false.
     */
    public boolean isValidPosition(Point position, boolean allowEatables) {
        var outOfBounds = position.x < 0 || position.x >= _width ||
                position.y < 0 || position.y >= _height;

        if (outOfBounds) return false;

        var tile = _tiles[position.x][position.y];
        return allowEatables
                ? tile == 0 || tile == 1
                : tile == 0;
    }
}
