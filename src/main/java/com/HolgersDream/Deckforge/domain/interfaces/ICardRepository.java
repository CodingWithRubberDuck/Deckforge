package com.HolgersDream.Deckforge.domain.interfaces;

import com.HolgersDream.Deckforge.domain.Deck;

import java.util.List;
import java.util.Optional;

public interface ICardRepository {
    Optional<List<Deck>> findDecksById(int userId);
    void addDeckToUser(Deck newDeck);
}
