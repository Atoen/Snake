package sprites;

import javax.swing.*;
import java.awt.*;

public class SpriteManager {

    private static Image _playerBodySprite;
    private static Image _playerTailSprite;
    private static Image _playerHead0Sprite;
    private static Image _playerHead1Sprite;
    private static Image[] _playerBodyTurnSprites;

    private static Image[] _rockSprites;
    private static Image[] _fruitSprites;

    private static Image _frogSprite;

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

        return _fruitSprites;
    }

    public static Image getFrogSprite() {
        if (_frogSprite != null) {
            return _frogSprite;
        }

        _frogSprite = loadImage("src/sprites/frog.png");
        return _frogSprite;
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
