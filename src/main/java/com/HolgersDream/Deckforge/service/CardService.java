package com.HolgersDream.Deckforge.service;

import com.HolgersDream.Deckforge.controller.DeckRequest;
import com.HolgersDream.Deckforge.domain.Card;
import com.HolgersDream.Deckforge.domain.Deck;
import com.HolgersDream.Deckforge.domain.OwnedCard;
import com.HolgersDream.Deckforge.domain.interfaces.ICardRepository;
import com.HolgersDream.Deckforge.exceptions.NewDeckValidationException;
import com.HolgersDream.Deckforge.exceptions.NoCardFoundException;
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


    public List<OwnedCard> getUserCollection(int userId) {
        return repository.getUserCardCollection(userId);
    }

    public List<OwnedCard> findOwnedCardByName(int userId, String name) {
        return repository.findOwnedCardByName(userId, name);
    }

    public void addCardToCollection(OwnedCard ownedCard) {
        repository.addCardToCollection(ownedCard);
    }

    public List<Card> getAllCards() {
        return repository.getAllCards();
    }

    public Card getCardById(int cardId) {
        Optional<Card> card = repository.getCardById(cardId);
        if (card.isPresent()) {
            return card.get();
        }
        throw new NoCardFoundException("Det søgte kort blev ikke fundet");
    }

    public OwnedCard getOwnedCardById(int ownedCardId) {
        Optional<OwnedCard> result = repository.getOwnedCardById(ownedCardId);
        if (result.isPresent()) {
            return result.get();
        }
        throw new NoCardFoundException("Det søgte egede kort blev ikke fundet");
    }

    public void removeCardFromCollection(int ownedCardId, int userId) {
        repository.removeOwnedCard(ownedCardId, userId);
    }

    public List<Card> findCardByName(String name){
        return repository.findCardByName(name);
    }
}
