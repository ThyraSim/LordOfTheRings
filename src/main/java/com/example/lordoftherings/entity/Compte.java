package com.example.lordoftherings.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_compte;
    private String nom_utilisateur;
    private String motDePasse;
    private String date_creation;
    private boolean premium;
    private Integer nombre_personnages;

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL)
    private List<Personnage> personnages;

    public Compte() {
    }

    public Compte(Integer id_compte, String nom_utilisateur, String motDePasse, String date_creation, boolean premium, Integer nombre_personnages, List<Personnage> personnages) {
        this.id_compte = id_compte;
        this.nom_utilisateur = nom_utilisateur;
        this.date_creation = date_creation;
        this.premium = premium;
        this.nombre_personnages = nombre_personnages;
        this.motDePasse = motDePasse;
        this.personnages = personnages;
    }

    public Integer getId_compte() {
        return id_compte;
    }

    public void setId_compte(Integer id_compte) {
        this.id_compte = id_compte;
    }

    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public Integer getNombre_personnages() {
        return nombre_personnages;
    }

    public void setNombre_personnages(Integer nombre_personnages) {
        this.nombre_personnages = nombre_personnages;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<Personnage> getPersonnages() {
        return personnages;
    }

    public void setPersonnages(List<Personnage> personnages) {
        this.personnages = personnages;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id_compte=" + id_compte +
                ", nom_utilisateur='" + nom_utilisateur + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", date_creation='" + date_creation + '\'' +
                ", premium=" + premium +
                ", nombre_personnages=" + nombre_personnages +
                ", personnages=" + personnages +
                '}';
    }
}
