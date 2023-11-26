package com.yplatform.network.clientHandlers;

import com.yplatform.commands.*;
import com.yplatform.models.Following;
import com.yplatform.models.Reaction;
import com.yplatform.network.NetworkHelper;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.yplatform.commands.handlers.LoginCommandHandler;
import com.yplatform.commands.handlers.RegisterCommandHandler;
import com.yplatform.models.User;
import com.yplatform.network.ExitException;
import com.yplatform.services.AuthenticationService;
import com.yplatform.services.FollowingService;
import com.yplatform.services.PostService;
import com.yplatform.services.ReactionService;
import com.yplatform.utils.ClientDiModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler with inline processing of requests
 */
public class DefaultClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DefaultClientHandler.class);

    private final Map<String, Class<? extends ICommand>> commands = new HashMap<>();


    private final Socket clientSocket;
    private final Gson gson;
    private User currentUser;
    private BufferedReader reader;
    private PrintWriter writer;

    public DefaultClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.gson = new Gson();
        //
        commands.put("my posts", QueryMyPostsCommand.class);
        commands.put("login", LoginCommand.class);
        commands.put("register", RegisterCommand.class);
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
                    if (CommandNames.Login.equals(inputLine)) {
                        // expected login model
                        var handler = injector.getInstance(LoginCommandHandler.class);
                        currentUser = handler.Handle();
                    } else if (CommandNames.Register.equals(inputLine)) {
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
            injector.getInstance(AuthenticationService.class).setCurrentUser(currentUser);

            //////////////////////////////////////////////////
            // THIS STAGE IS REACHED ONLY IF USER IS LOGGED IN
            // THIS STAGE IS REACHED ONLY IF USER IS LOGGED IN
            // THIS STAGE IS REACHED ONLY IF USER IS LOGGED IN
            //////////////////////////////////////////////////


            ////////////////////
            // WAIT FOR COMMANDS
            // WAIT FOR COMMANDS
            // WAIT FOR COMMANDS
            ////////////////////
            var postsService = injector.getInstance(PostService.class);
            var reactionService = injector.getInstance(ReactionService.class);
            var followService = injector.getInstance(FollowingService.class);

            //
            loop:
            while ((inputLine = NetworkHelper.readLine(reader, logger)) != null) {
                switch (inputLine) {
                    case CommandNames.Exit:
                        logger.info("Client 'exit' command received. Exiting now...");
                        break loop;
                    case CommandNames.AddPost: {
                        var content = NetworkHelper.readLine(reader, logger);
                        postsService.addPost(content, currentUser.getUsername());
                        break;
                    }
                    case CommandNames.React: {
                        inputLine = NetworkHelper.readLine(reader, logger);
                        var command = gson.fromJson(inputLine, AddReactionCommand.class);
                        var reaction = new Reaction(
                                command.getPostId(),
                                currentUser.getUsername(),
                                command.getReactionType());
                        reactionService.addReaction(reaction);
                        break;
                    }
                    case CommandNames.Follow: {
                        var followId = NetworkHelper.readLine(reader, logger);

                        var follow = new Following(
                                currentUser.getUsername(),
                                followId
                        );
                        followService.followUser(follow);
                        break;
                    }
                    case CommandNames.MyPosts: {
                        var response = postsService.getAllPostsByUser(currentUser.getUsername());
                        writer.println(gson.toJson(response));
                        break;
                    }
                    case CommandNames.MyInterests: {
                        var response = postsService.getRandomPostsFromNonFollowedUsers(currentUser.getUsername(), 10);
                        writer.println(gson.toJson(response));
                        break;
                    }
                    default:
                        // ignore !?
                        break;
                }

            }
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
