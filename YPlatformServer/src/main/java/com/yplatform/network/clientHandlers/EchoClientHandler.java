package com.yplatform.network.clientHandlers;

import java.io.*;
import java.net.Socket;
import java.sql.PseudoColumnUsage;

/**
 * Simply echos whatever the clients send to the server
 */
public class EchoClientHandler implements Runnable {
    private final Socket clientSocket;

    public EchoClientHandler(Socket clientSocket) {
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
                System.out.println("Client socket closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}