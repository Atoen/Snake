package Entities;

import java.awt.*;

public class SnakeHead extends SnakePart {

    public static final Color HeadColor = Color.orange;

    public SnakeHead(Point point) {
        position = point;
        color = HeadColor;
    }
}
