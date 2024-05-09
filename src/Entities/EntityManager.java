package Entities;

import Game.Grid;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EntityManager {

    private EntityManager() {}

    private static final List<Entity> _entities = Collections.synchronizedList(new ArrayList<>());
    private static Grid _grid;

    public static synchronized Grid getGrid() {
        return _grid;
    }

    public static synchronized List<Entity> getEntities() {
        return _entities;
    }

    public static synchronized List<Entity> getEntities(Predicate<Entity> predicate) {
        return _entities.stream()
                .filter(predicate)
                .collect(Collectors.toList());
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

    public static synchronized void drawEntities(Graphics g) {
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

    public static synchronized Snake createSnake(Point position, int initialLength) {
        var snake = new Snake(position, initialLength);
        _entities.add(snake);

        return snake;
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
