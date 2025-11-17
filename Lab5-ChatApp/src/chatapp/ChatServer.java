package chatapp;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

// Multithreaded Chat Server
public class ChatServer {
    private static final int PORT = 5002;
    private static final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) {
        System.out.println("Server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    static void removeClient(ClientHandler handler) {
        clients.remove(handler);
        System.out.println("Client disconnected: " + handler.getClientName());
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName = "Guest";

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public String getClientName() {
        return clientName;
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Enter your name:");
            clientName = in.readLine();
            if (clientName == null || clientName.isEmpty()) {
                clientName = "Guest";
            }

            out.println("Welcome " + clientName + "! Type 'bye' to exit.");
            ChatServer.broadcast(clientName + " joined the chat.", this);

            String message;
            while ((message = in.readLine()) != null) {
                if ("bye".equalsIgnoreCase(message.trim())) {
                    out.println("Goodbye!");
                    break;
                }
                ChatServer.broadcast(clientName + ": " + message, this);
            }
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        } finally {
            ChatServer.removeClient(this);
            ChatServer.broadcast(clientName + " left the chat.", this);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(String message) {
        out.println(message);
    }
}
