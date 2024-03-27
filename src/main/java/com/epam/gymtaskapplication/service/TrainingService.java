package com.epam.gymtaskapplication.service;

import com.epam.gymtaskapplication.dao.SpecializationRepository;
import com.epam.gymtaskapplication.dao.TraineeRepository;
import com.epam.gymtaskapplication.dao.TrainerRepository;
import com.epam.gymtaskapplication.dao.TrainingRepository;
import com.epam.gymtaskapplication.model.*;
import com.epam.gymtaskapplication.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import request.dto.ReqNewTraining;
import response.dto.ResLogin;
import response.dto.TraineeInList;
import response.dto.TrainerInList;

import java.util.Optional;

@Service
@Transactional
public class TrainingService {
    // fields
    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final Logger LOG = LoggerFactory.getLogger(TrainingService.class);
    private static final Utility utility = new Utility();
    // injection
    public TrainingService(
            TrainingRepository trainingRepository,
            TraineeRepository traineeRepository,
            TrainerRepository trainerRepository
    ){
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
    }
    // Create
    public Optional<ResLogin> addTraining(
            ReqNewTraining data
    ){
        try {
            Optional<Trainer> optionalTrainer
                    = trainerRepository.findByUserUsername(data.getTrainerUsername());
            if(optionalTrainer.isPresent()){
                LOG.info("PROCESS: Retrieving Trainer-User");
                Optional<Trainee> optionalTrainee
                        = traineeRepository.findByUserUsername(data.getTraineeUsername());
                if(optionalTrainee.isPresent()){
                    LOG.info("PROCESS: Building Training");
                    Trainer trainer = optionalTrainer.get();
                    Specialization specialization = trainer.getSpecialization();
                    Trainee trainee = optionalTrainee.get();

                    LOG.info("Saving new data");
                    Training training = new Training();

                    training.setTrainee(trainee);
                    training.setTrainer(trainer);
                    training.setDate(data.getTrainingDate());
                    training.setDuration(data.getTrainingDuration());
                    training.setName(data.getTrainingName());
                    training.setSpecialization(specialization);
                    LOG.info("Saving to database");
                    trainingRepository.save(training);

                    ResLogin response = new ResLogin();
                    response.setMessage("Successfully added new Training");

                    LOG.info("PROCESS: Saving response");
                    return Optional.of(response);
                }
                else {
                    LOG.error("FAILED: Unable to retrieve Trainee entity");
                    return Optional.empty();
                }
            }
            else {
                LOG.error("FAILED: Unable to Retrieve Trainer entity");
                return Optional.empty();
            }
        }
        catch (Exception e){
            LOG.error("FAILED: Severe error when creating new Training");
            return Optional.empty();
        }
    }
}
