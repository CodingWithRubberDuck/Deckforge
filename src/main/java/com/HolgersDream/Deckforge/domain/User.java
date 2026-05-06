package com.HolgersDream.Deckforge.domain;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private Role role;


    //Base Chain Constructor kald
    private User(String name, String email, Role role){
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
        this(name, email, role);

        if (userId < 1){
            throw new IllegalArgumentException("Brugeren har ikke et gyldigt id");
        }
        this.userId = userId;

        if (password == null || password.isBlank()){
            throw new IllegalArgumentException("Brugeren har ikke et gyldigt kodeord");
        }
        this.password = password;
    }



    //Konstruktør uden Id
    public User(String name, String email, String password, Role role){
        //Chain constructor kald
        this(name, email, role);

        if (password == null || password.isBlank()){
            throw new IllegalArgumentException("Brugeren har ikke et gyldigt kodeord");
        }
        this.password = password;
    }


    //Konstruktør uden Kodeord
    public User(int userId, String name, String email, Role role){
        //Chain constructor kald
        this(name, email, role);

        if (userId < 1){
            throw new IllegalArgumentException("Brugeren har ikke et gyldigt id");
        }
        this.userId = userId;
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
