package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The CustomButton class extends JButton to create custom-styled buttons.
 * It provides functionality for buttons with rounded corners, hover effects,
 * and pressed effects.
 */
public class CustomButton extends JButton {
    private final int cornerRadius;

    /**
     * Constructs a CustomButton with specified text and colors.
     * @param text           the text to be displayed on the button
     * @param backgroundColor the background color of the button
     * @param hoverColor     the color of the button when the mouse hovers over it
     * @param pressedColor   the color of the button when it is pressed
     * @param textColor      the color of the text on the button
     */
    public CustomButton(String text, Color backgroundColor, Color hoverColor, Color pressedColor, Color textColor) {
        super(text);
        cornerRadius = 15;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(textColor);
        setBackground(backgroundColor);

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

    /**
     * Overrides the paintComponent method to draw the rounded rectangle background.
     * @param g the Graphics context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(g);
        g2.dispose();
    }

    /**
     * Overrides the setBackground method to ensure proper repainting.
     * @param bg the background color to be set
     */
    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }
}

