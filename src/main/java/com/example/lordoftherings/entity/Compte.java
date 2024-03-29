package com.example.lordoftherings.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe représentant un compte utilisateur dans le jeu "Seigneur des Anneaux".
 */
@Entity
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_compte;
    private String nom_utilisateur;
    private String motDePasse;
    private String date_creation;
    private boolean premium;

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Personnage> personnages = new ArrayList<>();

    public Compte() {
    }

    /**
     * Constructeur de la classe Compte.
     *
     * @param nom_utilisateur le nom de l'utilisateur
     * @param motDePasse      le mot de passe de l'utilisateur
     * @param date_creation   la date de création du compte
     * @param premium         indique si le compte est un compte premium ou non
     */

    public Compte( String nom_utilisateur, String motDePasse, String date_creation, boolean premium) {

        this.nom_utilisateur = nom_utilisateur;
        this.date_creation = date_creation;
        this.premium = premium;
        this.motDePasse = motDePasse;

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
                ", personnages=" + personnages +
                '}';
    }

    /**
     * Ces fonctions permet de comparer les comptes par tous leurs attributs
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compte compte = (Compte) o;
        return premium == compte.premium && Objects.equals(id_compte, compte.id_compte) && Objects.equals(nom_utilisateur, compte.nom_utilisateur) && Objects.equals(motDePasse, compte.motDePasse) && Objects.equals(date_creation, compte.date_creation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_compte, nom_utilisateur, motDePasse, date_creation, premium);
    }
}
