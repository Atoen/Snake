import java.util.Random;

public class Grid {
    private final int _width;
    private final int _height;

    private final boolean[][] _cells;

    public Grid(int width, int height) {
        _width = width;
        _height = height;

        _cells = new boolean[height][width];
        fillRandomly();
    }

    public boolean getCell(int row, int column) {
        return _cells[row][column];
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    private void fillRandomly() {
        var random = new Random();
        for (int row = 0; row < _height; row++) {
            for (int col = 0; col < _width; col++) {
                _cells[row][col] = random.nextBoolean();
            }
        }
    }
}
