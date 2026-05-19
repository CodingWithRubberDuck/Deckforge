package com.HolgersDream.Deckforge.domain.interfaces;

import com.HolgersDream.Deckforge.domain.Card;
import com.HolgersDream.Deckforge.domain.Deck;
import com.HolgersDream.Deckforge.domain.OwnedCard;

import java.util.List;
import java.util.Optional;

public interface ICardRepository {
    Optional<List<Deck>> findDecksById(int userId);
    void addDeckToUser(Deck newDeck);
    List<OwnedCard> getUserCardCollection(int userId);
    List<OwnedCard> findCardByName(int userId, String name);
    void addCardToCollection (OwnedCard ownedCard);
    List<Card> getAllCards();
    Card getCardById(int cardId);
}
