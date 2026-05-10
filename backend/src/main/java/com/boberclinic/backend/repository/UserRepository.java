package com.boberclinic.backend.repository;

// User model and role enum.
import com.boberclinic.backend.model.Role;
import com.boberclinic.backend.model.UserAccount;

// JpaRepository provides common database methods.
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// User database queries.
public interface UserRepository extends JpaRepository<UserAccount, Long> {
    // Find one user by email for login.
    Optional<UserAccount> findByEmail(String email);

    // Check if an email is already saved.
    boolean existsByEmail(String email);

    // Find users by account role.
    List<UserAccount> findByRole(Role role);
}
