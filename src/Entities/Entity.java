package Entities;

import Sprites.SpriteManager;

import java.awt.*;

import static UI.GameCanvas.CellSize;

/**
 * The Entity class represents a game entity with a position and drawing capabilities.
 */
public abstract class Entity {

    private Point _position;

    /**
     * Constructs an Entity at the specified position.
     *
     * @param point The initial position of the Entity.
     */
    protected Entity(Point point) {
        setPosition(new Point(point));
    }

    /**
     * Gets the current position of the Entity.
     *
     * @return The current position as a Point.
     */
    public Point getPosition() {
        return _position;
    }

    /**
     * Sets the position of the Entity.
     *
     * @param position The new position of the Entity.
     */
    public void setPosition(Point position) {
        _position = position;
    }

    /**
     * Gets the color of the Entity. Default is magenta.
     *
     * @return The color of the Entity.
     */
    public Color getColor() {
        return Color.magenta;
    }

    /**
     * Gets the sprite image of the Entity. Default is null.
     *
     * @return The sprite image of the Entity.
     */
    public Image getSprite() {
        return null;
    }

    /**
     * Checks if the Entity is colliding with a specified point.
     *
     * @param point The point to check for collision.
     * @return True if the Entity's position equals the specified point, otherwise false.
     */
    public boolean isColliding(Point point) {
        return getPosition().equals(point);
    }

    /**
     * Draws the Entity using the provided Graphics context.
     *
     * @param g The Graphics context to draw on.
     */
    public void Draw(Graphics g) {
        var sprite = getSprite();
        if (sprite != null) {
            SpriteManager.DrawSprite(g, sprite, getPosition());
        } else {
            g.setColor(getColor());
            g.fillRect(getPosition().x * CellSize, getPosition().y * CellSize, CellSize, CellSize);
        }
    }

    /**
     * Calculates the Euclidean distance to another Entity.
     *
     * @param other The other Entity to calculate the distance to.
     * @return The distance to the other Entity.
     */
    public double distanceTo(Entity other) {
        return Math.sqrt(Math.pow(_position.x - other._position.x, 2) + Math.pow(_position.y - other._position.y, 2));
    }
}
