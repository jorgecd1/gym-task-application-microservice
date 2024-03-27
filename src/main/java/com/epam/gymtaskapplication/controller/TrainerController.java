package com.epam.gymtaskapplication.controller;

import com.epam.gymtaskapplication.service.LoginService;
import com.epam.gymtaskapplication.service.TrainerService;
import com.epam.gymtaskapplication.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import request.dto.ReqGetUser;
import request.dto.ReqNewTrainee;
import request.dto.ReqNewTrainer;
import request.dto.ReqUpdateTrainer;
import response.dto.ResGetTrainer;
import response.dto.ResLogin;
import response.dto.ResNewUser;

import java.util.Optional;

@RestController
@RequestMapping("/api/trainer")
public class TrainerController {
    // fields
    private final TrainerService trainerService;
    private final LoginService loginService;
    private final Logger LOG = LoggerFactory.getLogger(TrainerController.class);
    private static final Utility utility = new Utility();
    // injection
    public TrainerController(
            TrainerService trainerService,
            LoginService loginService
    ){
        this.trainerService = trainerService;
        this.loginService = loginService;
    }
    // Create, Read, Update, Delete
        // Create
    @PostMapping("/add")
    public ResponseEntity<ResNewUser> addTrainer(
            @RequestBody ReqNewTrainer data
    ){
        // instantiate call
        utility.instantiateTransaction();
        LOG.info("CALL: (POST) ?/api/trainer/add");
        // validate
        if(data.getFirstName().isEmpty() || data.getLastName().isEmpty()){
            LOG.warn("FAILED: First and Last Name cannot be null for new Trainer");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        if(data.getSpecializationName().isEmpty()){
            LOG.warn("FAILED: Specialization name reference cannot be null");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        // call service
        Optional<ResNewUser> opResponse
                = trainerService.addTrainee(data);
        if(opResponse.isPresent()){
            // response
            LOG.info("SUCCESS: Created and saved new Trainer Entity");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    opResponse.get(),
                    HttpStatus.OK
            );
        }
        else {
            // response
            LOG.error("FAILED: Unable to Create new Trainer, error at Service");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
        // Read
    @GetMapping("/get")
    public ResponseEntity<ResGetTrainer> getTrainer(
            @RequestBody ReqGetUser data
    ){
        // instantiate transaction
        utility.instantiateTransaction();
        LOG.info("CALL: (GET) ?/api/trainer/get");
        // validate fields
        if(data.getUsername().isEmpty() || data.getPassword().isEmpty()){
            LOG.warn("FAILED: Missing username and / or password");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        // validate login
        if(!validateCredentials(
                data.getUsername(),
                data.getPassword()
        )){
            LOG.warn("FAILED: Incorrect username or password");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        // call service
        Optional<ResGetTrainer> opTrainer
                = trainerService.getTrainer(data.getUsername());
        if(opTrainer.isPresent()){
            LOG.info("SUCCESS: Retrieved Trainer-User");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    opTrainer.get(),
                    HttpStatus.OK
            );
        }
        else {
            LOG.error("FAILED: Unable to recover user. Not found");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }
    }
        // Update
    @PutMapping("/update")
    public ResponseEntity<ResGetTrainer> updateTrainer(
            @RequestBody ReqUpdateTrainer data
    ){
        // instantiate call
        utility.instantiateTransaction();
        LOG.info("CALL: (PUT) ?/api/trainer/get");
        // validate credentials
        if(!validateCredentials(
                data.getUsername(),
                data.getPassword()
        )){
            LOG.warn("FAILED: Username or password incorrect");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.UNAUTHORIZED
            );
        }
        // call service
        Optional<ResGetTrainer> opTrainer
                = trainerService.updateTrainer(data);
        if(opTrainer.isPresent()){
            LOG.info("SUCCESS: Updated Trainer");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    opTrainer.get(),
                    HttpStatus.OK
            );
        }
        else {
            LOG.error("FAILED: Error handling data at service layer");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
        // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<ResLogin> deleteTrainer(
            @RequestBody ReqGetUser data
    ){
        // instantiate call
        utility.instantiateTransaction();
        LOG.info("CALL: (DELETE) ?/api/trainer/delete");
        // validate data
        if(data.getUsername().isEmpty() || data.getPassword().isEmpty()){
            LOG.warn("FAILED: Username and Password cannot be null");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        // validate credentials
        if(!validateCredentials(
                data.getUsername(),
                data.getPassword()
        )){
            LOG.warn("FAILED: Password and Username not matching");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.UNAUTHORIZED
            );
        }
        // call service
        Optional<ResLogin> opResponse
                = trainerService.deleteTrainer(data.getUsername());
        if(opResponse.isPresent()){
            LOG.info("SUCCESS: Deleted Trainer-User");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    opResponse.get(),
                    HttpStatus.OK
            );
        }
        else {
            LOG.error("FAILED: Unknown exception at Service");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    // Methods
    private boolean validateCredentials(
            String username,
            String password
    ){
        return loginService.validateUserLogin(
                username,
                password
        );
    }
}
