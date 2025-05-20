package lab8.model;

import java.io.Serializable;


public class Ball implements Serializable {

    private int x, y;      
    private int dx = 10;   
    private int dy = 0;  

    public Ball(int cx, int cy) { x = cx; y = cy; }

    public void move() { x += dx; y += dy; }

    public void reverseX() { dx = -dx; }

    public int getX() { return x; }
    public int getY() { return y; }

    public void reset(int cx, int cy) { x = cx; y = cy; }
}
