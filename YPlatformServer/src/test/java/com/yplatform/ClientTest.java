package com.yplatform;

import com.google.gson.Gson;
import com.yplatform.shared.dtos.LoginDto;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.*;

/**
 * Unit test for simple App.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientTest {
    private PrintWriter printWriter;
    private BufferedReader reader;
    private Socket socket;

    @BeforeAll
    public static void setUpClass() {
        // Code to be executed once before any test in the class
        System.out.println("Setting up the class...");
    }


    @BeforeEach
    public void setUp() throws IOException {
        System.out.println("Setting up for the test...");

        final String serverHost = "localhost"; // Replace with your server's IP or hostname
        final int serverPort = 43211; // Replace with your server's port number

        // Create a socket to connect to the server
        socket = new Socket(serverHost, serverPort);
        // Create input and output streams for communication with the server
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @AfterAll
    public static void tearDownClass() {
        // Code to be executed once after all tests in the class
        System.out.println("Tearing down the class...");
    }

    @AfterEach
    public void tearDown() throws IOException {
        System.out.println("Tearing down after the test...");

        // Close the socket and associated streams
        printWriter.close();
        reader.close();
        socket.close();
    }

    @Test
    public void testValidLogin() throws IOException {

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
        Assertions.assertTrue(true);
    }

    private static String readServer(BufferedReader reader) throws IOException {
        String serverResponse;
        serverResponse = reader.readLine();
        // server
        System.out.println("[Server Response] " + serverResponse);
        return serverResponse;
    }
}
