package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameOverPanel extends JPanel {
    private BufferedImage backgroundImage;

    public GameOverPanel(MainFrameListener listener) {

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(800, 500));

        try {
            BufferedImage originalImage = ImageIO.read(new File("src/Sprites/skull.jpg"));
            backgroundImage = resizeImage(originalImage, 800, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }

        var _restartButton = new CustomButton("Restart", Color.lightGray, Color.darkGray, Color.GREEN, Color.BLACK);
        _restartButton.setPreferredSize(new Dimension(180, 60));
        _restartButton.addActionListener(e -> listener.onRestartGame());

        var _quitButton = new CustomButton("Quit", Color.lightGray, Color.darkGray, Color.red, Color.BLACK);
        _quitButton.setPreferredSize(new Dimension(180, 60));
        _quitButton.addActionListener(e -> listener.onExit());

        JPanel buttonPanelGO = new JPanel();

        buttonPanelGO.setOpaque(false);
        buttonPanelGO.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanelGO.add(_restartButton);
        buttonPanelGO.add(_quitButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        add(buttonPanelGO, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            int x = (getWidth() - backgroundImage.getWidth()) / 2;
            int y = (getHeight() - backgroundImage.getHeight()) / 2;
            g.drawImage(backgroundImage, x, y, this);
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        double aspectRatio = (double) originalWidth / originalHeight;

        int newWidth = targetWidth;
        int newHeight = targetHeight;

        if (aspectRatio > 1) {
            newHeight = (int) (targetWidth / aspectRatio);
        } else {
            newWidth = (int) (targetHeight * aspectRatio);
        }

        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        return resizedImage;

    }
}
