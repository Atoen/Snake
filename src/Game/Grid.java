package Game;

import java.awt.*;
import java.util.Arrays;

public class Grid {

    public static final int Empty = 0;
    public static final int Eatable = 1;
    public static final int Obstacle = 2;

    private final int _width;
    private final int _height;

    private final int[][] _tiles;

    public Point GetCenter() {
        return new Point(_width / 2, _height / 2);
    }

    public Grid(int width, int height) {
        _width = width;
        _height = height;

        _tiles = new int[width][height];
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public void clear() {
        for (int[] tile : _tiles) {
            Arrays.fill(tile, Empty);
        }
    }

    public void markAsOccupied(Point position, int type) {
        _tiles[position.x][position.y] = type;
    }

    public boolean isSpotEmpty(Point position) {
        return _tiles[position.x][position.y] == Empty;
    }

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
