package HW5.Server;

import java.io.IOException;

import static HW5.Configuration.CONNECTION_PORT;

public class Main {
    public static void main(String[] args) {
        try {
            new Thread(new Server(CONNECTION_PORT)).start();
        } catch (IOException e) {
            System.err.println("Check connection port value");
            throw new RuntimeException(e);
        }
    }
}
