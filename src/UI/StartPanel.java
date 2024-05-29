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
        setPreferredSize(new Dimension(800, 450));

        try {
            backgroundImage = ImageIO.read(new File("src\\Sprites\\snake.png"));
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

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // 10px vertical gap between buttons
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        // Use GridBagConstraints to center the buttonPanel
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

}
