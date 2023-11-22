package com.yplatform.utils;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class ClientDiModule extends AppDiModule {
    private final PrintWriter printWriter;
    private final BufferedReader bufferedReader;

    public ClientDiModule(PrintWriter printWriter, BufferedReader bufferedReader) {

        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
    }

    @Override
    protected void configure() {
        super.configure();
        bind(PrintWriter.class).toInstance(printWriter);
        bind(BufferedReader.class).toInstance(bufferedReader);
//        // Bind Logger using a custom Provider
//        bind(Logger.class).toProvider(LoggerProvider.class);

    }
}
