package Entities;

import sprites.SpriteManager;

import java.awt.*;

public class Fruit extends Entity implements ScoreEntity {

    private final Image _sprite;

    public Fruit(Point point) {
        super(point);
        _sprite = SpriteManager.getRandomFruitSprite();
    }

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    public Image getSprite() {
        return _sprite;
    }
}
