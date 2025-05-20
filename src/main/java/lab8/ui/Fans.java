package lab8.ui;

import lab8.model.Field;

import java.awt.*;

/**
 * Decorative “fans” overlay based on score milestones.
 * Hook this into your Swing panel after drawing the game.
 */
public class Fans {

    public void paint(Graphics g, int leftScore, int rightScore) {
        Graphics2D g2 = (Graphics2D) g.create();

        if (leftScore == 3 || rightScore == 3) {
            g2.setColor(Color.YELLOW);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
            g2.drawString("⭐  3 POINTS!  ⭐",
                          Field.WIDTH / 2f - 100, 40);
        }

        if (leftScore >= 5 || rightScore >= 5) {
            g2.setColor(Color.PINK);
            for (int i = 0; i < 180; i++) {
                int x = (int) (Math.random() * Field.WIDTH);
                int y = (int) (Math.random() * Field.HEIGHT);
                g2.fillOval(x, y, 4, 4);
            }
        }
        g2.dispose();
    }
}
