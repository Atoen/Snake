package Entities;

import java.awt.*;

public class Apple extends Entity implements ScoreEntity {
    public Apple(Point point) {
        super(point);
    }

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    public Color getColor() {
        return Color.red;
    }
}
