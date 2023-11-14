package com.yplatform.commands;

public class LoginCommand implements ICommand {


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username, password;

    @Override
    public void Execute() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
