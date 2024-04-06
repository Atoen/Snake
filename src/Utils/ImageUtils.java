package Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {
    public static Image tintImage(Image originalImage, Color color) {
        var width = originalImage.getWidth(null);
        var height = originalImage.getHeight(null);

        var tintedImg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        var g2d = tintedImg.createGraphics();

        g2d.setXORMode(color);
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.fillRect(0, 0, width, height);

        g2d.dispose();

        return tintedImg;
    }
}
