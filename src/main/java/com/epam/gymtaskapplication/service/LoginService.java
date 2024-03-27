package com.epam.gymtaskapplication.service;

import com.epam.gymtaskapplication.dao.UserRepository;
import com.epam.gymtaskapplication.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import request.dto.ReqChangeLogin;

import java.util.Optional;

@Service
@Transactional
public class LoginService {
    // fields
    private final UserRepository userRepository;
    private final Logger LOG = LoggerFactory.getLogger(LoginService.class);
    // injection
    public LoginService(
            UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }
    // Read
    public boolean validateUserLogin(
            String username,
            String password
    ){
        try {
            Optional<User> opUser
                    = userRepository.findByUsername(username);
            if(opUser.isPresent()){
                LOG.info("PROCESS: Validating User Credentials");
                if(opUser.get().getPassword().equals(password)){
                    LOG.info("PROCESS (SUCCESS): User is now verified");
                    return true;
                }
                else {
                    LOG.warn("PROCESS (FAILED): Unable to verify, incorrect password");
                    return false;
                }
            }
            else {
                LOG.info("FAILED: User with provided username was not found");
                return false;
            }
        }
        catch (Exception e){
            LOG.info("FAILED: Unable to verify Credentials for User");
            return false;
        }
    }
    // Update
    public boolean changeLogin(
            ReqChangeLogin data
    ){
        try {
            if(validateUserLogin(
                    data.getUsername(),
                    data.getOldPassword()
            )){
                LOG.info("PROCESS: Changing Password");
                userRepository.updatePasswordByUsername(
                        data.getUsername(),
                        data.getNewPassword()
                );
                LOG.info("PROCESS: Password was changed");
                return true;
            }
            else {
                LOG.warn("FAILED: Incorrect password. Unchanged");
                return false;
            }
        }
        catch (Exception e){
            LOG.error("FAILED: Issue when handling update on database. Password unchanged");
            return false;
        }
    }
}
