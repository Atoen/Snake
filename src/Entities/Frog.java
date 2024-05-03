package Entities;

import java.awt.*;

public class Frog extends Entity implements ScoreEntity {
    public Frog(Point point) {
        super(point);
    }

    @Override
    public int getScore() {
        return 10;
    }
}
