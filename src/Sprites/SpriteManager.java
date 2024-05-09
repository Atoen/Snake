package Sprites;

import Game.Direction;
import Game.SnakeColor;
import Game.SnakePart;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static UI.GameCanvas.CellSize;

public class SpriteManager {

    private static final Map<SnakeColor, Map<SnakePart, Image>> _snakeSprites = new HashMap<>();

    private static Image[] _rockSprites;
    private static Image[] _fruitSprites;
    private static Image _frogSprite;

    private static final Random _random = new Random();

    public static Image getSnakeSprite(SnakeColor color, SnakePart part) {
        var partMap = _snakeSprites.computeIfAbsent(color, _ -> CreateEmptyPartMap());
        var image = partMap.get(part);

        if (image != null) {
            return image;
        }

        var path = createPath(color, part);
        image = loadImage(path);
        partMap.put(part, image);

        return image;
    }

    public static Map<SnakePart, Image> getSnakeSprites(SnakeColor color) {
        var partMap = _snakeSprites.computeIfAbsent(color, _ -> CreateEmptyPartMap());
        var partsToLoad = new HashSet<SnakePart>();

        partMap.forEach((key, value) -> {
            if (value == null) {
                partsToLoad.add(key);
            }
        });

        for (var missingPart : partsToLoad) {
            var loadedImage = loadImage(createPath(color, missingPart));
            partMap.put(missingPart, loadedImage);
        }

        return partMap;
    }

    private static Map<SnakePart, Image> CreateEmptyPartMap() {
        var map = new HashMap<SnakePart, Image>();
        for (SnakePart part : SnakePart.values()) {
            map.put(part, null);
        }

        return map;
    }

    public static Image[] getRockSprites() {
        if (_rockSprites != null) {
            return _rockSprites;
        }

        _rockSprites = new Image[3];
        _rockSprites[0] = loadImage("src/sprites/rock1.png");
        _rockSprites[1] = loadImage("src/sprites/rock2.png");
        _rockSprites[2] = loadImage("src/sprites/rock3.png");

        return _rockSprites;
    }

    public static Image getRandomRockSprite() {
        var sprites = getRockSprites();
        var index = _random.nextInt(sprites.length);
        return sprites[index];
    }

    public static Image[] getFruitSprites() {
        if (_fruitSprites != null) {
            return _fruitSprites;
        }

        _fruitSprites = new Image[4];
        _fruitSprites[0] = loadImage("src/sprites/apple.png");
        _fruitSprites[1] = loadImage("src/sprites/banana.png");
        _fruitSprites[2] = loadImage("src/sprites/melon.png");
        _fruitSprites[3] = loadImage("src/sprites/pear.png");

        return _fruitSprites;
    }

    public static Image getRandomFruitSprite() {
        var sprites = getFruitSprites();
        var index = _random.nextInt(sprites.length);
        return sprites[index];
    }

    public static Image getFrogSprite() {
        if (_frogSprite != null) {
            return _frogSprite;
        }

        _frogSprite = loadImage("src/sprites/frog.png");
        return _frogSprite;
    }

    public static void DrawSnakeSprite(Graphics g, SnakeColor color, SnakePart part, Point position, Direction direction) {
        var sprite = getSnakeSprite(color, part);
        DrawSprite(g, sprite, position, direction);
    }

    public static void DrawSprite(Graphics g, Image sprite, Point position) {
        DrawSprite(g, sprite, position, Direction.Up);
    }

    public static void DrawSprite(Graphics g, Image sprite, Point position, Direction direction) {
        var g2d = (Graphics2D) g;
        var startingTransform = g2d.getTransform();

        var centerX = position.x * CellSize + CellSize / 2;
        var centerY = position.y * CellSize + CellSize / 2;

        g2d.translate(centerX, centerY);

        if (direction != Direction.Up) {
            g2d.rotate(direction.getRadians());
        }

        g2d.drawImage(sprite, - CellSize / 2, - CellSize / 2, CellSize, CellSize, null);
        g2d.setTransform(startingTransform);
    }

    private static String createPath(SnakeColor color, SnakePart part) {
        return STR."src/sprites/snakes/\{color.getValue()}/\{part.getValue()}.png";
    }

    private static Image loadImage(String path) {
        try {
            return new ImageIcon(path).getImage();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
