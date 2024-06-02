package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StartPanel extends JPanel {
    private final MainFrameListener _listener;
    private BufferedImage backgroundImage;

    public StartPanel(MainFrameListener listener) {
        _listener = listener;

        setLayout(new GridBagLayout());

        setPreferredSize(new Dimension(800, 500));

        try {
            BufferedImage originalImage = ImageIO.read(new File("src\\Sprites\\snake.png"));
            backgroundImage = resizeImage(originalImage, 800, 500);

        } catch (IOException e) {
            e.printStackTrace();
        }

        var startButton = new CustomButton("Start Game",Color.lightGray, Color.darkGray, Color.GREEN, Color.BLACK);
        startButton.setPreferredSize(new Dimension(150, 50));
        var exitButton = new CustomButton("Exit",Color.lightGray, Color.darkGray, Color.red, Color.BLACK);
        exitButton.setPreferredSize(new Dimension(150, 50));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _listener.onStartGame();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _listener.onExit();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        add(buttonPanel, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
