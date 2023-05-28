package com.example.lordoftherings.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Arme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_arme;
    private String nom_arme;
    private double dommage;
    private double portee; //en mètres
    private int precission; //sur 100
    private String type_stat;//str, agi, int

    public Arme() {
    }

    public Arme(Integer id_arme, String nom_arme, double dommage, double portee, int precission, String type_stat) {
        this.id_arme = id_arme;
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
}
