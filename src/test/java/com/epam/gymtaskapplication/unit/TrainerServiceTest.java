package com.epam.gymtaskapplication.unit;

import com.epam.gymtaskapplication.dao.SpecializationRepository;
import com.epam.gymtaskapplication.dao.TrainerRepository;
import com.epam.gymtaskapplication.dao.UserRepository;
import com.epam.gymtaskapplication.model.Specialization;
import com.epam.gymtaskapplication.model.Trainer;
import com.epam.gymtaskapplication.model.User;
import com.epam.gymtaskapplication.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import request.dto.ReqNewTrainer;
import response.dto.ResGetTrainer;
import response.dto.ResNewUser;

import javax.naming.NameNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SpecializationRepository specializationRepository;

    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addTrainer_ValidData_SuccessfullyAdded() throws NameNotFoundException {
        // Arrange
        ReqNewTrainer reqNewTrainer = new ReqNewTrainer();
        reqNewTrainer.setFirstName("John");
        reqNewTrainer.setLastName("Doe");
        reqNewTrainer.setSpecializationName("Specialization");
        User user = new User();
        user.setUsername("john.doe");
        user.setPassword("password123");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(specializationRepository.findByName(anyString())).thenReturn(Optional.of(new Specialization()));

        // Act
        Optional<ResNewUser> result = trainerService.addTrainee(reqNewTrainer);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user.getUsername(), result.get().getUsername());
        assertEquals(user.getPassword(), result.get().getPassword());
    }

    @Test
    void getTrainer_ExistingUsername_ReturnsTrainer() {
        // Arrange
        String username = "john.doe";
        Trainer trainer = new Trainer();
        Specialization specialization = new Specialization();
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername(username);
        trainer.setUser(user);
        trainer.setSpecialization(specialization);
        when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.of(trainer));

        // Act
        Optional<ResGetTrainer> result = trainerService.getTrainer(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user.getFirstName(), result.get().getFirstName());
        assertEquals(user.getLastName(), result.get().getLastName());
        assertNotNull(result.get().getSpecialization());
    }

    // Add more tests for other scenarios
}
