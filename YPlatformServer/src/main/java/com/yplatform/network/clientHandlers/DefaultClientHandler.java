package com.yplatform.network.clientHandlers;

import com.yplatform.network.NetworkHelper;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.yplatform.commands.handlers.LoginCommandHandler;
import com.yplatform.commands.handlers.RegisterCommandHandler;
import com.yplatform.models.User;
import com.yplatform.network.ExitException;
import com.yplatform.services.PostService;
import com.yplatform.utils.ClientDiModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handler with inline processing of requests
 */
public class DefaultClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DefaultClientHandler.class);

    private final Socket clientSocket;
    private final Gson gson;
    private User currentUser;
    private BufferedReader reader;
    private PrintWriter writer;

    public DefaultClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.gson = new Gson();
    }

    @Override
    public void run() {
        try (
                // Create input stream to read data from the client
                BufferedReader runReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // Creating a writer to send a message to client
                PrintWriter runWriter = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            writer = runWriter;
            reader = runReader;

            // The server allows the users to register and login
            writer.println("you can send 'exit' to end connection.");
            writer.println("Enter login or register");
            writer.println("OVER");

            String inputLine;

            Injector injector = Guice.createInjector(new ClientDiModule(writer, reader));

            while ((inputLine = NetworkHelper.readLine(reader, logger)) != null) {
                // loop until login
                if (currentUser == null) {
                    if ("login".equals(inputLine)) {
                        // expected login model
                        var handler = injector.getInstance(LoginCommandHandler.class);
                        currentUser = handler.Handle();
                    } else if ("register".equals(inputLine)) {
                        // register
                        var handler = injector.getInstance(RegisterCommandHandler.class);
                        currentUser = handler.Handle();
                    } else {
                        writer.println("login or register");
                    }
                } else {
                    logger.info("User logged in: " + currentUser.getUsername());
                    break;
                }
            }
            ///////////////////////////////////////////////////////////////////////////////
            // THIS STAGE IS REACHED ONLY IF USER IS LOGGED IN
            // THIS STAGE IS REACHED ONLY IF USER IS LOGGED IN
            // THIS STAGE IS REACHED ONLY IF USER IS LOGGED IN
            ///////////////////////////////////////////////////////////////////////////////
            var postsService = injector.getInstance(PostService.class);
            var userPosts = postsService.getAllPostsByUser(currentUser.getUsername());
            var json = gson.toJson(userPosts);
            writer.println(json);
            //
        } catch (IOException e) {
            logger.warn("read/write error", e);
        } catch (ExitException e) {
            logger.info("Client request to end connection through 'exit' command");
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