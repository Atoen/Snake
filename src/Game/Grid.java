package Game;

import java.awt.*;
import java.util.Arrays;

public class Grid {
    private final int _width;
    private final int _height;

    private final boolean[][] _tiles;

    public Point GetCenter() {
        return new Point(_width / 2, _height / 2);
    }

    public Grid(int width, int height) {
        _width = width;
        _height = height;

        _tiles = new boolean[width][height];
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public void clear() {
        for (boolean[] tile : _tiles) {
            Arrays.fill(tile, false);
        }
    }

    public void markAsOccupied(Point position) {
        _tiles[position.x][position.y] = true;
    }

    public boolean isSpotAvailable(Point position) {
        return !_tiles[position.x][position.y];
    }

    public boolean isValidPosition(Point position) {
        return position.x >= 0 && position.x < _width &&
               position.y >= 0 && position.y < _height &&
                isSpotAvailable(position);
    }
}
