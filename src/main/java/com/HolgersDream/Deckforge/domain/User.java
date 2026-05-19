package com.HolgersDream.Deckforge.domain;

import java.time.LocalDate;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private Role role;


    //Base Chain Constructor kald
    //Constructor uden password (Skal muligvis bruges i fremtiden til at vise brugere som deltager i et event)
    public User(int userId, String name, String email, Role role){
        if (userId <= 0){
            throw new IllegalArgumentException("Brugeren har ikke et gyldigt id");
        }
        this.userId = userId;

        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Brugeren har ikke et gyldigt navn");
        }
        this.name = name.trim();

        if (email == null || email.isBlank()){
            throw new IllegalArgumentException("Brugeren har ikke en gyldig email");
        }
        this.email = email.trim();

        if (role == null){
            throw new IllegalArgumentException("Brugeren har ikke en gyldig rolle");
        }
        this.role = role;
    }



    //Konstruktør med alle værdier
    public User(int userId, String name, String email, String password, Role role) {
        //Chain constructor kald
        this(userId, name, email, role);

        if (password == null || password.isBlank()){
            throw new IllegalArgumentException("Brugeren har ikke et gyldigt kodeord");
        }
        this.password = password;
    }


    // Change with rules
    public void changePassword(String newPassword){
        if (newPassword == null || newPassword.isBlank()){
            throw new IllegalArgumentException();
        }
        this.password = newPassword;
    }




    /// Getters
    public int getUserId(){
        return userId;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Role getRole(){
        return role;
    }
}
