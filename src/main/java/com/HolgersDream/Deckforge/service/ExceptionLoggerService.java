package com.HolgersDream.Deckforge.service;

import com.HolgersDream.Deckforge.domain.interfaces.IExceptionLoggerRepository;
import org.springframework.stereotype.Service;

@Service
public class ExceptionLoggerService {
    private final IExceptionLoggerRepository repository;

    public ExceptionLoggerService(IExceptionLoggerRepository repository){
        this.repository = repository;
    }

    public void checkSaveExceptionMessage(Exception e, String details){
        repository.saveExceptionMessage(e, details);
    }
}
