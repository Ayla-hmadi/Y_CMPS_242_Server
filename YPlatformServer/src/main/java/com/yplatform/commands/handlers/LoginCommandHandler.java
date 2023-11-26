package com.yplatform.commands.handlers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.yplatform.commands.ICommandHandler;
import com.yplatform.commands.LoginCommand;
import com.yplatform.commands.responses.ErrorResponse;
import com.yplatform.commands.responses.LoginResponse;
import com.yplatform.models.User;
import com.yplatform.network.ExitException;
import com.yplatform.services.PostService;
import com.yplatform.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginCommandHandler extends BaseCommandHandler implements ICommandHandler<LoginCommand, User> {

    private static final Logger logger = LoggerFactory.getLogger(LoginCommandHandler.class);

    @Inject
    public LoginCommandHandler(Injector injector, PrintWriter printWriter, BufferedReader reader) {
        super(injector, printWriter, reader, logger);
    }

    @Override
    public User Handle() throws IOException, ExitException {
        var response = new LoginResponse();
        var login = readJsonObject(LoginCommand.class);
        var userService = injector.getInstance(UserService.class);

        User user = userService.authenticateUser(login.getUsername(), login.getPassword());
        if (user == null) {
            writer.println(gson.toJson(new ErrorResponse("Invalid username or password!")));
            return null;
        }
        user.setPassword(null);
        response.setUser(user);

        // return posts
        var postsService = injector.getInstance(PostService.class);
        response.setPosts(
                postsService.getAllPostsByUser(user.getUsername())
        );
        response.setInterests(
                postsService.getRandomPostsFromNonFollowedUsers(user.getUsername(), 10)
        );
        writer.println(gson.toJson(response));

        return user;
    }
}


