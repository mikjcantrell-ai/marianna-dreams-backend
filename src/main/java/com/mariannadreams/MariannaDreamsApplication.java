package com.mariannadreams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Marianna Dreams Spring Boot REST API.
 *
 * <p>Runs on port 8082 (see application.properties).
 * SQLite database file: {@code marianna.db} (created automatically on first run).
 */
@SpringBootApplication
public class MariannaDreamsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MariannaDreamsApplication.class, args);
    }
}
