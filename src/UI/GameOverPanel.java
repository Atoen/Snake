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
        setLayout(new GridBagLayout()); // Use GridBagLayout to center the buttons
        setPreferredSize(new Dimension(800, 600));

        try {
            backgroundImage = ImageIO.read(new File("src\\Sprites\\skull.jpg"));
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
        buttonPanelGO.setOpaque(false); // Make the panel transparent
        buttonPanelGO.setLayout(new GridLayout(2, 1, 10, 10)); // 10px vertical gap between buttons
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
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
