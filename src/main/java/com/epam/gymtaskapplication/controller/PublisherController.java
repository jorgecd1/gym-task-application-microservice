package com.epam.gymtaskapplication.controller;

import com.epam.gymtaskapplication.model.SystemMessage;
import com.epam.gymtaskapplication.model.TrainerPayload;
import com.epam.gymtaskapplication.model.TrainerWorkload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import request.dto.ReqNewTrainee;
import response.dto.ResLogin;

@RestController
@RequestMapping("/api/sender")
public class PublisherController {

    @Autowired
    private JmsTemplate jmsTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(PublisherController.class);

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestBody SystemMessage systemMessage) {
        try {
            jmsTemplate.convertAndSend("bridgingcode-queue", systemMessage);

            return new ResponseEntity<>("Sent.", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/publishJson")
    public ResponseEntity<ResLogin> publishMessage(@RequestBody ReqNewTrainee data){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String myJsonObject = objectMapper.writeValueAsString(data);
            jmsTemplate.convertAndSend("my-other-q", myJsonObject);

            ResLogin response = new ResLogin();
            response.setMessage(data.getFirstName());
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/publishHours")
    public ResponseEntity<ResLogin> publishHours(
            @RequestBody TrainerWorkload data
    ){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String workload = mapper.writeValueAsString(data);

            jmsTemplate.convertAndSend("trainer-workload",workload);

            ResLogin response = new ResLogin();
            response.setMessage("Success! Hours were sent to ActiveMQ!");
            return new ResponseEntity<>(
                    response,
                    HttpStatus.OK
            );
        }
        catch (Exception e){
            LOG.error("FAILED: Unable to send Workload");
            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
