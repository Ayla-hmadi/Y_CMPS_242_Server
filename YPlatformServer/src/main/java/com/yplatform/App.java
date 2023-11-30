package com.yplatform;

import com.yplatform.database.DatabaseInitializer;
import com.yplatform.network.TCPServer;
import com.yplatform.network.UDPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    static final int UdpPort = 44444;

    public static void main(String[] args) {

        // This function will also seed the database, so when finalizing project please remove the function seedData() inside initializeDatabaseIfNeeded()
        DatabaseInitializer.initializeDatabaseIfNeeded();

        if (args.length == 0) {
            logger.error("TCP Server failed to start.. Invalid port! ");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);

        Thread tcpThread = new Thread(() -> {
            logger.info("[TCP Server] Initializing...");
            TCPServer server = new TCPServer(portNumber);
            server.start();
            logger.info("[TCP Server] Server started on port " + server.getPortNumber());
        });

        Thread udpThread = new Thread(() -> {
            logger.info("[UDP Server] Initializing...");
            var udpServer = new UDPServer(UdpPort, portNumber);
            udpServer.start();
            logger.info("[UDP Server] Server started on port " + UdpPort);
        });

        tcpThread.start();
        udpThread.start();

    }
}
