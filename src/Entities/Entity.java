package Entities;

import java.awt.*;

public abstract class Entity {

    protected Entity(Point point) {
        position = new Point(point);
    }

    public Point position;
    public Color color = Color.magenta;

    public boolean isColliding(Entity entity) {
        return position.equals(entity.position);
    }

    public boolean isColliding(Point point) {
        return position.equals(point);
    }
}
