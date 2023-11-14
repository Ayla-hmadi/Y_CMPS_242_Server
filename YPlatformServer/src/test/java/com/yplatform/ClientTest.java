package com.yplatform;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.*;
import java.net.*;

import org.junit.runner.RunWith;

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

    /**
     * Rigourous Test :-)
     */
    public void testConnectToServer() throws IOException {
        final String serverHost = "localhost"; // Replace with your server's IP or hostname
        final int serverPort = 43211; // Replace with your server's port number


        try (


                // Create a socket to connect to the server
                Socket socket = new Socket(serverHost, serverPort)
        ) {


            // Create input and output streams for communication with the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Create a BufferedReader to read input from the user
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to the server.");

            // Read user input and send it to the server
            String userInputLine = "Hello from client!!!";
            out.println(userInputLine);
            out.println("Send me some stuff!");

            // Read and print the server's response
            String serverResponse = in.readLine();
            System.out.println("Server response: " + serverResponse);

            while ((userInputLine = userInput.readLine()) != null) {
                out.println(userInputLine);

                // Read and print the server's response
                serverResponse = in.readLine();
                System.out.println("Server response: " + serverResponse);
            }
        }

        assertTrue(true);
    }
}
