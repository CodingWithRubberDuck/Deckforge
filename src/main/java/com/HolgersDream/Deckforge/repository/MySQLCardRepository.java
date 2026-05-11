package com.HolgersDream.Deckforge.repository;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.domain.interfaces.ICardRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MySQLCardRepository implements ICardRepository {

    private final DatabaseConfig databaseConfig;

    public MySQLCardRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
}
