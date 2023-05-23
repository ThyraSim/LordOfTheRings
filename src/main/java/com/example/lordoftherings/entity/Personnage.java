package com.example.lordoftherings.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Personnage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_personnage;
    private String nom_personnage;
    private Integer id_classe;
    private Integer id_arme;
    private String date_creation;
    private Integer niveau;
    private Integer id_compte;

    public Personnage() {
    }

    public Personnage(Integer id_personnage, String nom_personnage, Integer id_classe, Integer id_arme, String date_creation, Integer niveau, Integer id_compte) {
        this.id_personnage = id_personnage;
        this.nom_personnage = nom_personnage;
        this.id_classe = id_classe;
        this.id_arme = id_arme;
        this.date_creation = date_creation;
        this.niveau = niveau;
        this.id_compte = id_compte;
    }

    public Integer getId_personnage() {
        return id_personnage;
    }

    public void setId_personnage(Integer id_personnage) {
        this.id_personnage = id_personnage;
    }

    public String getNom_personnage() {
        return nom_personnage;
    }

    public void setNom_personnage(String nom_personnage) {
        this.nom_personnage = nom_personnage;
    }

    public Integer getId_classe() {
        return id_classe;
    }

    public void setId_classe(Integer id_classe) {
        this.id_classe = id_classe;
    }

    public Integer getId_arme() {
        return id_arme;
    }

    public void setId_arme(Integer id_arme) {
        this.id_arme = id_arme;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    public Integer getId_compte() {
        return id_compte;
    }

    public void setId_compte(Integer id_compte) {
        this.id_compte = id_compte;
    }

    @Override
    public String toString() {
        return "Personnage{" +
                "id_personnage=" + id_personnage +
                ", nom_personnage='" + nom_personnage + '\'' +
                ", id_classe=" + id_classe +
                ", id_arme=" + id_arme +
                ", date_creation='" + date_creation + '\'' +
                ", niveau=" + niveau +
                ", id_compte=" + id_compte +
                '}';
    }
}
