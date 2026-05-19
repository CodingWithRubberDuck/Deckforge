package com.HolgersDream.Deckforge.service;

import com.HolgersDream.Deckforge.domain.interfaces.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final IUserRepository repository;

    public UserService(IUserRepository repository){
        this.repository = repository;
    }

    public void markingToDelete(int userId){
        repository.markToDeleteById(userId);
    }
}
