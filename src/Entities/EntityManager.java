package Entities;

import Game.Grid;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EntityManager {

    private EntityManager() {}

    public static List<Entity> entities = Collections.synchronizedList(new ArrayList<>());
    private static final Random _random = new Random();


    public static Grid grid;

    public static void createGrid(int width, int height) {
        grid = new Grid(width, height);
    }

    public static <T extends Entity> T findFirstEntity(Class<T> entityType) {
        for (var entity : entities) {
            if (entityType.isInstance(entity)) {
                return entityType.cast(entity);
            }
        }

        return null;
    }

    public static <T extends Entity> T findRandomEntity(Class<T> entityType) {
        var entitiesOfType = new ArrayList<T>();
        for (var entity : entities) {
            if (entityType.isInstance(entity)) {
                entitiesOfType.add(entityType.cast(entity));
            }
        }

        if (entitiesOfType.isEmpty()) {
            return null;
        }

        return entitiesOfType.get(_random.nextInt(entitiesOfType.size()));
    }

    public static void drawNonPlayerEntities(Graphics g) {
        grid.clear();

        for (var entity : entities) {
            if (!(entity instanceof SnakePart)) {
                grid.markAsOccupied(entity.position);
                entity.Draw(g);
            }
        }
    }

    public static Fruit createFruit(Point position) {
        var fruit = new Fruit(position);
        entities.add(fruit);

        return fruit;
    }

    public static Rock createRock(Point position) {
        var rock = new Rock(position);
        entities.add(rock);

        return rock;
    }

    public static Frog createFrog(Point position) {
        var frog = new Frog(position);
        entities.add(frog);

        return frog;
    }

    public static SnakePart createSnakePart(Point position) {
        var snakePart = new SnakePart(position);
        entities.add(snakePart);

        return snakePart;
    }

    public static SnakeHead createSnakeHead(Point position) {
        var snakeHead = new SnakeHead(position);
        entities.add(snakeHead);

        return snakeHead;
    }
}
