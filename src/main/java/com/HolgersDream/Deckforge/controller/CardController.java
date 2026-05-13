package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CardController {

    private final CardService service;

    public CardController(CardService service, DatabaseConfig databaseConfig) {
        this.service = service;
    }

    @GetMapping("/deck/search")
    public String searchDeck() {
        return "/deck/search";
    }

    @GetMapping("/collection/search")
    public String searchCollection() {
        return "/collection/search";
    }
}
