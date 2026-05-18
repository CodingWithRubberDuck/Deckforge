package com.HolgersDream.Deckforge.domain;

public class AuthSessionUser {
    private final int userId;
    private final String name;
    private final String email;
    private final Role role;

    public AuthSessionUser(int userId, String name, String email, Role role){
        if (userId <= 0){
            throw new IllegalArgumentException("Sessionen har ikke et gyldigt id");
        }
        this.userId = userId;

        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Sessionen har ikke et gyldigt navn");
        }
        this.name = name;

        if (email == null || email.isBlank()){
            throw new IllegalArgumentException("Sessionen har ikke en gyldig email");
        }
        this.email = email;

        if (role == null){
            throw new IllegalArgumentException("Sessionen har ikke en gyldig rolle");
        }
        this.role = role;
    }

    //Getters

    //Skal bruges til at se kortsamling for bestemt user
    public int getUserId() {
        return userId;
    }

}
