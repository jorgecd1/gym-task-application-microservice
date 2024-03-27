package com.epam.gymtaskapplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "TRAININGS")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @Column(name = "trainingName")
    private String name;
    @Column(name = "trainingDate")
    private Date date;
    @Column(name = "trainingDuration")
    private int duration;

    @ManyToOne
    private Specialization specialization;
    @ManyToOne
    private Trainee trainee;
    @ManyToOne
    private Trainer trainer;
}
