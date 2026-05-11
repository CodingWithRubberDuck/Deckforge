package com.HolgersDream.Deckforge.repository;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.domain.interfaces.IEventRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MySQLEventRepository implements IEventRepository {

    private final DatabaseConfig databaseConfig;

    public MySQLEventRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
}
