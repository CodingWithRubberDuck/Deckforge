package com.HolgersDream.Deckforge.repository;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.domain.interfaces.IUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MySQLUserRepository implements IUserRepository {

    private final DatabaseConfig databaseConfig;

    public MySQLUserRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
}
