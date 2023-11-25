package com.yplatform.utils;

import com.google.inject.*;
import com.yplatform.database.dao.implementations.FollowingDAOImpl;
import com.yplatform.database.dao.implementations.PostDAOImpl;
import com.yplatform.database.dao.implementations.UserDAOImpl;
import com.yplatform.database.dao.interfaces.FollowingDAO;
import com.yplatform.database.dao.interfaces.PostDAO;
import com.yplatform.database.dao.interfaces.UserDAO;
import com.yplatform.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class AppDiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserDAO.class).to(UserDAOImpl.class);
        bind(FollowingDAO.class).to(FollowingDAOImpl.class);
        bind(PostDAO.class).to(PostDAOImpl.class);

        bind(AuthenticationService.class).asEagerSingleton();
    }
}


// TODO: Dynamic logger
//class LoggerProvider implements Provider<Logger> {
//    @Inject
//    private Injector injector;
//
//    @Override
//    public Logger get() {
//        // Retrieve the class of the requesting instance using TypeLiteral
//        Class<?> requestingClass = getKeyClass();
//
//        // Create and return the logger for the current class
//        return LoggerFactory.getLogger(requestingClass);
//    }
//
//    private Class<?> getKeyClass() {
//        // Use Key to get the TypeLiteral of the injected type
//        TypeLiteral<?> typeLiteral;
//        typeLiteral = TypeLiteral.get(injector.getBinding(Logger.class).getKey().getTypeLiteral());
//
//        // Get the raw type (class) from the TypeLiteral
//        return typeLiteral.getRawType();
//    }
//}