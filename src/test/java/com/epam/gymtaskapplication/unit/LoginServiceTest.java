package com.epam.gymtaskapplication.unit;

import com.epam.gymtaskapplication.dao.UserRepository;
import com.epam.gymtaskapplication.model.User;
import com.epam.gymtaskapplication.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void validateUserLogin_ValidCredentials_ReturnsTrue() {
        String username = "testUser";
        String password = "password123";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        boolean result = loginService.validateUserLogin(username, password);

        assertTrue(result);
    }

    @Test
    void validateUserLogin_IncorrectPassword_ReturnsFalse() {
        String username = "testUser";
        String password = "password123";
        User user = new User();
        user.setUsername(username);
        user.setPassword("wrongPassword");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        boolean result = loginService.validateUserLogin(username, password);

        assertFalse(result);
    }
}
