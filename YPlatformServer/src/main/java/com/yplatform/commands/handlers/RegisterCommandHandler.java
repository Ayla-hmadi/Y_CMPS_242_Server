package com.yplatform.commands.handlers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.yplatform.commands.ICommandHandler;
import com.yplatform.commands.RegisterCommand;
import com.yplatform.commands.responses.ErrorResponse;
import com.yplatform.commands.responses.LoginResponse;
import com.yplatform.models.User;
import com.yplatform.network.ExitException;
import com.yplatform.network.Keywords;
import com.yplatform.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterCommandHandler extends BaseCommandHandler implements ICommandHandler<RegisterCommand, User> {

    private static final Logger logger = LoggerFactory.getLogger(RegisterCommandHandler.class);

    @Inject
    public RegisterCommandHandler(Injector injector, PrintWriter printWriter, BufferedReader reader) {
        super(injector, printWriter, reader, logger);
    }

    @Override
    public User Handle() throws IOException, ExitException {

        var command = readJsonObject(RegisterCommand.class);
        var userService = injector.getInstance(UserService.class);

        var newUser = new User(
                command.getUsername(),
                command.getName(),
                command.getEmail(),
                command.getPassword());
        var userResult = userService.addUser(newUser);
        if (userResult) {
            logger.info("New user registered " + newUser.getUsername());
//            writer.println(Keywords.CommandSuccess);
            newUser.setPassword(null);

            var response = new LoginResponse();
            response.setUser(newUser);
            // return posts
//            var postsService = injector.getInstance(PostService.class);
//            response.setPosts(
//                    postsService.getAllPostsByUser(
//                            newUser.getUsername()
//                    )
//            );
//            response.setInterests(
//                    postsService.getRandomPostsFromNonFollowedUsers(
//                            newUser.getUsername(),
//                            10)
//            );
            writer.println(gson.toJson(response));

            return newUser;

        } else {
            writer.println(
                    gson.toJson(
                            new ErrorResponse("Failed to create a new user. Verify and try again!")
                    )
            );
            return null;
        }
    }
}
