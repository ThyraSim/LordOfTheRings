package com.example.lordoftherings.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe représentant une arme dans le jeu "Seigneur des Anneaux".
 */
@Entity
public class Arme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_arme;
    private String nom_arme;
    private double dommage;
    private double portee; // en mètres
    private int precission; // sur 100
    private String type_stat; // str, agi, int

    @JsonIgnore
    @OneToMany(mappedBy = "arme", fetch = FetchType.EAGER)
    private List<Personnage> personnages = new ArrayList<>();

    public Arme() {
    }

    /**
     * Constructeur de la classe Arme.
     *
     * @param nom_arme    le nom de l'arme
     * @param dommage     les points de dommage de l'arme
     * @param portee      la portée de l'arme en mètres
     * @param precission  la précision de l'arme sur 100
     * @param type_stat   le type de statistique de l'arme (str, agi, int)
     */
    public Arme(String nom_arme, double dommage, double portee, int precission, String type_stat) {
        this.nom_arme = nom_arme;
        this.dommage = dommage;
        this.portee = portee;
        this.precission = precission;
        this.type_stat = type_stat;
    }

    public Integer getId_arme() {
        return id_arme;
    }

    public void setId_arme(Integer id_arme) {
        this.id_arme = id_arme;
    }

    public String getNom_arme() {
        return nom_arme;
    }

    public void setNom_arme(String nom_arme) {
        this.nom_arme = nom_arme;
    }

    public double getDommage() {
        return dommage;
    }

    public void setDommage(double dommage) {
        this.dommage = dommage;
    }

    public double getPortee() {
        return portee;
    }

    public void setPortee(double portee) {
        this.portee = portee;
    }

    public int getPrecission() {
        return precission;
    }

    public void setPrecission(int precission) {
        this.precission = precission;
    }

    public String getType_stat() {
        return type_stat;
    }

    public void setType_stat(String type_stat) {
        this.type_stat = type_stat;
    }

    public List<Personnage> getPersonnages() {
        return personnages;
    }

    public void setPersonnages(List<Personnage> personnages) {
        this.personnages = personnages;
    }

    @Override
    public String toString() {
        return "Arme{" +
                "id_arme=" + id_arme +
                ", nom_arme='" + nom_arme + '\'' +
                ", dommage=" + dommage +
                ", portee=" + portee +
                ", precission=" + precission +
                ", type_stat='" + type_stat + '\'' +
                '}';
    }

    /**
     * Ces fonctions permet de comparer les armes par tous leurs attributs
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arme arme = (Arme) o;
        return Double.compare(arme.dommage, dommage) == 0 && Double.compare(arme.portee, portee) == 0 && precission == arme.precission && Objects.equals(id_arme, arme.id_arme) && Objects.equals(nom_arme, arme.nom_arme) && Objects.equals(type_stat, arme.type_stat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_arme, nom_arme, dommage, portee, precission, type_stat);
    }
}
