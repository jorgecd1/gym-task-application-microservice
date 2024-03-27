package com.epam.gymtaskapplication.dao;

import com.epam.gymtaskapplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.firstName = ?1 AND u.lastName = ?2")
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    @Modifying
    @Query("UPDATE User u SET u.password = ?2 WHERE u.username = ?1")
    void updatePasswordByUsername(String username, String newPassword);
}
