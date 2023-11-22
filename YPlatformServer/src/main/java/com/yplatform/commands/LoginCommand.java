package com.yplatform.commands;

import com.yplatform.models.User;

public class LoginCommand implements ICommand<User> {
    private String username, password;

    public LoginCommand() {
    }

    public LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

