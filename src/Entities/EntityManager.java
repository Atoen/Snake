package Entities;

import Game.Grid;
import Game.RandomPointGenerator;
import Game.SnakeColor;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager {

    private EntityManager() {}

    private static final Map<Class<? extends Entity>, Constructor<? extends Entity>> _constructorCache = new HashMap<>();
    private static final List<Entity> _entities = Collections.synchronizedList(new ArrayList<>());

    private static Grid _grid;
    private static RandomPointGenerator _raRandomPointGenerator;

    public static synchronized Grid getGrid() {
        return _grid;
    }

    public static synchronized List<Entity> getEntities() {
        return _entities;
    }

    public static synchronized void createGrid(int width, int height) {
        _grid = new Grid(width, height);
        _raRandomPointGenerator = new RandomPointGenerator(width, height);
    }

    public static synchronized <T extends Entity> T findFirstEntity(Class<T> entityType) {
        return _entities.stream()
                .filter(entityType::isInstance)
                .findFirst()
                .map(entityType::cast)
                .orElse(null);
    }

    public static synchronized <T extends Entity> List<T> findAllEntities(Class<T> entityType) {
        return _entities.stream().
                filter(entityType::isInstance)
                .map(entityType::cast)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static synchronized void drawEntities(Graphics g) {
        _grid.clear();

        for (var entity : _entities) {
            _grid.markAsOccupied(entity.getPosition());
            entity.Draw(g);
        }
    }

    public static synchronized Snake createSnake(Point position, SnakeColor color, int initialLength) {
        var snake = new Snake(position, color, initialLength);
        _entities.add(snake);

        return snake;
    }

    public static synchronized void spawnObstacles(int amount, int clearRadius) {
        var center = _grid.GetCenter();

        var clearArea = new Rectangle(center.x - clearRadius, center.y - clearRadius, clearRadius * 2, clearRadius * 2);
        _raRandomPointGenerator.pickRandomPointsExcept(amount, clearArea)
                .forEach(x -> createEntity(Rock.class, x));
    }

    public static synchronized <T extends Entity> void spawnEntities(Class<T> entityType, int amount) {
        for (var i = 0; i < amount; i++) {
            spawnEntity(entityType);
        }
    }

    public static synchronized <T extends Entity> void spawnEntity(Class<T> entityType) {
        var point = _raRandomPointGenerator.pickRandomPointExcept(_entities);

        createEntity(entityType, point);
    }

    public static synchronized <T extends Entity> void createEntity(Class<T> entityType, Point position) {
        try {
            var constructor = _constructorCache.get(entityType);
            if (constructor == null) {
                constructor = entityType.getDeclaredConstructor(Point.class);
                _constructorCache.put(entityType, constructor);
            }

            var entity = entityType.cast(constructor.newInstance(position));
            _entities.add(entity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
