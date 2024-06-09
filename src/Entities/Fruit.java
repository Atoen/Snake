package Entities;

import Sprites.SpriteManager;

import java.awt.*;

/**
 * The Fruit class represents a fruit entity in the game.
 * It implements the ScoreEntity interface.
 */
public class Fruit extends Entity implements ScoreEntity {

    private final Image _sprite;

    /**
     * Constructs a Fruit at the specified position.
     *
     * @param point The initial position of the Fruit.
     */
    public Fruit(Point point) {
        super(point);
        _sprite = SpriteManager.getRandomFruitSprite();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGrowLength() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getSprite() {
        return _sprite;
    }
}
