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

/**
 * The EntityManager class manages entities within the game.
 * It provides methods for creating, finding, and drawing entities.
 */
public class EntityManager {

    private EntityManager() {}

    private static final Map<Class<? extends Entity>, Constructor<?>> _constructorCache = new HashMap<>();
    private static final List<Entity> _entities = Collections.synchronizedList(new ArrayList<>());

    private static Grid _grid;
    private static RandomPointGenerator _raRandomPointGenerator;

    /**
     * Gets the grid managed by the entity manager.
     *
     * @return The grid.
     */
    public static synchronized Grid getGrid() {
        return _grid;
    }

    /**
     * Gets the list of entities managed by the entity manager.
     *
     * @return The list of entities.
     */
    public static synchronized List<Entity> getEntities() {
        return _entities;
    }

    /**
     * Creates a new grid with the specified width and height.
     *
     * @param width  The width of the grid.
     * @param height The height of the grid.
     */
    public static synchronized void createGrid(int width, int height) {
        _grid = new Grid(width, height);
        _raRandomPointGenerator = new RandomPointGenerator(width, height);
    }

    /**
     * Finds entities of the specified class.
     *
     * @param entityType The class of the entity type to find.
     * @param <T>        The type of the entity.
     * @return A list of entities of the specified class.
     */
    public static synchronized <T extends Entity> List<T> findEntitiesOfClass(Class<T> entityType) {
        return _entities.stream()
                .filter(entityType::isInstance)
                .map(entityType::cast)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Finds entities implementing the specified interface.
     *
     * @param entityInterface The interface to find entities implementing.
     * @param <T>              The type of the interface.
     * @return A list of entities implementing the specified interface.
     */
    public static synchronized <T> List<Entity> findEntitiesImplementing(Class<T> entityInterface) {
        return _entities.stream()
                .filter(entityInterface::isInstance)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Draws all entities on the graphics object.
     *
     * @param g The graphics object.
     */
    public static synchronized void drawEntities(Graphics g) {
        _grid.clear();

        for (var entity : _entities) {
            var type = entity instanceof ScoreEntity ? Grid.Eatable : Grid.Obstacle;
            _grid.markAsOccupied(entity.getPosition(), type);
            entity.Draw(g);
        }
    }

    /**
     * Spawns obstacles in the game grid.
     *
     * @param amount      The number of obstacles to spawn.
     * @param clearRadius The radius around the center where no obstacles will be spawned.
     */
    public static synchronized void spawnObstacles(int amount, int clearRadius) {
        var center = _grid.GetCenter();

        var clearArea = new Rectangle(center.x - clearRadius, center.y - clearRadius, clearRadius * 2, clearRadius * 2);
        _raRandomPointGenerator.pickRandomPointsExcept(amount, clearArea)
                .forEach(x -> createEntity(Rock.class, x));
    }

    /**
     * Spawns entities of the specified type in the game grid.
     *
     * @param entityType The class of the entity type to spawn.
     * @param amount     The number of entities to spawn.
     * @param <T>        The type of the entity.
     */
    public static synchronized <T extends Entity> void spawnEntities(Class<T> entityType, int amount) {
        for (var i = 0; i < amount; i++) {
            spawnEntity(entityType);
        }
    }

    /**
     * Spawns an entity of the specified type in the game grid.
     *
     * @param entityType The class of the entity type to spawn.
     * @param args       The arguments to pass to the entity constructor.
     * @param <T>        The type of the entity.
     * @return The spawned entity.
     */
    public static synchronized <T extends Entity> T spawnEntity(Class<T> entityType, Object... args) {
        var point = _raRandomPointGenerator.pickRandomPointExcept(_entities);

        return createEntity(entityType, args.length == 0 ? new Object[]{point} : Stream.concat(Stream.of(point), Arrays.stream(args)).toArray());
    }

    /**
     * Creates an entity of the specified type with the given arguments.
     *
     * @param entityType The class of the entity type to create.
     * @param args       The arguments to pass to the entity constructor.
     * @param <T>        The type of the entity.
     * @return The created entity.
     */
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

    /**
     * Retrieves the constructor of the specified entity type that matches the given arguments.
     *
     * @param entityType The class of the entity type.
     * @param args       The arguments to match against the constructor parameters.
     * @return The matching constructor, or null if not found.
     */
    private static Constructor<?> getMatchingConstructor(Class<?> entityType, Object... args) {
        return Arrays.stream(entityType.getConstructors())
                .filter(constructor -> isMatchingConstructor(constructor, args))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if the specified constructor matches the given arguments.
     *
     * @param constructor The constructor to check.
     * @param args        The arguments to match against the constructor parameters.
     * @return True if the constructor matches the arguments, otherwise false.
     */
    private static boolean isMatchingConstructor(Constructor<?> constructor, Object... args) {
        var paramTypes = constructor.getParameterTypes();

        return paramTypes.length == args.length &&
                IntStream.range(0, paramTypes.length)
                        .allMatch(i -> paramTypes[i].isInstance(args[i]));
    }

}
