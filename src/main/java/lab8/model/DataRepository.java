package lab8.model;

import java.io.Serializable;


public class DataRepository implements Serializable, Cloneable {

    private int ballX, ballY;
    private int serverY, clientY;
    private int direction;
    private int whoAmI;

    public DataRepository(int bx, int by,
                          int serverY, int clientY,
                          int dir, int who) {
        this.ballX   = bx;
        this.ballY   = by;
        this.serverY = serverY;
        this.clientY = clientY;
        this.direction = dir;
        this.whoAmI  = who;
    }

    public DataRepository() { this(0,0,0,0,Field.RIGHT,Field.SERVER); }

    public int getBallX()         { return ballX; }
    public int getBallY()         { return ballY; }
    public int getServerPaddleY() { return serverY; }
    public int getClientPaddleY() { return clientY; }
    public int getDirection()     { return direction; }
    public int getWhoAmI()        { return whoAmI; }

    public void setBall(int x,int y)  { ballX = x; ballY = y; }
    public void setServerPaddleY(int y){ serverY = y; }
    public void setClientPaddleY(int y){ clientY = y; }
    public void setDirection(int d)    { direction = d; }
    public void setWhoAmI(int who)     { whoAmI = who; }

    @Override public DataRepository clone() {
        return new DataRepository(ballX, ballY, serverY, clientY, direction, whoAmI);
    }

    @Override public String toString() {
        return "Ball(" + ballX + "," + ballY + ") "
             + "S‑Pad:" + serverY + " C‑Pad:" + clientY
             + " Dir:" + direction + " Me:" + whoAmI;
    }
}
