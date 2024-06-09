package Entities;

import Sprites.SpriteManager;

import java.awt.*;

/**
 * The Rock class represents a rock entity in the game.
 */
public class Rock extends Entity {

    private final Image _sprite;

    /**
     * Constructs a Rock at the specified position.
     *
     * @param point The initial position of the Rock.
     */
    public Rock(Point point) {
        super(point);
        _sprite = SpriteManager.getRandomRockSprite();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getSprite() {
        return _sprite;
    }
}
