package com.yplatform.network;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineUsers {
    private static final ConcurrentHashMap<String, Socket> onlineUsers = new ConcurrentHashMap<>();

    public static void addUser(String username, Socket socket) {
        onlineUsers.put(username, socket);
    }

    public static void removeUser(String username) {
        onlineUsers.remove(username);
    }

    public static Socket getUserSocket(String username) {
        return onlineUsers.get(username);
    }

}
