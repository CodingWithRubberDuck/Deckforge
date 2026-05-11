package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.service.CardService;
import org.springframework.stereotype.Controller;

@Controller
public class CardController {

    private final CardService service;

    public CardController(CardService service, DatabaseConfig databaseConfig) {
        this.service = service;
    }
}
