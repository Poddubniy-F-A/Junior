package HW5.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class Server implements Runnable {
    private final ServerSocket serverSocket;
    private final HashMap<Integer, ClientHandler> clientHandlersById = new HashMap<>();

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while (true) {
                new Thread(new ClientHandler(this, serverSocket.accept())).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dispatchMessage(ClientHandler sender, String message) {
        Integer receiveId = parseId(message);
        if (receiveId != null) {
            ClientHandler receiver = clientHandlersById.get(receiveId);
            if (receiver != sender && receiver != null) {
                receiver.receiveMessage(message);
            }
        } else {
            broadcastMessage(sender, message);
        }
    }

    private Integer parseId(String message) {
        int i = 2;
        while (i < message.length() && message.charAt(i - 2) != ' ') {
            i++;
        }
        int start = i;
        while (i < message.length() && Character.isDigit(message.charAt(i))) {
            i++;
        }

        Integer id = null;
        try {
            id = Integer.parseInt(message.substring(start, i));
        } catch (NumberFormatException _) {
        }

        return id;
    }

    public void subscribe(int id, ClientHandler subscriber) {
        clientHandlersById.put(id, subscriber);
        broadcastMessage(subscriber, "@id" + id + " connected");
    }

    public void unsubscribe(int id) {
        clientHandlersById.remove(id);
        broadcastMessage(null, "@id" + id + " disconnected");
    }

    private void broadcastMessage(ClientHandler sender, String message) {
        for (ClientHandler clientHandler : clientHandlersById.values()) {
            if (clientHandler != sender) {
                clientHandler.receiveMessage(message);
            }
        }
    }
}
