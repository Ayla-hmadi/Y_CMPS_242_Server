package com.yplatform;

import com.yplatform.database.DatabaseInitializer;
import com.yplatform.network.TCPServer;

public class App {
    public static void main(String[] args) {

        // This function will also seed the database, so when finalizing project please remove the function seedData() inside initializeDatabaseIfNeeded()
        DatabaseInitializer.initializeDatabaseIfNeeded();

        if (args.length == 0) {
            System.out.println("TCP Server failed to start.. Invalid port! ");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);

        System.out.println("[TCP Server] Initializing...");
        TCPServer server = new TCPServer(portNumber);
        server.start();
        System.out.println("Server started on port " + server.getPortNumber());
    }
}
