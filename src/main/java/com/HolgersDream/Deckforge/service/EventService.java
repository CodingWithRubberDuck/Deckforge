package com.HolgersDream.Deckforge.service;

import com.HolgersDream.Deckforge.domain.interfaces.IEventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final IEventRepository repository;

    public EventService(IEventRepository repository){
        this.repository = repository;
    }
}
