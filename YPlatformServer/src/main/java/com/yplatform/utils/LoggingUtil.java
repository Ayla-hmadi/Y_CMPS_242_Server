package com.yplatform.utils;

import org.slf4j.Logger;
import com.yplatform.models.User;

public class LoggingUtil {

    private LoggingUtil() {}

    public static void logUserInfo(Logger logger, User user) {
        if (user != null) {
            logger.info("User Info: Username - {}, Name - {}, Email - {}",
                    user.getUsername(), user.getName(), user.getEmail());
        } else {
            logger.warn("Attempted to log user info, but user object is null.");
        }
    }

    public static void logError(Logger logger, String message, Exception e) {
        logger.error(message + " Error: " + e.getMessage(), e);
    }

    public static void logInfo(Logger logger, String message) {
        logger.info(message);
    }

}
