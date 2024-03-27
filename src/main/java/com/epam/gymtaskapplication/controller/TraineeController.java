package com.epam.gymtaskapplication.controller;

import com.epam.gymtaskapplication.service.LoginService;
import com.epam.gymtaskapplication.service.TraineeService;
import com.epam.gymtaskapplication.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import request.dto.ReqDeleteTrainee;
import request.dto.ReqGetUser;
import request.dto.ReqNewTrainee;
import request.dto.ReqUpdateTrainee;
import response.dto.ResGetTrainee;
import response.dto.ResLogin;
import response.dto.ResNewUser;
import response.dto.SimpleTrainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trainee/")
public class TraineeController {
    // fields
    private final TraineeService traineeService;
    private final LoginService loginService;
    private final Logger LOG = LoggerFactory.getLogger(TraineeController.class);
    private static final Utility utility = new Utility();
    // injection
    public TraineeController(
            TraineeService traineeService,
            LoginService loginService
    ){
        this.traineeService = traineeService;
        this.loginService = loginService;
    }
    // Create, Read, Update, Delete
        // Create
    @PostMapping("/add")
    public ResponseEntity<ResNewUser> addTrainee(
            @RequestBody ReqNewTrainee data
    ){
        // instantiate call
        utility.instantiateTransaction();
        LOG.info("CALL (POST): ?/api/trainee/add");
        // validate first and last name
        if(data.getFirstName().isEmpty() || data.getLastName().isEmpty()){
            LOG.warn("FAILED: First and Last Name cannot be null for new Trainee");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        // call service
        Optional<ResNewUser> opResponse
                = traineeService.addTrainee(data);
        if (opResponse.isPresent()){
            // response
            LOG.info("SUCCESS: Created and saved new Trainee Entity");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    opResponse.get(),
                    HttpStatus.OK
            );
        }
        else {
            // response
            LOG.error("FAILED: Unable to create Trainee, error at Service");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
        // Read
    @GetMapping("/get")
    public ResponseEntity<ResGetTrainee> getTrainee(
            @RequestBody ReqGetUser data
    ){
        // instantiate call
        utility.instantiateTransaction();
        LOG.info("CALL: (GET) ?/api/trainee/get");
        // validate fields
        if(data.getUsername().isEmpty() || data.getPassword().isEmpty()){
            LOG.warn("FAILED: Username and / or password was missing");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        // validate login before service
        if(validateCredentials(
                data.getUsername(),
                data.getPassword()
        )){
            // call service
            Optional<ResGetTrainee> opResponse
                    = traineeService.getTrainee(data.getUsername());
            if(opResponse.isPresent()){
                LOG.info("SUCCESS: Retrieved Trainee");
                utility.closeTransaction();
                return new ResponseEntity<>(
                        opResponse.get(),
                        HttpStatus.OK
                );
            }
            else {
                LOG.warn("FAILED: Unable to retrieve Trainee");
                utility.closeTransaction();
                return new ResponseEntity<>(
                        HttpStatus.NOT_FOUND
                );
            }
        }
        else {
            // response
            LOG.warn("FAILED: Credentials not validated. Incorrect password" +
                    " or username");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.UNAUTHORIZED
            );
        }

    }
    @GetMapping("/get-not-assigned")
    public ResponseEntity<List<SimpleTrainer>> getAllNotAssignedOnTrainee(
            @RequestBody ReqGetUser data
    ){
        // instantiate call
        utility.instantiateTransaction();
        LOG.info("CALL: (GET) ?/api/trainee/get-not-assigned");
        // validate fields
        if(data.getUsername().isEmpty() || data.getPassword().isEmpty()){
            LOG.warn("FAILED: Incorrect or missing password / username");
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
            LOG.warn("FAILED: Credentials do not match");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.UNAUTHORIZED
            );
        }
        // call service
        List<SimpleTrainer> notAssignedList
                = traineeService.getNotAssigned(data.getUsername());
        if(notAssignedList.isEmpty()){
            LOG.info("COMPLETED: Retrieved List but was empty");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT
            );
        }
        else {
            LOG.info("SUCCESS: Retrieved not assigned Trainer");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    notAssignedList,
                    HttpStatus.OK
            );
        }
    }
        // Update
    @PutMapping("/update")
    public ResponseEntity<ResGetTrainee> updateTrainee(
            @RequestBody ReqUpdateTrainee data
    ){
        // instantiate call
        utility.instantiateTransaction();
        LOG.info("CALL: (PUT) ?/api/trainee/update");
        // validate credentials
        if(data.getUsername().isEmpty() || data.getPassword().isEmpty()){
            LOG.warn("FAILED: Missing password or username");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        // validate fields
        if(data.getFirstName().isEmpty() || data.getLastName().isEmpty()){
            LOG.warn("FAILED: Missing first or last name for update");
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
            LOG.warn("FAILED: Credentials do not match for user");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.UNAUTHORIZED
            );
        }
        // call service
        Optional<ResGetTrainee> opTrainee
                = traineeService.updateTrainee(data);
        if(opTrainee.isPresent()){
            LOG.info("SUCCESS: Updated Trainee");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    opTrainee.get(),
                    HttpStatus.OK
            );
        }
        else {
            LOG.error("FAILED: Error at service layer. User not found");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }
    }
        // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<ResLogin> deleteTrainee(
            @RequestBody ReqDeleteTrainee data
    ){
        // instantiate transaction
        utility.instantiateTransaction();
        LOG.info("CALL: (DELETE) ?/api/trainee/delete");
        ResLogin response = new ResLogin();
        // validate credentials
        if(data.getUsername().isEmpty() || data.getPassword().isEmpty()){
            response.setMessage("Missing password or username");
            LOG.info("FAILED: Missing password or username");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    response,
                    HttpStatus.BAD_REQUEST
            );
        }
        // validate login
        if(!validateCredentials(
                data.getUsername(),
                data.getPassword()
        )){
            LOG.warn("FAILED: Incorrect password or username");
            utility.closeTransaction();
            response.setMessage("Incorrect password. Account not deleted");
            return new ResponseEntity<>(
                    response,
                    HttpStatus.UNAUTHORIZED
            );
        }
        // call service
        Optional<ResLogin> opResponse
                = traineeService.deleteTrainee(data.getUsername());
        if(opResponse.isPresent()){
            LOG.info("SUCCESS: Deleted Trainee-User account");
            utility.closeTransaction();
            response.setMessage("Account has been successfully deleted");
            return new ResponseEntity<>(
                    response,
                    HttpStatus.OK
            );
        }
        else {
            LOG.error("FAILED: Error at service layer");
            utility.closeTransaction();
            response.setMessage("Error at service layer. Trainee not deleted ");
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
