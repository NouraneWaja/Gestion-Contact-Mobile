package com.example.gestioncontact;

public class User {
    int id;
    String nom;
    String password;

    public User(String nom, String password) {
        this.nom = nom;
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "nom='" + nom + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
