package lab8.ui;

import lab8.net.Subscriber;

import javax.swing.*;
import java.io.IOException;


public class ClientApp {

    public static void main(String[] args) {

        try {
            Subscriber sub = new Subscriber();
            if (sub.isReady()) {
                new Thread(sub, "Subscriber-Thread").start();
                System.out.println("Subscriber networking started");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Lab8 Pong – Client");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(new PongPanel());
            f.pack();
            f.setLocation(850, 20);  
            f.setResizable(false);
            f.setVisible(true);
        });
    }
}
