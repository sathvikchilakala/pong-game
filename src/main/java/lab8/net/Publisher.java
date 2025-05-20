package lab8.net;

import lab8.model.DataRepository;
import lab8.model.Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server‑side loop: accepts one client, then streams state snapshots
 * and ingests paddle updates at 30 frames per second.
 */
public class Publisher implements Runnable {

    private static final int PORT = 8888;

    private final ObjectOutputStream out;
    private final ObjectInputStream  in;
    private volatile boolean ready;

    public Publisher() throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        Socket client   = ss.accept();
        out   = new ObjectOutputStream(client.getOutputStream());
        in    = new ObjectInputStream(client.getInputStream());
        ready = true;
    }

    public boolean isReady() { return ready; }

    @Override public void run() {
        while (true) {
            try {
                Thread.sleep(1000 / 30);
                send();
                receive();
                Game.get().tick();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private void send() throws IOException {
        out.writeObject(Game.get().snapshot());
        out.flush();
    }

    private void receive() {
        try {
            DataRepository snap = (DataRepository) in.readObject();
            Game.get().applySnapshot(snap);           // includes client paddle
        } catch (Exception e) { /* ignore desync for now */ }
    }
}
