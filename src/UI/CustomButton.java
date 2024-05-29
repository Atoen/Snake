package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {
    private Color backgroundColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color textColor;
    private int cornerRadius;

    public CustomButton(String text, Color backgroundColor, Color hoverColor, Color pressedColor, Color textColor) {
        super(text);
//        backgroundColor = new Color(70, 130, 180); // Steel Blue
//        hoverColor = new Color(100, 149, 237); // Cornflower Blue
//        pressedColor = new Color(65, 105, 225); // Royal Blue
//        textColor = Color.WHITE;
        cornerRadius = 15;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(textColor);
        setBackground(backgroundColor); // Initial background color

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                setBackground(backgroundColor);
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (getBounds().contains(evt.getPoint())) {
                    setBackground(hoverColor);
                } else {
                    setBackground(backgroundColor);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }
}
