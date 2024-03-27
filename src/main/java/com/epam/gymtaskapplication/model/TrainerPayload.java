package com.epam.gymtaskapplication.model;

import lombok.Data;

@Data
public class TrainerPayload {
    private String username;
    private String message;
    private int totalMinutes;
    private ActionType actionType;
}
