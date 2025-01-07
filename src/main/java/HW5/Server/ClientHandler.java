package HW5.Server;

import java.io.*;
import java.net.Socket;

import static HW5.Configuration.EXIT_COMMAND;

public class ClientHandler implements Runnable {
    private static int examples = 0;

    private final int id;

    private final Server server;
    private final Socket socket;

    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public ClientHandler(Server server, Socket socket) {
        try {
            id = examples++;
            this.server = server;
            this.socket = socket;

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("New connection on port " + socket.getPort() + " was registered as " + id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            server.subscribe(id, this);
            while (socket.isConnected()) {
                String message = bufferedReader.readLine();
                if (!message.equals(EXIT_COMMAND)) {
                    server.dispatchMessage(this, "(@id" + id + ")" + message);
                } else {
                    server.unsubscribe(id);
                    break;
                }
            }

            socket.close();
            bufferedReader.close();
            bufferedWriter.close();

            System.out.println("Connection on port " + socket.getPort() + " closed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveMessage(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
