package com.HolgersDream.Deckforge.service;

import com.HolgersDream.Deckforge.domain.Event;
import com.HolgersDream.Deckforge.domain.interfaces.IEventRepository;
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
}
