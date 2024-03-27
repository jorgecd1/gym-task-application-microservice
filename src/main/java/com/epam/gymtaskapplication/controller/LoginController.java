package com.epam.gymtaskapplication.controller;

import com.epam.gymtaskapplication.dao.UserRepository;
import com.epam.gymtaskapplication.service.LoginService;
import com.epam.gymtaskapplication.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import request.dto.ReqChangeLogin;
import response.dto.ResLogin;
import response.dto.ResNewUser;

@RestController
@RequestMapping("/api")
public class LoginController {
    // fields
    private final LoginService loginService;
    private final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    private static final Utility utility = new Utility();
    // injection
    public LoginController(
            LoginService loginService
    ){
        this.loginService = loginService;
    }
    // Login (Get)
    @GetMapping("/login")
    public ResponseEntity<ResLogin> loginUser(
            @RequestBody ResNewUser data
    ){
        // instantiate call
        utility.instantiateTransaction();
        ResLogin response = new ResLogin();
        LOG.info("CALL: (GET) ?/api/login");
        // validate fields
        if(data.getPassword().isEmpty() || data.getUsername().isEmpty()){
            LOG.warn("FAILED: Password and / or Username was null or missing. Login failed");
            response.setMessage("Missing password or username");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    response,
                    HttpStatus.BAD_REQUEST
            );
        }
        // call service
        if(loginService
                .validateUserLogin(
                    data.getUsername(),
                    data.getPassword()
                )
        ){
            LOG.info("SUCCESS: User is now Logged in");
            response.setMessage("Success! User is now logged in");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    response,
                    HttpStatus.OK
            );
        }
        else {
            LOG.warn("FAILED: Username and Password don't match");
            response.setMessage("Incorrect password or username");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    response,
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
    // Change-Login (Put)
    @PutMapping("/change-login")
    public ResponseEntity<ResLogin> changeLogin(
            @RequestBody ReqChangeLogin data
    ){
        // instantiate call
        utility.instantiateTransaction();
        ResLogin response = new ResLogin();
        LOG.info("CALL: (PUT) ?/api/change-login");
        // validate data
        if(data.getUsername().isEmpty() || data.getOldPassword().isEmpty()){
            LOG.warn("FAILED: Password and / or Username was null or missing. Login failed");
            response.setMessage("Missing password or username");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    response,
                    HttpStatus.BAD_REQUEST
            );
        }
        if(data.getNewPassword().isEmpty()){
            LOG.warn("FAILED: New Password cannot be null");
            response.setMessage("Missing new password");
            utility.closeTransaction();
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
        // call service
        if(loginService.changeLogin(data)){
            LOG.info("SUCCESS: Changed password for user");
            utility.closeTransaction();
            response.setMessage("Password was changed for this User");
            return new ResponseEntity<>(
                    response,
                    HttpStatus.OK
            );
        }
        else {
            LOG.warn("COMPLETED (FAILED): Password unchanged. Incorrect credentials");
            utility.closeTransaction();
            response.setMessage("Password unchanged, incorrect password");
            return new ResponseEntity<>(
                    response,
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
}
