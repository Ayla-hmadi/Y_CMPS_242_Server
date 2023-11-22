package com.yplatform.network;

import com.yplatform.network.clientHandlers.DefaultClientHandler;
import com.yplatform.network.interceptors.IMessageInterceptor;
import com.yplatform.utils.LoggingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
    private static final Logger logger = LoggerFactory.getLogger(TCPServer.class);

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
                LoggingUtil.logInfo(logger,
                        "Client connected: " +
                                clientSocket.getInetAddress().getHostAddress() +
                                " Keep: " +
                                clientSocket.getKeepAlive());

                // Submit the client handling task to the executor service
                executorService.submit(new DefaultClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPortNumber() {
        return portNumber;
    }
}
