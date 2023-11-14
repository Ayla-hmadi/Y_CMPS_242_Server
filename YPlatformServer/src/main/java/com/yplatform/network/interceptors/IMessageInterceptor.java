package com.yplatform.network.interceptors;

import java.io.BufferedReader;
import java.io.IOException;

public interface IMessageInterceptor {
    /**
     * intercepts the input with a chance to modify any part of it
     *
     * @param input
     * @return the modified input. if null is returned then further processing is halted
     */
    String interceptIncoming(String input);

    /**
     * intercepts the output with a chance to modify any part of it
     *
     * @param output
     * @return
     */
    String interceptOutgoing(String output);

//    private static void handleLoginCommand(BufferedReader reader, PrintWriter writer) throws IOException {
//        // Read the username and password from the client
//        String username = reader.readLine();
//        String password = reader.readLine();
//
//        // Check the credentials (This is a simple example, replace it with your authentication logic)
//        if ("user".equals(username) && "password".equals(password)) {
//            writer.println("Login successful");
//        } else {
//            writer.println("Login failed");
//        }
//    }
}

