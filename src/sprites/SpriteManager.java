package sprites;

import Game.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static UI.GamePanel.CellSize;

public class SpriteManager {

    private static Image _playerBodySprite;
    private static Image _playerTailSprite;
    private static Image _playerHead0Sprite;
    private static Image _playerHead1Sprite;
    private static Image[] _playerBodyTurnSprites;

    private static Image[] _rockSprites;
    private static Image[] _fruitSprites;

    private static Image _frogSprite;

    private static final Random _random = new Random();

    public static Image getPlayerBodySprite() {
        if (_playerBodySprite != null) {
            return _playerBodySprite;
        }

        _playerBodySprite = loadImage("src/sprites/body.png");
        return _playerBodySprite;
    }

    public static Image getPlayerTailSprite() {
        if (_playerTailSprite != null) {
            return _playerTailSprite;
        }

        _playerTailSprite = loadImage("src/sprites/tail.png");
        return _playerTailSprite;
    }

    public static Image getPlayerHead0Sprite() {
        if (_playerHead0Sprite != null) {
            return _playerHead0Sprite;
        }

        _playerHead0Sprite = loadImage("src/sprites/head0.png");
        return _playerHead0Sprite;
    }

    public static Image getPlayerHead1Sprite() {
        if (_playerHead1Sprite != null) {
            return _playerHead1Sprite;
        }

        _playerHead1Sprite = loadImage("src/sprites/head1.png");
        return _playerHead1Sprite;
    }

    public static Image[] getPlayerBodyTurnSprites() {
        if (_playerBodyTurnSprites != null) {
            return _playerBodyTurnSprites;
        }

        _playerBodyTurnSprites = new Image[4];
        _playerBodyTurnSprites[0] = loadImage("src/sprites/bodyturn1.png");
        _playerBodyTurnSprites[1] = loadImage("src/sprites/bodyturn2.png");
        _playerBodyTurnSprites[2] = loadImage("src/sprites/bodyturn3.png");
        _playerBodyTurnSprites[3] = loadImage("src/sprites/bodyturn4.png");

        return _playerBodyTurnSprites;
    }

    public static Image[] getRockSprites() {
        if (_rockSprites != null) {
            return _rockSprites;
        }


        return _rockSprites;
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

    public static void DrawSprite(Graphics g, Image sprite, Point position) {
        DrawSprite(g, sprite, position, Direction.Up);
    }

    public static void DrawSprite(Graphics g, Image sprite, Point position, Direction direction) {
        var g2d = (Graphics2D) g;
        var startingTransform = g2d.getTransform();

        var centerX = position.x * CellSize +  CellSize / 2;
        var centerY = position.y *  CellSize +  CellSize / 2;

        g2d.translate(centerX, centerY);
        g2d.rotate(direction.getRadians());

        g2d.drawImage(sprite, - CellSize / 2, - CellSize / 2,  CellSize,  CellSize, null);
        g2d.setTransform(startingTransform);
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
