package com.yplatform;

import com.yplatform.database.dao.interfaces.UserDAO;
import com.yplatform.models.User;
import com.yplatform.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @Test
    void addUser_NewUser_ReturnsTrue() {
        User newUser = new User("username", "name", "email@example.com", "password");
        when(userDAO.addUser(any(User.class))).thenReturn(true);

        boolean result = userService.addUser(newUser);

        assertTrue(result);
        verify(userDAO).addUser(any(User.class));
    }

    @Test
    public void authenticateUser_ValidCredentials_ReturnsUser() {
        String username = "alice";
        String rawPassword = "hashed_password1";
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(hashedPassword);

        when(userDAO.getUser(username)).thenReturn(Optional.of(mockUser));

        User result = userService.authenticateUser(username, rawPassword);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    public void authenticateUser_InvalidPassword_ReturnsNull() {
        String username = "alice";
        String rawPassword = "hashed_password1";
        String wrongPassword = "wrongPassword";
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(hashedPassword);

        when(userDAO.getUser(username)).thenReturn(Optional.of(mockUser));

        User result = userService.authenticateUser(username, wrongPassword);

        assertNull(result);
    }

    @Test
    public void authenticateUser_NonExistentUser_ReturnsNull() {
        String username = "nonExistentUser";
        String password = "password123";

        when(userDAO.getUser(username)).thenReturn(Optional.empty());

        User result = userService.authenticateUser(username, password);

        assertNull(result);
    }
}
