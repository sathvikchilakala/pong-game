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

    private Publisher(ServerSocket ss) throws IOException {
        Socket client = ss.accept();
        out = new ObjectOutputStream(client.getOutputStream());
        in  = new ObjectInputStream(client.getInputStream());
    }

    private static Publisher create() throws IOException {
        try (ServerSocket ss = new ServerSocket(PORT)) {
            System.out.println("Publisher listening on port " + PORT + " …");
            return new Publisher(ss);     
        }
    }


    @Override public void run() {
        try {                       
            gameLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gameLoop() throws IOException {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000 / 60); 
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            }
            sendSnapshot();
            receiveSnapshot();
            Game.get().tick();
        }
    }


    private void sendSnapshot() throws IOException {
        out.writeObject(Game.get().snapshot());
        out.flush();
    }

    private void receiveSnapshot() {
        try {
            DataRepository snap = (DataRepository) in.readObject();
            Game.get().applySnapshot(snap);
        } catch (Exception ignored) {}
    }


    public static void main(String[] args) {
        try {
            Publisher pub = Publisher.create();
            new Thread(pub, "Publisher-Thread").start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
