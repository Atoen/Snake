package Sprites;

import Game.Direction;
import Game.SnakeColor;
import Game.SnakePart;
import Utils.SharedRandom;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static UI.GameCanvas.CellSize;

/**
 * The SpriteManager class manages the loading and retrieval of various sprites used in the game.
 */
public class SpriteManager {

    private static final Map<SnakeColor, Map<SnakePart, Image>> _snakeSprites = new HashMap<>();
    private static Image[] _rockSprites;
    private static Image[] _fruitSprites;
    private static Image _frogSprite;

    /**
     * Retrieves the sprite image for the specified snake color and part.
     *
     * @param color The color of the snake.
     * @param part  The part of the snake (head, body, tail).
     * @return The Image of the snake part.
     */
    public static Image getSnakeSprite(SnakeColor color, SnakePart part) {
        var partMap = _snakeSprites.computeIfAbsent(color, _ -> CreateEmptyPartMap());
        var image = partMap.get(part);

        if (image != null) {
            return image;
        }

        image = loadImage(createSnakeSpritePath(color, part));
        partMap.put(part, image);

        return image;
    }

    /**
     * Creates an empty map for snake parts with null images.
     *
     * @return A map with SnakePart keys and null Image values.
     */
    private static Map<SnakePart, Image> CreateEmptyPartMap() {
        var map = new HashMap<SnakePart, Image>();
        for (SnakePart part : SnakePart.values()) {
            map.put(part, null);
        }
        return map;
    }

    /**
     * Retrieves an array of rock sprites.
     *
     * @return An array of rock Images.
     */
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

    /**
     * Retrieves a random rock sprite.
     *
     * @return A random rock Image.
     */
    public static Image getRandomRockSprite() {
        var sprites = getRockSprites();
        var index = SharedRandom.nextInt(sprites.length);
        return sprites[index];
    }

    /**
     * Retrieves an array of fruit sprites.
     *
     * @return An array of fruit Images.
     */
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

    /**
     * Retrieves a random fruit sprite.
     *
     * @return A random fruit Image.
     */
    public static Image getRandomFruitSprite() {
        var sprites = getFruitSprites();
        var index = SharedRandom.nextInt(sprites.length);
        return sprites[index];
    }

    /**
     * Retrieves the frog sprite.
     *
     * @return The frog Image.
     */
    public static Image getFrogSprite() {
        if (_frogSprite != null) {
            return _frogSprite;
        }

        _frogSprite = loadImage("src/sprites/frog.png");
        return _frogSprite;
    }

    /**
     * Draws the specified snake sprite at the given position with the specified direction.
     *
     * @param g         The Graphics context to draw on.
     * @param color     The color of the snake.
     * @param part      The part of the snake to draw.
     * @param position  The position to draw the sprite.
     * @param direction The direction the sprite should face.
     */
    public static void DrawSnakeSprite(Graphics g, SnakeColor color, SnakePart part, Point position, Direction direction) {
        var sprite = getSnakeSprite(color, part);
        DrawSprite(g, sprite, position, direction);
    }

    /**
     * Draws the specified sprite at the given position with the default direction (up).
     *
     * @param g        The Graphics context to draw on.
     * @param sprite   The Image to draw.
     * @param position The position to draw the sprite.
     */
    public static void DrawSprite(Graphics g, Image sprite, Point position) {
        DrawSprite(g, sprite, position, Direction.Up);
    }

    /**
     * Draws the specified sprite at the given position with the specified direction.
     *
     * @param g         The Graphics context to draw on.
     * @param sprite    The Image to draw.
     * @param position  The position to draw the sprite.
     * @param direction The direction the sprite should face.
     */
    public static void DrawSprite(Graphics g, Image sprite, Point position, Direction direction) {
        var g2d = (Graphics2D) g;
        var startingTransform = g2d.getTransform();

        var centerX = position.x * CellSize + CellSize / 2;
        var centerY = position.y * CellSize + CellSize / 2;

        g2d.translate(centerX, centerY);

        if (direction != Direction.Up) {
            g2d.rotate(direction.getRadians());
        }

        g2d.drawImage(sprite, -CellSize / 2, -CellSize / 2, CellSize, CellSize, null);
        g2d.setTransform(startingTransform);
    }

    /**
     * Creates the file path for a snake sprite based on its color and part.
     *
     * @param color The color of the snake.
     * @param part  The part of the snake.
     * @return The file path as a String.
     */
    private static String createSnakeSpritePath(SnakeColor color, SnakePart part) {
        return "src/sprites/snakes/" + color.getValue() + '/' + part.getValue() + ".png";
    }

    /**
     * Loads an image from the specified file path.
     *
     * @param path The file path to load the image from.
     * @return The loaded Image, or null if loading failed.
     */
    private static Image loadImage(String path) {
        try {
            return new ImageIcon(path).getImage();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
