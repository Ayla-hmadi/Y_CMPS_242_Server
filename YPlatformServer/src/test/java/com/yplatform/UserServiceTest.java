package com.yplatform;

import com.yplatform.database.dao.interfaces.UserDAO;
import com.yplatform.models.User;
import com.yplatform.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

}
