package chatapp;

import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_IP = "127.0.0.1"; // Localhost
    private static final int SERVER_PORT = 5002;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to chat server");

            // Thread for receiving messages
            Thread listener = new Thread(() -> {
                try {
                    String line;
                    while ((line = serverIn.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            listener.start();

            // Main thread handles sending messages
            String input;
            while ((input = userIn.readLine()) != null) {
                serverOut.println(input);
                if ("bye".equalsIgnoreCase(input.trim())) break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

