package Entities;

import sprites.SpriteManager;

import java.awt.*;

import static UI.GameCanvas.CellSize;

public abstract class Entity {

    protected Entity(Point point) {
        setPosition(new Point(point));
    }

    private Point _position;

    public Point getPosition() {
        return _position;
    }

    public void setPosition(Point position) {
        _position = position;
    }

    public Color getColor() { return Color.magenta; }
    public Image getSprite() { return null; }

    public boolean isColliding(Entity entity) {
        return getPosition().equals(entity.getPosition());
    }

    public boolean isColliding(Point point) {
        return getPosition().equals(point);
    }

    public void Draw(Graphics g) {
        var sprite = getSprite();
        if (sprite != null) {
            SpriteManager.DrawSprite(g, sprite, getPosition());
        } else {
            g.setColor(getColor());
            g.fillRect(getPosition().x * CellSize, getPosition().y * CellSize, CellSize, CellSize);
        }
    }
}
