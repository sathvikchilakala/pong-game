package lab8.ui;

import lab8.model.Field;
import lab8.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;



public class PongPanel extends JPanel implements MouseMotionListener {

    public PongPanel() {
        setPreferredSize(new Dimension(Field.WIDTH, Field.HEIGHT));
        setBackground(new Color(172, 248, 199));
        addMouseMotionListener(this);

        new Timer(1000 / 30, e -> repaint()).start();
    }


    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawCenterLine(g);
        drawPaddles(g);
        drawBall(g);
    }


    private void drawCenterLine(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        float[] dash = {10f};
        g2.setStroke(new BasicStroke(1f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10f, dash, 0f));
        g2.setColor(Color.WHITE);
        g2.drawLine(Field.WIDTH / 2, 0, Field.WIDTH / 2, Field.HEIGHT);
        g2.setStroke(new BasicStroke());
    }

    private void drawPaddles(Graphics g) {
        var s = Game.get().getState();
        g.setColor(Color.WHITE);
        g.fillRect(Field.SERVER_X,
                   s.getServerPaddleY(),
                   Field.PADDLE_WIDTH,
                   Field.PADDLE_HEIGHT);
        g.fillRect(Field.CLIENT_X,
                   s.getClientPaddleY(),
                   Field.PADDLE_WIDTH,
                   Field.PADDLE_HEIGHT);
    }

    private void drawBall(Graphics g) {
        var s = Game.get().getState();
        g.setColor(Color.DARK_GRAY);
        g.fillOval(s.getBallX() - 5, s.getBallY() - 5, 10, 10);
    }


    @Override public void mouseMoved(MouseEvent e) {
        Game.get().getState().setClientPaddleY(e.getY());
    }
    @Override public void mouseDragged(MouseEvent e) { /* ignore */ }
}
