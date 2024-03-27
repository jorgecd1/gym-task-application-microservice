package com.epam.gymtaskapplication.model;

import lombok.Data;

import java.util.Date;

@Data
public class TrainerWorkload {
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private Date trainingDate;
    private int trainingDuration;
    private ActionType actionType;
}
