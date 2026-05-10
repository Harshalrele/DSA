package com.boberclinic.backend.service;

// Runs code once when the backend starts.
import org.springframework.boot.CommandLineRunner;

// Used here for a small database cleanup query.
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

// Keeps old local database files compatible after role changes.
@Component
public class SchemaFixer implements CommandLineRunner {
    // Direct SQL access for H2.
    private final JdbcTemplate jdbcTemplate;

    // Spring provides JdbcTemplate here.
    public SchemaFixer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Startup cleanup.
    @Override
    public void run(String... args) {
        // Find old CHECK constraints on the user_account table.
        List<String> constraints = jdbcTemplate.queryForList(
                "select constraint_name from information_schema.table_constraints " +
                        "where table_name = 'USER_ACCOUNT' and constraint_type = 'CHECK'",
                String.class
        );

        // Remove old constraints so the new role value can be saved.
        for (String constraint : constraints) {
            jdbcTemplate.execute("alter table user_account drop constraint " + constraint);
        }
    }
}
