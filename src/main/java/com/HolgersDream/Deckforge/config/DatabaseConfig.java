package com.HolgersDream.Deckforge.config;

import com.HolgersDream.Deckforge.exceptions.DatabaseConnectionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    public Connection getConnection(){
        try {
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException sqle) {
            throw new DatabaseConnectionException("Der er ikke forbindelse til databasen", sqle);
        }
    }
}
