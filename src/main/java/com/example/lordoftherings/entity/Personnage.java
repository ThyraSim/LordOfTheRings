package com.example.lordoftherings.entity;

import com.example.lordoftherings.service.ArmeService;
import com.example.lordoftherings.service.ArmeServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Personnage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_personnage;
    private String nom_personnage;
    private String date_creation;
    private Integer niveau;



    @ManyToOne
    @JoinColumn(name="id_compte")
    private Compte compte;

    @ManyToOne
    @JoinColumn(name="id_arme")
    private Arme arme;


    @ManyToOne
    @JoinColumn(name="id_classe")
    private Classes classe;

    public Personnage() {
    }

    public Personnage(String nom_personnage, String date_creation, Integer niveau, Compte compte, Arme arme, Classes classe) {

        this.nom_personnage = nom_personnage;
        this.date_creation = date_creation;
        this.niveau = niveau;
        this.compte = compte;
        this.arme = arme;
        this.classe = classe;
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

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    @Override
    public String toString() {
        return "Personnage{" +
                "id_personnage=" + id_personnage +
                ", nom_personnage='" + nom_personnage + '\'' +
                ", date_creation='" + date_creation + '\'' +
                ", niveau=" + niveau +

                ", arme=" + arme +
                ", classe=" + classe +
                '}';
    }

    public Arme getArme() {
        return arme;
    }

    public void setArme(Arme arme) {
        this.arme = arme;
    }

    public Classes getClasse() {
        return classe;
    }

    public void setClasse(Classes classe) {
        this.classe = classe;
    }
}
