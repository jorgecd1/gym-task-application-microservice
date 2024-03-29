package com.epam.gymtaskapplication.unit;

import com.epam.gymtaskapplication.dao.TraineeRepository;
import com.epam.gymtaskapplication.dao.UserRepository;
import com.epam.gymtaskapplication.model.Trainee;
import com.epam.gymtaskapplication.model.User;
import com.epam.gymtaskapplication.service.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import request.dto.ReqNewTrainee;
import response.dto.ResGetTrainee;
import response.dto.ResNewUser;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addTrainee_ValidData_SuccessfullyAdded() {
        // Arrange
        ReqNewTrainee reqNewTrainee = new ReqNewTrainee();
        reqNewTrainee.setFirstName("John");
        reqNewTrainee.setLastName("Doe");
        reqNewTrainee.setAddress("123 Main St");
        reqNewTrainee.setDateOfBirth(new Date());
        User user = new User();
        user.setUsername("john.doe");
        user.setPassword("password123");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        Optional<ResNewUser> result = traineeService.addTrainee(reqNewTrainee);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user.getUsername(), result.get().getUsername());
        assertEquals(user.getPassword(), result.get().getPassword());
    }

    @Test
    void getTrainee_ExistingUsername_ReturnsTrainee() {
        // Arrange
        String username = "john.doe";
        Trainee trainee = new Trainee();
        trainee.setAddress("123 Main St");
        trainee.setDateOfBirth(new Date());
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername(username);
        trainee.setUser(user);
        when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));

        // Act
        Optional<ResGetTrainee> result = traineeService.getTrainee(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user.getFirstName(), result.get().getFirstName());
        assertEquals(user.getLastName(), result.get().getLastName());
        assertEquals(trainee.getAddress(), result.get().getAddress());
        assertEquals(trainee.getDateOfBirth(), result.get().getDateOfBirth());
    }
}
