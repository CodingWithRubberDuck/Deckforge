package com.HolgersDream.Deckforge.domain.interfaces;

import com.HolgersDream.Deckforge.domain.Event;
import com.HolgersDream.Deckforge.domain.User;

import java.util.List;
import java.util.Optional;

public interface IEventRepository {
    Optional<List<Event>> findComingEvents();
    Optional<List<Event>> findRegisteredEvents(int userId);
    void addNewEvent(Event event);
    Optional<Event> findEventById(int eventId);
    Optional<List<User>> findEventParticipants(int eventId);
    void addUserToEvent(int userId, int eventId);
    void removeUserFromEvent(int userId, int eventId);
}
