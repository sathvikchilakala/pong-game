package lab8.net;

import lab8.model.DataRepository;
import lab8.model.Game;

import java.io.*;
import java.net.Socket;

/**
 * Client‑side loop: sends local paddle location to the server,
 * receives full world snapshot, applies it, 30 fps.
 */
public class Subscriber implements Runnable {

    private static final String HOST = "localhost";
    private static final int    PORT = 8888;

    private final ObjectOutputStream out;
    private final ObjectInputStream  in;
    private volatile boolean ready;

    public Subscriber() throws IOException {
        Socket s = new Socket(HOST, PORT);
        out  = new ObjectOutputStream(s.getOutputStream());
        in   = new ObjectInputStream(s.getInputStream());
        ready = true;
    }

    public boolean isReady() { return ready; }

    @Override public void run() {
        while (true) {
            try {
                Thread.sleep(1000 / 30);
                receive();
                send();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private void send() throws IOException {
        out.writeObject(Game.get().snapshot());   // local paddle included
        out.flush();
    }

    private void receive() {
        try {
            DataRepository snap = (DataRepository) in.readObject();
            Game.get().applySnapshot(snap);
        } catch (Exception e) { /* ignore for now */ }
    }
}
