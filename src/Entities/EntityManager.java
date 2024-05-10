package Entities;

import Game.Grid;
import Game.RandomPointGenerator;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EntityManager {

    private EntityManager() {}

    private static final Map<Class<? extends Entity>, Constructor<?>> _constructorCache = new HashMap<>();
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

    public static synchronized <T extends Entity> T spawnEntity(Class<T> entityType, Object... args) {
        var point = _raRandomPointGenerator.pickRandomPointExcept(_entities);

        var ctorArgs = args.length == 0 ? point : Stream.concat(Stream.of(point), Arrays.stream(args));
        return createEntity(entityType, ctorArgs);
    }

    public static synchronized <T extends Entity> T createEntity(Class<T> entityType, Object... args) {
        try {
            var constructor = _constructorCache.computeIfAbsent(entityType, key -> getMatchingConstructor(key, args));
            var entity = entityType.cast(constructor.newInstance(args));
            _entities.add(entity);
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Constructor<?> getMatchingConstructor(Class<?> entityType, Object... args) {
        return Arrays.stream(entityType.getConstructors())
                .filter(constructor -> isMatchingConstructor(constructor, args))
                .findFirst()
                .orElse(null);
    }

    private static boolean isMatchingConstructor(Constructor<?> constructor, Object... args) {
        var paramTypes = constructor.getParameterTypes();

        return paramTypes.length == args.length &&
               IntStream.range(0, paramTypes.length)
                   .allMatch(i -> paramTypes[i].isInstance(args[i]));
    }
}
