package com.epam.gymtaskapplication.controller;

import com.epam.gymtaskapplication.service.TrainingService;
import com.epam.gymtaskapplication.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import request.dto.ReqNewTraining;
import response.dto.ResLogin;

import java.util.Optional;

@RestController
@RequestMapping("/api/training")
public class TrainingController {
    // fields
    private final TrainingService trainingService;
    private final Logger LOG = LoggerFactory.getLogger(TrainingController.class);
    private static final Utility utility = new Utility();
    // injection
    public TrainingController(
            TrainingService trainingService
    ){
        this.trainingService = trainingService;
    }
    // Create
    @PostMapping("/add")
    public ResponseEntity<ResLogin> addTraining(
            @RequestBody ReqNewTraining data
    ){
        // instantiate transaction
        utility.instantiateTransaction();
        LOG.info("CALL: (POST) ?/api/training/add");
        // validate
        if(
                data.getTraineeUsername().isEmpty() ||
                data.getTrainerUsername().isEmpty() ||
                data.getTrainingName().isEmpty()
        ){
            LOG.warn("FAILED: Unable to create Training. One username reference was missing");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        if(data.getTrainingDate() == null || data.getTrainingDuration() <= 0){
            LOG.warn("FAILED: Wrong Date and Duration format");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        // call service
        Optional<ResLogin> opResponse
                = trainingService.addTraining(data);
        if(opResponse.isPresent()){
            LOG.info("SUCCESS: Created new Training Entity");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    opResponse.get(),
                    HttpStatus.OK
            );
        }
        else {
            LOG.error("FAILED: Severe error at Service Layer");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
