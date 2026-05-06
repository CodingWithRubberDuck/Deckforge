package com.HolgersDream.Deckforge.service;

import com.HolgersDream.Deckforge.controller.AuthRequest;
import com.HolgersDream.Deckforge.domain.AuthSessionUser;
import com.HolgersDream.Deckforge.domain.Role;
import com.HolgersDream.Deckforge.domain.User;
import com.HolgersDream.Deckforge.domain.interfaces.IAuthUserRepository;
import com.HolgersDream.Deckforge.exceptions.LoginValidationException;
import com.HolgersDream.Deckforge.exceptions.RegisterValidationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

    private final IAuthUserRepository repository;
    private final RegisterValidationService registerValidation;

    public AuthUserService(IAuthUserRepository repository, RegisterValidationService registerValidation){
        this.repository = repository;
        this.registerValidation = registerValidation;
    }

    public void checkRegister(AuthRequest authRequest){
        User user;
        try {
            user = new User(authRequest.getName(), authRequest.getEmail(), authRequest.getPassword(), Role.USER);
        } catch (IllegalArgumentException iae){
            throw new RegisterValidationException(iae.getMessage());
        }

        registerValidation.validate(user);
        if (repository.findByEmail(user.getEmail()).isPresent()){
            throw new RegisterValidationException("Den indtastede email er allerede i brug");
        }
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.changePassword(hashed);
        repository.saveNewUser(user);
    }

    public AuthSessionUser checkLogin(AuthRequest authRequest){
        User user = repository.findByEmail(authRequest.getEmail()).orElse(null);

        if (user != null && BCrypt.checkpw(authRequest.getPassword(), user.getPassword())){
            return new AuthSessionUser(user.getUserId(), user.getName(), user.getEmail(), user.getRole());
        }
        throw new LoginValidationException("Email eller kodeord er ikke korrekt");
    }

}
