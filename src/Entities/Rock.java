package Entities;

import Sprites.SpriteManager;

import java.awt.*;

public class Rock extends Entity {

    private final Image _sprite;

    public Rock(Point point) {
        super(point);
        _sprite = SpriteManager.getRandomRockSprite();
    }

    @Override
    public Image getSprite() {
        return _sprite;
    }
}
