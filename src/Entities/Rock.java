package Entities;

import java.awt.*;

public class Rock extends Entity {
    public Rock(Point point) {
        super(point);
    }

    @Override
    public Color getColor() {
        return Color.gray;
    }
}
