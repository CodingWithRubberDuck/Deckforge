package com.HolgersDream.Deckforge;


import com.HolgersDream.Deckforge.domain.User;
import com.HolgersDream.Deckforge.domain.interfaces.IAuthUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeAuthUserRepository implements IAuthUserRepository {

    private List<User> users = new ArrayList<>();

    @Override
    public Optional<User> findByEmail(String email){
        for (User user : users){
            if (user.getEmail().equals(email)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public void saveNewUser(User user){
        //normalt auto-genereres userId af auto-increment,
        //men da det ikke er tilfældet her, bliver vi nødt til at gøre det manuelt
        //da der ellers kastes en fejl med ugyldigt id grundet domæne-regler
        User userWithId = new User(1, user.getName(), user.getEmail(), user.getPassword(), user.getRole());
        users.add(userWithId);

    }

    @Override
    public void updateLastLogin(int userId) {
        //Something happens
    }

}
