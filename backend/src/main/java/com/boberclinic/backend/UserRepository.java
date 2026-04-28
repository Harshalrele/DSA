package com.boberclinic.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// This repository gives simple database commands for users.
public interface UserRepository extends JpaRepository<UserAccount, Long> {

    // Finds one account by email for login.
    Optional<UserAccount> findByEmail(String email);

    // Checks if an email is already used during registration.
    boolean existsByEmail(String email);

    // Gets all users with a selected role, for example all doctors.
    List<UserAccount> findByRole(Role role);
}
