package Entities;

import java.awt.*;

import static UI.GamePanel.CellSize;

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
            var g2d = (Graphics2D) g;
            var centerX = position.x * CellSize + CellSize / 2;
            var centerY = position.y * CellSize + CellSize / 2;

            g2d.translate(centerX, centerY);
            g2d.drawImage(sprite, -CellSize / 2, -CellSize / 2, CellSize, CellSize, null);
        } else {
            g.setColor(getColor());
            g.fillRect(position.x * CellSize, position.y * CellSize, CellSize, CellSize);
        }
    }
}
