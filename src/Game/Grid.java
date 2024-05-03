package Game;

public class Grid {
    private final int _width;
    private final int _height;

    public Grid(int width, int height) {
        _width = width;
        _height = height;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }
}
