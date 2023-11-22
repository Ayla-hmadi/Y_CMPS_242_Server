package com.yplatform;

import com.google.gson.Gson;
import com.yplatform.shared.dtos.LoginDto;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.*;
import java.net.*;
import java.util.EventListener;

import org.junit.runner.RunWith;

import java.util.Scanner;

/**
 * Unit test for simple App.
 */
public class ClientTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ClientTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ClientTest.class);
    }

    public void testLogin() throws IOException {
        final String serverHost = "localhost"; // Replace with your server's IP or hostname
        final int serverPort = 43211; // Replace with your server's port number


        try (
                // Create a socket to connect to the server
                Socket socket = new Socket(serverHost, serverPort);
                // Create input and output streams for communication with the server
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // Create a BufferedReader to read input from the user

            System.out.println("Connected to the server.");

            // Read user input and send it to the server
            String userInputLine = "Hello from client!!!";
            printWriter.println(userInputLine);
            printWriter.println("Send me some stuff!");

            // Read and print the server's response
            String serverResponse = reader.readLine();
            System.out.println("Server response: " + serverResponse);

            while ((serverResponse = readServer(reader)) != null) {
                if (serverResponse.equals("OVER")) {
                    break;
                }
            }

            // login
            printWriter.println("login");
            // send login dto
            var loginDto = new LoginDto("oz", "oz");
            var gson = new Gson();
            var json = gson.toJson(loginDto);
            printWriter.println(json);

            while ((serverResponse = readServer(reader)) != null) {
                if (serverResponse.equals("OVER")) {
                    break;
                }
            }
        }

        assertTrue(true);
    }

    private static String readServer(BufferedReader reader) throws IOException {
        String serverResponse;
        serverResponse = reader.readLine();
        // server
        System.out.println("[Server Response] " + serverResponse);
        return serverResponse;
    }
}
