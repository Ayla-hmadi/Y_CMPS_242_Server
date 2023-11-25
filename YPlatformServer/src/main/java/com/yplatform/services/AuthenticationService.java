package com.yplatform.services;

import com.yplatform.models.User;

public class AuthenticationService {
    User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User newValue) {
        currentUser = newValue;
    }
}
