package Entities;

import java.awt.*;

public class SnakeBody extends SnakePart {
    public static final Color BodyColor = Color.orange;

    public SnakeBody(Point point) {
        position = point;
        color = BodyColor;
    }
}
