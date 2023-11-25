//package com.yplatform.commands.handlers;
//
//import com.google.inject.Inject;
//import com.google.inject.Injector;
//import com.yplatform.commands.*;
//import com.yplatform.models.*;
//import com.yplatform.network.*;
//import com.yplatform.services.PostService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//
//public class QueryPostsCommandHandler extends BaseCommandHandler implements ICommandHandler<QueryPostsCommand, List<Post>> {
//
//    private static final Logger logger = LoggerFactory.getLogger(QueryPostsCommandHandler.class);
//
//    @Inject
//    public QueryPostsCommandHandler(Injector injector, PrintWriter printWriter, BufferedReader reader) {
//        super(injector, printWriter, reader, logger);
//    }
//
//    @Override
//    public List<Post> Handle() throws IOException, ExitException {
//        // read loginDto
//        var command = readJsonObject(RegisterCommand.class);
//        var userService = injector.getInstance(PostService.class);
//
//        var newUser = new User(
//                command.getUsername(),
//                command.getName(),
//                command.getEmail(),
//                command.getPassword());
//        var userResult = userService.addUser(newUser);
//        if (userResult) {
//            logger.info("New user registered " + newUser.getUsername());
//            writer.println(Keywords.CommandSuccess);
//            return newUser;
//        } else {
//            writer.println("Failed to create a new user. Verify and try again!");
//        }
//
//    }
//}
