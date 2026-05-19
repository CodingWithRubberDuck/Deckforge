package com.HolgersDream.Deckforge.domain.interfaces;

import com.HolgersDream.Deckforge.domain.User;
import java.util.Optional;

public interface IAuthUserRepository {
    Optional<User> findByEmail(String email);
    void saveNewUser(User user);
    void updateLastLogin(int userId);
}
