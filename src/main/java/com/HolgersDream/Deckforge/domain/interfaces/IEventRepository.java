package com.HolgersDream.Deckforge.domain.interfaces;

import com.HolgersDream.Deckforge.domain.Event;

import java.util.List;
import java.util.Optional;

public interface IEventRepository {
    Optional<List<Event>> findComingEvents();
}
