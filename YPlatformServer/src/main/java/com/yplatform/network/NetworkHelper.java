package com.yplatform.network;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public final class NetworkHelper {
    public static String readLine(BufferedReader reader, Logger logger) throws IOException {
        String serverResponse = reader.readLine();
        logger.info("[RCV] " + serverResponse);
        return serverResponse;
    }
}
