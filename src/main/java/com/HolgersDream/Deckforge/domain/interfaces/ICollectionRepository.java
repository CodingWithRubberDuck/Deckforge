package com.HolgersDream.Deckforge.domain.interfaces;

import com.HolgersDream.Deckforge.domain.OwnedCard;

import java.util.List;
import java.util.Optional;

public interface ICollectionRepository {
    List<OwnedCard> getUserCardCollection(int userId);
    List<OwnedCard> findOwnedCardByName(int userId, String name);
    void addCardToCollection (OwnedCard ownedCard);
    Optional<OwnedCard> getOwnedCardById(int ownedCardId);
    void removeOwnedCard(int ownedCardId, int userId);
}
