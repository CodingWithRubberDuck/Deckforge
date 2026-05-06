package com.HolgersDream.Deckforge.controller;

public class AuthRequest {
    private String name;
    private String email;
    private String password;

    // Tom konstruktør til brug i controller/thymeleaf/brugergrænseflade
    public AuthRequest(){
    }

    //Getters
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }

    //Setters
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
