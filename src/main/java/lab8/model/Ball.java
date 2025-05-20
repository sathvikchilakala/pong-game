package lab8.model;

import java.io.Serializable;

/** Ball position + simple horizontal motion. */
public class Ball implements Serializable {

    private int x, y;       // centre coordinates
    private int dx = 10;    // horizontal velocity (pixels / tick)
    private int dy = 0;     // vertical velocity (unused for now)

    public Ball(int cx, int cy) { x = cx; y = cy; }

    public void move() { x += dx; y += dy; }

    public void reverseX() { dx = -dx; }

    public int getX() { return x; }
    public int getY() { return y; }

    public void reset(int cx, int cy) { x = cx; y = cy; }
}
