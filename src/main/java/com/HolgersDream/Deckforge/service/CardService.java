package com.HolgersDream.Deckforge.service;

import com.HolgersDream.Deckforge.domain.interfaces.ICardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final ICardRepository repository;

    public CardService(ICardRepository repository) {
        this.repository = repository;
    }
}
