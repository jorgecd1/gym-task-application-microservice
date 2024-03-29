package com.epam.gymtaskapplication.consumer;

import com.epam.gymtaskapplication.model.SystemMessage;
import com.epam.gymtaskapplication.model.TrainerPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @JmsListener(destination = "bridgingcode-queue")
    public void messageListener(SystemMessage systemMessage) {
        LOGGER.info("Message received! {}", systemMessage);
    }
    @JmsListener(destination = "published-hours")
    public void messageListener(String trainerPayload) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        TrainerPayload payload = mapper.readValue(trainerPayload, TrainerPayload.class);

        LOGGER.info("SUCCESS: Message received. Hours were "
                +payload.getActionType()+
                " to "
                +payload.getUsername());
    }

}
