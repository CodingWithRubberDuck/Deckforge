package com.HolgersDream.Deckforge.service;

import com.HolgersDream.Deckforge.controller.EventRequest;
import com.HolgersDream.Deckforge.domain.AuthSessionUser;
import com.HolgersDream.Deckforge.domain.Event;
import com.HolgersDream.Deckforge.domain.Role;
import com.HolgersDream.Deckforge.domain.interfaces.IEventRepository;
import com.HolgersDream.Deckforge.exceptions.EventValidationException;
import com.HolgersDream.Deckforge.exceptions.NotAuthorizedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final IEventRepository repository;

    public EventService(IEventRepository repository){
        this.repository = repository;
    }

    public Optional<List<Event>> findTheComingEvents(){
        return repository.findComingEvents();
    }

    public Optional<List<Event>> findTheRegisteredEvents(int userId){
        return repository.findRegisteredEvents(userId);
    }

    public void checkAddEvent(EventRequest eventRequest, AuthSessionUser sessionUser){
        Role role = sessionUser.getRole();
        if (role != Role.ORGANIZER && role != Role.ADMIN) {
            throw new NotAuthorizedException("Du har ikke tilladelse til at udføre denne handling");
        }
        Event newEvent;
        try {
            newEvent = new Event(0, sessionUser.getUserId(), eventRequest.getEventName(), eventRequest.getMaxSlots(), eventRequest.getMaxSlots(), eventRequest.getLocation(), eventRequest.getStartTime(), eventRequest.getDate());
        } catch (IllegalArgumentException iae){
            throw new EventValidationException(iae.getMessage());
        }
        repository.addNewEvent(newEvent);
    }
}
