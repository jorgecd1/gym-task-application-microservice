package com.epam.gymtaskapplication.dao;

import com.epam.gymtaskapplication.model.Trainee;
import com.epam.gymtaskapplication.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee,Integer> {
    @Query("SELECT t FROM Trainee t WHERE t.user.username = ?1")
    Optional<Trainee> findByUserUsername(String username);
}
