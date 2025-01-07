package HW5.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static HW5.Configuration.EXIT_COMMAND;

public class Client implements Runnable {
    private final Scanner scanner;

    private final String name;
    private final Socket socket;

    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public Client(String host, int port) throws IOException {
        try {
            System.out.print("Enter your name: ");
            scanner = new Scanner(System.in);
            this.name = scanner.nextLine();

            socket = new Socket(host, port);

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("You were connected to the port " + socket.getLocalPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            new Thread(() -> {
                while (socket.isConnected()) {
                    try {
                        System.out.println(bufferedReader.readLine());
                    } catch (IOException e) {
                        if (socket.isClosed()) {
                            break;
                        } else {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }).start();

            while (socket.isConnected()) {
                String message = scanner.nextLine();
                if (!message.equalsIgnoreCase(EXIT_COMMAND)) {
                    bufferedWriter.write(name + ": " + message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } else {
                    bufferedWriter.write(EXIT_COMMAND);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    break;
                }
            }

            socket.close();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
