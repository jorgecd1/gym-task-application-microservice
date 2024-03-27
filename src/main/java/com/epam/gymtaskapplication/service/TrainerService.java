package com.epam.gymtaskapplication.service;

import com.epam.gymtaskapplication.dao.SpecializationRepository;
import com.epam.gymtaskapplication.dao.TrainerRepository;
import com.epam.gymtaskapplication.dao.UserRepository;
import com.epam.gymtaskapplication.model.Specialization;
import com.epam.gymtaskapplication.model.Trainer;
import com.epam.gymtaskapplication.model.User;
import com.epam.gymtaskapplication.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import request.dto.ReqNewTrainer;
import request.dto.ReqUpdateTrainer;
import response.dto.ResGetTrainer;
import response.dto.ResLogin;
import response.dto.ResNewUser;
import response.dto.SimpleTrainer;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrainerService {
    // fields
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final SpecializationRepository specializationRepository;
    private static final Utility utility = new Utility();
    private final Logger LOG = LoggerFactory.getLogger(TrainerService.class);
    // injection
    public TrainerService(
            TrainerRepository trainerRepository,
            UserRepository userRepository,
            SpecializationRepository specializationRepository
    ){
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.specializationRepository = specializationRepository;
    }
    // Create, Read, Update, Delete
        // Create
    public Optional<ResNewUser> addTrainee(
            ReqNewTrainer data
    ){
        try {
            LOG.info("PROCESS: Creating new Trainer-User");
            Trainer trainer = new Trainer();
            User user = new User();
            Specialization specialization = getSpecializationByName(data.getSpecializationName());

            LOG.info("PROCESS: Adding data to entity");
            user.setFirstName(data.getFirstName());
            user.setLastName(data.getLastName());
            user.setUsername(
                    countUsername(
                            data.getFirstName(),
                            data.getLastName()
                    )
            );
            user.setActive(true);
            user.setPassword(utility.generatePassword());

            trainer.setSpecialization(specialization);
            trainer.setUser(user);
            LOG.info("PROCESS: Saving entities to database");
            userRepository.save(user);
            trainerRepository.save(trainer);
            LOG.info("PROCESS: Responding to controller");
            ResNewUser resNewUser = new ResNewUser();
            resNewUser.setUsername(user.getUsername());
            resNewUser.setPassword(user.getPassword());
            return Optional.of(resNewUser);
        }
        catch (Exception e){
            LOG.error("FAILED: Unable to create Trainer. Error at handling data");
            return Optional.empty();
        }
    }
        // Read
    public Optional<ResGetTrainer> getTrainer(
            String username
    ){
        try {
            LOG.info("PROCESS: Retrieving Trainer-User");
            Optional<Trainer> opTrainer
                    = trainerRepository.findByUserUsername(username);
            if(opTrainer.isPresent()){
                LOG.info("PROCESS: Retrieving Trainer-User");
                ResGetTrainer response = new ResGetTrainer();
                Trainer trainer = opTrainer.get();
                Specialization specialization = trainer.getSpecialization();
                User user = trainer.getUser();

                response.setFirstName(user.getFirstName());
                response.setLastName(user.getLastName());
                response.setSpecialization(specialization);
                response.setActive(user.isActive());
                // TODO: Implement List

                LOG.info("PROCESS: Returning response");
                return Optional.of(response);
            }
            else {
                LOG.warn("FAILED: Unable to retrieve Trainer. Not Found");
                return Optional.empty();
            }
        }
        catch (Exception e){
            LOG.error("FAILED: Error at service. Unable to recover Trainer-User");
            return Optional.empty();
        }
    }
        // Update
    public Optional<ResGetTrainer> updateTrainer(
            ReqUpdateTrainer data
    ){
        try {
            LOG.info("PROCESS: Retrieving Trainer");
            Optional<Trainer> opTrainer
                    = trainerRepository.findByUserUsername(data.getUsername());
            if(opTrainer.isPresent()){
                Trainer trainer = opTrainer.get();
                User user = trainer.getUser();
                Specialization specialization = new Specialization();
                Optional<Specialization> opSpec
                        = specializationRepository.findByName(data.getSpecializationName());
                if(opSpec.isPresent()){
                    specialization = opSpec.get();
                }
                else {
                    LOG.error("FAILED: Unable to retrieve new Specialization");
                    return Optional.empty();
                }
                LOG.info("PROCESS: Setting new Data");
                trainer.setSpecialization(specialization);

                user.setFirstName(data.getFirstName());
                user.setLastName(data.getLastName());
                user.setActive(data.isActive());

                trainer.setUser(user);
                LOG.info("PROCESS: Saving changes to database");
                userRepository.save(user);
                trainerRepository.save(trainer);
                ResGetTrainer response = new ResGetTrainer();

                response.setSpecialization(trainer.getSpecialization());
                response.setActive(user.isActive());
                response.setFirstName(user.getFirstName());
                response.setLastName(user.getLastName());
                // TODO: Implement list

                return Optional.of(response);
            }
            else {
                LOG.error("FAILED: Unable to retrieve Trainer-User");
                return Optional.empty();
            }
        }
        catch (Exception e){
            LOG.error("FAILED: Unknown exception at service layer");
            return Optional.empty();
        }
    }
        // Delete
    public Optional<ResLogin> deleteTrainer(
            String username
    ){
        try {
            Optional<Trainer> optionalTrainer
                    = trainerRepository.findByUserUsername(username);
            if(optionalTrainer.isPresent()){
                LOG.info("PROCESS: Retrieving Trainer");
                Trainer trainer = optionalTrainer.get();
                User user = trainer.getUser();
                LOG.info("PROCESS: Deleting from Database");
                userRepository.delete(user);
                trainerRepository.delete(trainer);
                LOG.info("PROCESS: Returning response");
                ResLogin response = new ResLogin();
                response.setMessage("Account deleted");
                return Optional.of(response);
            }
            else {
                LOG.error("FAILED: Trainer-User not found");
                return Optional.empty();
            }
        }
        catch (Exception e){
            LOG.error("FAILED: Unable to retrieve or modify Trainer-User");
            return Optional.empty();
        }
    }
    // Methods
    private Specialization getSpecializationByName(String name) throws NameNotFoundException {
        Optional<Specialization> opSpec
                = specializationRepository.findByName(name);
        if(opSpec.isPresent()){
            LOG.info("PROCESS: Fetching Specialization");
            return opSpec.get();
        }
        else {
            LOG.error("FAILED: Reference Specialization null or missing");
            throw new NameNotFoundException();
        }
    }
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
