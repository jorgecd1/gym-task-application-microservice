package com.epam.gymtaskapplication.controller;

import com.epam.gymtaskapplication.dao.SpecializationRepository;
import com.epam.gymtaskapplication.model.Specialization;
import com.epam.gymtaskapplication.service.SpecializationService;
import com.epam.gymtaskapplication.util.Utility;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/specialization")
public class SpecializationController {
    // fields
    private final SpecializationService specializationService;
    private final Logger LOG = LoggerFactory.getLogger(SpecializationController.class);
    private static final Utility utility = new Utility();
    // injection
    public SpecializationController(
            SpecializationService specializationService
    ){
        this.specializationService = specializationService;
    }
    // Create, Read
        // Create
    @PostMapping("/add/{name}")
    public ResponseEntity<Specialization> addSpecialization(
            @PathVariable String name
    ){
        // instantiate call
        String transactionId = utility.generateTransactionId();
        MDC.put("transactionId",transactionId);
        LOG.info("CALL: (POST) ?/api/specialization/add/{name}");
        // validate
        if(name.isEmpty()){
            LOG.error("FAILED: Unable to create Specialization. Name was" +
                    " missing or null");
            MDC.remove("transactionId");
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        // call service
        Optional<Specialization> opSpec
                = specializationService.addSpecialization(name);
        if(opSpec.isPresent()){
            LOG.info("SUCCESS: Created and saved Specialization to Database");
            MDC.remove("transactionId");
            return new ResponseEntity<>(
                    opSpec.get(),
                    HttpStatus.OK
            );
        }
        else {
            LOG.error("FAILED: Error occurred when saving Specialization to " +
                    "Database");
            MDC.remove("transactionId");
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
        // Read
    @GetMapping("/get/{name}")
    public ResponseEntity<Specialization> getSpecialization(
            @PathVariable String name
    ){
        // instantiate call
        String transactionId = utility.generateTransactionId();
        MDC.put("transactionId",transactionId);
        LOG.info("CALL: (POST) ?/api/specialization/get/{name}");
        // validate name
        if(name.isEmpty()){
            LOG.error("FAILED: Name provided for GET was null or missing");
            MDC.remove("transactionId");
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        // call service
        Optional<Specialization> opSpec
                = specializationService.getSpecialization(name);
        if(opSpec.isPresent()){
            LOG.info("SUCCESS: Specialization with provided name retrieved");
            MDC.remove("transactionId");
            return new ResponseEntity<>(
                    opSpec.get(),
                    HttpStatus.OK
            );
        }
        else {
            LOG.info("COMPLETED: Specialization with name provided was not found");
            MDC.remove("transactionId");
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
