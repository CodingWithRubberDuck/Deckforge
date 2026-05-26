package com.HolgersDream.Deckforge.service;

import com.HolgersDream.Deckforge.domain.Card;
import com.HolgersDream.Deckforge.domain.OwnedCard;
import com.HolgersDream.Deckforge.domain.interfaces.ICardRepository;
import com.HolgersDream.Deckforge.domain.interfaces.ICollectionRepository;
import com.HolgersDream.Deckforge.exceptions.NoCollectionCardFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    private final ICollectionRepository collectionRepository;
    private final ICardRepository cardRepository;

    public CollectionService(ICollectionRepository collectionRepository , ICardRepository cardRepository) {
        this.collectionRepository = collectionRepository;
        this.cardRepository = cardRepository;
    }

    public List<OwnedCard> getUserCollection(int userId) {
        return collectionRepository.getUserCardCollection(userId);
    }

    public List<OwnedCard> findOwnedCardByName(int userId, String name) {
        return collectionRepository.findOwnedCardByName(userId, name);
    }

    public void addCardToCollection(int userId, int cardId) {
        collectionRepository.addCardByIdToCollection(userId, cardId);
    }

    public List<Card> getAllCards() {
        return cardRepository.getAllCards();
    }

    public Card getCardById(int cardId) {
        Optional<Card> card = cardRepository.getCardById(cardId);
        if (card.isPresent()) {
            return card.get();
        }
        throw new NoCollectionCardFoundException("Det søgte kort blev ikke fundet");
    }

    public OwnedCard getOwnedCardById(int ownedCardId) {
        Optional<OwnedCard> result = collectionRepository.getOwnedCardById(ownedCardId);
        if (result.isPresent()) {
            return result.get();
        }
        throw new NoCollectionCardFoundException("Det søgte egede kort blev ikke fundet");
    }

    public void removeCardFromCollection(int ownedCardId, int userId) {
        collectionRepository.removeOwnedCard(ownedCardId, userId);
    }

    public List<Card> findCardsByName(String name){
        return cardRepository.findCardsByName(name);
    }
}
