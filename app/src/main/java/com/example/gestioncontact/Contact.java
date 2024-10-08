package com.example.gestioncontact;

public class Contact {
    String nom;
    String pseudo;
    String phone;

    public Contact(String nom, String pseudo, String phone) {
        this.nom = nom;
        this.pseudo = pseudo;
        this.phone = phone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "nom='" + nom + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
