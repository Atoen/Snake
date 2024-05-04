package Entities;

import sprites.SpriteManager;

import java.awt.*;

public class Frog extends Entity implements ScoreEntity {

    private static final int FramesPerJump = 5;
    private int _frame = 0;

    public Frog(Point point) {
        super(point);
    }

    @Override
    public int getScore() {
        return 5;
    }

    @Override
    public int getGrowLength() {
        return 2;
    }

    @Override
    public void Draw(Graphics g) {
        if (_frame++ > FramesPerJump) {
            _frame = 0;
            position.x++;
        }

        super.Draw(g);
    }

    @Override
    public Image getSprite() {
        return SpriteManager.getFrogSprite();
    }
}
