package com.boberclinic.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Interface for handling user data operations in the database
public interface UserRepository extends JpaRepository<UserAccount, Long> {

    // Retrieve a single user record based on their email address
    Optional<UserAccount> findByEmail(String email);

    // Verify if a specific email address is already registered
    boolean existsByEmail(String email);

    // Filter and return a list of users by their account role
    List<UserAccount> findByRole(Role role);
}