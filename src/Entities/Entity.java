package Entities;

import sprites.SpriteManager;

import java.awt.*;

import static UI.GameCanvas.CellSize;

public abstract class Entity {

    protected Entity(Point point) {
        position = new Point(point);
    }

    public Point position;

    public Color getColor() { return Color.magenta; }
    public Image getSprite() { return null; }

    public boolean isColliding(Entity entity) {
        return position.equals(entity.position);
    }

    public boolean isColliding(Point point) {
        return position.equals(point);
    }

    public void Draw(Graphics g) {
        var sprite = getSprite();
        if (sprite != null) {
            SpriteManager.DrawSprite(g, sprite, position);
        } else {
            g.setColor(getColor());
            g.fillRect(position.x * CellSize, position.y * CellSize, CellSize, CellSize);
        }
    }
}
