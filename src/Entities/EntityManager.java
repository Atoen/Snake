package Entities;

import Game.Grid;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EntityManager {

    private EntityManager() {}

    private static final List<Entity> _entities = Collections.synchronizedList(new ArrayList<>());
    private static final Random _random = new Random();
    private static final Object lock = new Object();
    private static Grid _grid;

    public static synchronized Grid getGrid() {
        return _grid;
    }

    public static synchronized List<Entity> getEntities() {
        return _entities;
    }

    public static synchronized void createGrid(int width, int height) {
        _grid = new Grid(width, height);
    }

    public static synchronized <T extends Entity> T findFirstEntity(Class<T> entityType) {
        for (var entity : _entities) {
            if (entityType.isInstance(entity)) {
                return entityType.cast(entity);
            }
        }
        return null;
    }

    public static synchronized <T extends Entity> List<T> findAllEntities(Class<T> entityType) {
        var entitiesOfType = new ArrayList<T>();
        for (var entity : _entities) {
            if (entityType.isInstance(entity)) {
                entitiesOfType.add(entityType.cast(entity));
            }
        }
        return entitiesOfType;
    }

    public static synchronized void drawNonPlayerEntities(Graphics g) {
        _grid.clear();

        for (var entity : _entities) {
            if (!(entity instanceof SnakePart)) {
                _grid.markAsOccupied(entity.getPosition());
                entity.Draw(g);
            }
        }
    }

    public static synchronized Fruit createFruit(Point position) {
        var fruit = new Fruit(position);
        _entities.add(fruit);

        return fruit;
    }

    public static synchronized Rock createRock(Point position) {
        var rock = new Rock(position);
        _entities.add(rock);

        return rock;
    }

    public static synchronized Frog createFrog(Point position) {
        var frog = new Frog(position);
        _entities.add(frog);

        return frog;
    }

    public static synchronized SnakePart createSnakePart(Point position) {
        var snakePart = new SnakePart(position);
        _entities.add(snakePart);

        return snakePart;
    }

    public static synchronized SnakeHead createSnakeHead(Point position) {
        var snakeHead = new SnakeHead(position);
        _entities.add(snakeHead);

        return snakeHead;
    }
}
