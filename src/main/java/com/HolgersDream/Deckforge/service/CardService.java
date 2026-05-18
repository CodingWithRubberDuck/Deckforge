package com.HolgersDream.Deckforge.service;

import com.HolgersDream.Deckforge.controller.DeckRequest;
import com.HolgersDream.Deckforge.domain.Deck;
import com.HolgersDream.Deckforge.domain.interfaces.ICardRepository;
import com.HolgersDream.Deckforge.exceptions.NewDeckValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final ICardRepository repository;

    public CardService(ICardRepository repository) {
        this.repository = repository;
    }

    public Optional<List<Deck>> getUserDecks(int userId){
        return repository.findDecksById(userId);
    }

    public void checkAddDeck(DeckRequest deckRequest){
        Deck newDeck;
        try {
            newDeck = new Deck(0, deckRequest.getUserId(), 0, deckRequest.getDeckName(), deckRequest.getFormat());
        } catch (IllegalArgumentException iae){
            throw new NewDeckValidationException(iae.getMessage());
        }
        repository.addDeckToUser(newDeck);
    }
}
