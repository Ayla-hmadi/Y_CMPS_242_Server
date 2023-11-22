package com.yplatform.commands.handlers;

import com.google.gson.*;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.yplatform.commands.ICommandHandler;
import com.yplatform.commands.LoginCommand;
import com.yplatform.models.User;
import com.yplatform.network.ExitException;
import com.yplatform.network.clientHandlers.DefaultClientHandler;
import com.yplatform.services.UserService;
import com.yplatform.shared.dtos.LoginDto;
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
//        User loginUser = null;
        while (true) {
            // read loginDto
            var login = readJsonObject(LoginDto.class);
            var userService = injector.getInstance(UserService.class);

            var userResult = userService.getUser(login.getUsername());
            if (userResult.isEmpty()) {
                writer.println("Invalid username!");
            } else {
                if (!userResult.get().getPassword().equals(login.getPassword())) {
                    writer.println("Invalid password");
                } else {
                    return userResult.get();
                    // login success
                }
            }
        }
    }
}
