package com.epam.gymtaskapplication.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "SPECIALIZATIONS")
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @Column(name = "name")
    private String name;
}
