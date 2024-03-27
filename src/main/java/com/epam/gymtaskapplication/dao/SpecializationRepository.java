package com.epam.gymtaskapplication.dao;

import com.epam.gymtaskapplication.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization,Integer> {
    @Query(
            "SELECT s FROM " +
            "Specialization s " +
            "WHERE s.name = ?1")
    Optional<Specialization> findByName(String name);
}
