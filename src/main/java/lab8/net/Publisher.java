package lab8.net;

import lab8.model.DataRepository;
import lab8.model.Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


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
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000 / 30);      // 30 fps
                send();
                receive();
                Game.get().tick();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void send() throws IOException {
        out.writeObject(Game.get().snapshot());
        out.flush();
    }

    private void receive() {
        try {
            DataRepository snap = (DataRepository) in.readObject();
            Game.get().applySnapshot(snap);
        } catch (Exception ignored) {}
    }


    public static void main(String[] args) {
        try {
            Publisher pub = new Publisher();
            if (pub.isReady()) {
                new Thread(pub, "Publisher-Thread").start();
                System.out.println("Publisher running on port " + PORT);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
