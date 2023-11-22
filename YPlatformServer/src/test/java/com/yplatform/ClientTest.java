package com.yplatform;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.yplatform.commands.LoginCommand;
import com.yplatform.commands.RegisterCommand;
import com.yplatform.network.Keywords;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
    public void testRegister() throws IOException {
        registerNewUser();
    }

    @Test
    public void testPostsLogin() throws IOException {
        registerNewUser();
    }

    public String registerNewUser() throws IOException {
        String serverResponse;
        while ((serverResponse = readServer(reader)) != null) {
            if (serverResponse.equals("OVER")) {
                break;
            }
        }

        printWriter.println("register");
        // send login dto
        Faker faker = new Faker();

        RegisterCommand dto = new RegisterCommand();
        dto.setName(faker.name().fullName());
        dto.setEmail(faker.internet().emailAddress());
        dto.setUsername(faker.name().username());
        dto.setPassword(faker.internet().password());
        dto.setAddress(faker.address().fullAddress());
        var gson = new Gson();
        var json = gson.toJson(dto);
        System.out.println(json);
        printWriter.println(json);

        serverResponse = readServer(reader);
        Assertions.assertEquals(Keywords.CommandSuccess, serverResponse);
        return dto.getUsername();
    }


    @Test
    public void testValidLogin() throws IOException {
        System.out.println("Connected to the server.");
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
        var loginDto = new LoginCommand("oz", "oz");
        var gson = new Gson();
        var json = gson.toJson(loginDto);
        printWriter.println(json);

        serverResponse = readServer(reader);
        Assertions.assertEquals(Keywords.CommandSuccess, serverResponse);
    }

    private static String readServer(BufferedReader reader) throws IOException {
        String serverResponse;
        serverResponse = reader.readLine();
        // server
        System.out.println("[Server Response] " + serverResponse);
        return serverResponse;
    }
}
