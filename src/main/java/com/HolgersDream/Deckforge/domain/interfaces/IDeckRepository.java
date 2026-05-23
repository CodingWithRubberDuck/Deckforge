package com.HolgersDream.Deckforge.domain.interfaces;

import com.HolgersDream.Deckforge.domain.Deck;
import com.HolgersDream.Deckforge.domain.DeckCard;

import java.util.List;
import java.util.Optional;

public interface IDeckRepository {
    List<Deck> findDecksById(int userId);
    void addDeckToUser(Deck newDeck);
    Optional<Deck> findDeckById(int deckId);
    List<DeckCard> findDeckCards(int deckId);
    void addCardToDeck(int cardId, int deckId, boolean isCommander);
    Optional<DeckCard> findDeckCardById(int deckContainId);
    void deleteCardFromDeck(int deckContainId);
}
