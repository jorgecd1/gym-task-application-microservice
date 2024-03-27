package com.epam.gymtaskapplication.dao;

import com.epam.gymtaskapplication.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer,Integer> {
    @Query("SELECT t FROM Trainer t WHERE t.user.username = ?1")
    Optional<Trainer> findByUserUsername(String username);
}
