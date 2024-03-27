package com.epam.gymtaskapplication.service;

import com.epam.gymtaskapplication.dao.TraineeRepository;
import com.epam.gymtaskapplication.dao.TrainerRepository;
import com.epam.gymtaskapplication.dao.TrainingRepository;
import com.epam.gymtaskapplication.dao.UserRepository;
import com.epam.gymtaskapplication.model.Trainee;
import com.epam.gymtaskapplication.model.Trainer;
import com.epam.gymtaskapplication.model.Training;
import com.epam.gymtaskapplication.model.User;
import com.epam.gymtaskapplication.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import request.dto.ReqDeleteTrainee;
import request.dto.ReqNewTrainee;
import request.dto.ReqUpdateTrainee;
import response.dto.ResGetTrainee;
import response.dto.ResLogin;
import response.dto.ResNewUser;
import response.dto.SimpleTrainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TraineeService {
    // fields
    private final TraineeRepository traineeRepository;
    private final UserRepository userRepository;
    private final Logger LOG = LoggerFactory.getLogger(TraineeService.class);
    private static final Utility utility = new Utility();
    // injection
    public TraineeService(
            TraineeRepository traineeRepository,
            UserRepository userRepository
    ){
        this.traineeRepository = traineeRepository;
        this.userRepository = userRepository;
    }
    // Create, Read, Update, Delete
        // Create
    public Optional<ResNewUser> addTrainee(
            ReqNewTrainee data
    ){
        try {
            LOG.info("PROCESS: Creating new Trainee-User");
            Trainee trainee = new Trainee();
            User user = new User();
            LOG.info("PROCESS: Add data");
            trainee.setAddress(data.getAddress());
            trainee.setDateOfBirth(data.getDateOfBirth());

            user.setFirstName(data.getFirstName());
            user.setLastName(data.getLastName());
            user.setPassword(utility.generatePassword());
            user.setActive(true);
            user.setUsername(countUsername(
                    data.getFirstName(),
                    data.getLastName()
            ));

            trainee.setUser(user);
            LOG.info("PROCESS: Saving to Database");
            userRepository.save(user);
            traineeRepository.save(trainee);
            // response
            ResNewUser resNewUser = new ResNewUser();
            resNewUser.setUsername(user.getUsername());
            resNewUser.setPassword(user.getPassword());
            return Optional.of(resNewUser);
        }
        catch (Exception e){
            LOG.error("FAILED: Unable to create Trainee. Error when handling data");
            return Optional.empty();
        }
    }
        // Read
    public Optional<ResGetTrainee> getTrainee(
            String username
    ){
        try {
            Optional<Trainee> optionalTrainee
                    = traineeRepository.findByUserUsername(username);
            if(optionalTrainee.isPresent()){
                LOG.info("PROCESS: Retrieving Trainee-User");
                Trainee trainee = optionalTrainee.get();
                ResGetTrainee response = new ResGetTrainee();
                response.setFirstName(trainee.getUser().getFirstName());
                response.setLastName(trainee.getUser().getLastName());
                response.setDateOfBirth(trainee.getDateOfBirth());
                response.setAddress(trainee.getAddress());
                response.setActive(trainee.getUser().isActive());
                // TODO: Implement Set constructor
                return Optional.of(response);
            }
            else {
                LOG.info("FAILED: No user with provided username was found");
                return Optional.empty();
            }
        }
        catch (Exception e){
            LOG.error("FAILED: Error when handling data at service");
            return Optional.empty();
        }
    }
    public List<SimpleTrainer> getNotAssigned(
            String username
    ){
        return new ArrayList<>();
    }
        // Update
    public Optional<ResGetTrainee> updateTrainee(
            ReqUpdateTrainee data
    ){
        try {
            Optional<Trainee> opTrainee
                    = traineeRepository.findByUserUsername(data.getUsername());
            if(opTrainee.isPresent()){
                LOG.info("PROCESS: Retrieving and updating");
                Trainee trainee = opTrainee.get();
                User user = opTrainee.get().getUser();

                trainee.setAddress(data.getAddress());
                trainee.setDateOfBirth(data.getDateOfBirth());

                user.setFirstName(data.getFirstName());
                user.setLastName(data.getLastName());
                user.setActive(data.isActive());

                trainee.setUser(user);

                LOG.info("PROCESS: Saving changes to database");
                userRepository.save(user);
                traineeRepository.save(trainee);

                ResGetTrainee response = new ResGetTrainee();
                response.setFirstName(user.getFirstName());
                response.setLastName(user.getLastName());
                response.setAddress(trainee.getAddress());
                response.setDateOfBirth(trainee.getDateOfBirth());
                response.setActive(data.isActive());
                return Optional.of(response);
            }
            else {
                LOG.info("FAILED: User not found for provided username");
                return Optional.empty();
            }
        }
        catch (Exception e){
            LOG.error("FAILED: Severe error at service level when updating");
            return Optional.empty();
        }
    }
        // Delete
    public Optional<ResLogin> deleteTrainee(
            String username
    ){
        try {
            Optional<Trainee> opTrainee
                    = traineeRepository.findByUserUsername(username);
            if(opTrainee.isPresent()){
                LOG.info("PROCESS: Retrieving Trainee-User");
                Trainee trainee = opTrainee.get();
                User user = trainee.getUser();

                LOG.info("PROCESS: Deleting from database");
                userRepository.delete(user);
                traineeRepository.delete(trainee);

                ResLogin response = new ResLogin();
                response.setMessage("Account deleted");
                return Optional.of(response);
            }
            else {
                LOG.error("FAILED: Trainee-User unable to be retrieved");
                return Optional.empty();
            }
        }
        catch (Exception e){
            LOG.error("ERROR: Unknown exception at service level");
            return Optional.empty();
        }
    }
    // methods
    public String countUsername(String firstName, String lastName){

        long counter = userRepository.findByFirstNameAndLastName(
                firstName,
                lastName
        ).size();
        String username = firstName+"."+lastName;
        if(counter == 0L){
            return username;
        }
        else {
            return username+counter;
        }
    }
}
