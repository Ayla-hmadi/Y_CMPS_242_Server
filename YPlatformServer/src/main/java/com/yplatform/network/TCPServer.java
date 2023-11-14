package com.yplatform.network;

import com.yplatform.network.clientHandlers.EchoClientHandler;
import com.yplatform.network.interceptors.IMessageInterceptor;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class TCPServer {
    int portNumber;

    public TCPServer(int portNumber) {
        this.portNumber = portNumber;
    }


    private final ExecutorService executorService = Executors.newCachedThreadPool();

    ArrayList<IMessageInterceptor> messageInterceptors = new ArrayList<>();

    public void addInterceptor(IMessageInterceptor messageInterceptor) {
        if (messageInterceptor == null) {
            throw new IllegalArgumentException("Interceptor is missing");
        }
        messageInterceptors.add(messageInterceptor);
    }

    public void start() {
        try (
                // Creating listener
                ServerSocket serverSocket = new ServerSocket(portNumber)
        ) {
            System.out.println("Server listening on port " + portNumber);

            while (true) {
                // Accept incoming connections
                Socket clientSocket = serverSocket.accept();
                System.out.println(
                        "Client connected: " +
                                clientSocket.getInetAddress().getHostAddress() +
                                " Keep: " +
                                clientSocket.getKeepAlive());

                // Submit the client handling task to the executor service
                executorService.submit(new EchoClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPortNumber() {
        return portNumber;
    }
}
