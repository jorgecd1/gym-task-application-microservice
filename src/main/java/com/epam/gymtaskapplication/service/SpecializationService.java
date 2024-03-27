package com.epam.gymtaskapplication.service;

import com.epam.gymtaskapplication.dao.SpecializationRepository;
import com.epam.gymtaskapplication.model.Specialization;
import com.epam.gymtaskapplication.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SpecializationService {
    // fields
    private final SpecializationRepository specializationRepository;
    private final Logger LOG = LoggerFactory.getLogger(SpecializationService.class);
    private static final Utility utility = new Utility();
    // injection
    public SpecializationService(
            SpecializationRepository specializationRepository
    ){
        this.specializationRepository = specializationRepository;
    }
    // Create, Read
        // Create
    public Optional<Specialization> addSpecialization(String name){
        try {
            LOG.info("PROCESS: Saving new Specialization");
            Specialization specialization = new Specialization();
            specialization.setName(name);
            LOG.info("PROCESS: Saving to Database");
            specializationRepository.save(specialization);
            // response
            return Optional.of(specialization);
        }
        catch (Exception e) {
            LOG.error("FAILED: An error occurred when handling new Specialization");
            return Optional.empty();
        }
    }
        // Read
    public Optional<Specialization> getSpecialization(String name){
        try {
            LOG.info("PROCESS: Getting Specialization by name");
            Optional<Specialization> opSpec
                    = specializationRepository.findByName(name);
            if(opSpec.isPresent()){
                LOG.info("PROCESS: Retrieving and responding to Service");
                return opSpec;
            }
            else {
                LOG.info("PROCESS: Specialization with provided name not found");
                return Optional.empty();
            }
        }
        catch (Exception e){
            LOG.error("FAILED: An unknown error happened during Specialization.GET");
            return Optional.empty();
        }
    }
}
