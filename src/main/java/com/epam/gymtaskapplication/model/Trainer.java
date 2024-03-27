package com.epam.gymtaskapplication.model;

import jakarta.persistence.*;
import lombok.Data;
import response.dto.TraineeInList;
import response.dto.TrainerInList;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "TRAINERS")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @ManyToOne
    private Specialization specialization;
    @OneToOne
    private User user;
}
