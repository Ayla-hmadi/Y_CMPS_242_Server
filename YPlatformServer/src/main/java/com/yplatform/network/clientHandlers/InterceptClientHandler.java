package com.yplatform.network.clientHandlers;

import com.yplatform.network.interceptors.IMessageInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the client by processing a list of interceptors
 */
public class InterceptClientHandler {
    public InterceptClientHandler(List<IMessageInterceptor> messageInterceptors) {
        this.messageInterceptors = messageInterceptors;
    }

    List<IMessageInterceptor> messageInterceptors;
}
