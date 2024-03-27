package com.epam.gymtaskapplication.model;

import jakarta.persistence.*;
import lombok.Data;
import response.dto.TrainerInList;

import java.util.*;

@Entity
@Data
@Table(name = "TRAINEES")
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @Column(name = "address")
    private String address;
    @Column(name = "dateOfBirt")
    private Date dateOfBirth;

    @OneToOne
    private User user;
}
