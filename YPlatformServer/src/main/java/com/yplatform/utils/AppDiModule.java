package com.yplatform.utils;

import com.google.inject.*;
import com.yplatform.database.dao.implementations.UserDAOImpl;
import com.yplatform.database.dao.interfaces.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class AppDiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserDAO.class).to(UserDAOImpl.class);
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