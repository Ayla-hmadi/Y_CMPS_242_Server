package com.yplatform.network;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class TCPServer {
    int portNumber;

    public TCPServer(int portNumber) {
        this.portNumber = portNumber;
    }


    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void start() {
        try (
                // Creating listener
                ServerSocket serverSocket = new ServerSocket(portNumber)
        ) {
            System.out.println("Server listening on port " + portNumber);

            while (true) {
                // Accept incoming connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Submit the client handling task to the executor service
                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPortNumber() {
        return portNumber;
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    // Create input stream to read data from the client
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    System.out.println("Received from " + clientSocket.getInetAddress().getHostAddress() + ": " + inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
