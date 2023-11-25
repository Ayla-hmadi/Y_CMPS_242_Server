package com.yplatform.commands.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Injector;
import com.yplatform.network.ExitException;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class BaseCommandHandler {
    protected final Injector injector;
    protected final PrintWriter writer;
    protected final BufferedReader reader;
    protected final Logger logger;
    protected final Gson gson;

    public BaseCommandHandler(Injector injector, PrintWriter printWriter, BufferedReader reader, Logger logger) {
        this.injector = injector;
        this.gson = new Gson();
        this.writer = printWriter;
        this.reader = reader;
        this.logger = logger;
    }

    protected <T> T readJsonObject(Class<T> classOfT) throws IOException, ExitException {
        while (true) {
            var inputLine = reader.readLine();
            if (inputLine.equals("cancel") || inputLine.equals("close")) {
                throw new ExitException();
            }
            try {
                Gson gson = new Gson();
                var json = gson.fromJson(inputLine, classOfT);
                logger.info("Received json object");
                return json;
            } catch (JsonSyntaxException exception) {
                logger.error("Invalid json object", exception);
                writer.println("Invalid json input. Please try again");
            }
        }
    }
}
