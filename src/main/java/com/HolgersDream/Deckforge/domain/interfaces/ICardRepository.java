package com.HolgersDream.Deckforge.domain.interfaces;

import com.HolgersDream.Deckforge.domain.Card;

import java.util.List;
import java.util.Optional;

public interface ICardRepository {
    List<Card> getAllCards();
    Optional<Card> getCardById(int cardId);
    List<Card> findCardsByName(String name);
}
