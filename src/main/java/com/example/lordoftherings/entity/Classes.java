package com.example.lordoftherings.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_classe;
    private String nom_classe;
    private int puissance;
    private int agileté;
    private int constitution;
    private int intelligence;

    public Classes(){

    }

    public Classes(Integer id_classe, String nom_classe, int puissance, int agileté, int constitution, int intelligence) {
        this.id_classe = id_classe;
        this.nom_classe = nom_classe;
        this.puissance = puissance;
        this.agileté = agileté;
        this.constitution = constitution;
        this.intelligence = intelligence;
    }

    public Integer getId_classe() {
        return id_classe;
    }

    public void setId_classe(Integer id_classe) {
        this.id_classe = id_classe;
    }

    public String getNom_classe() {
        return nom_classe;
    }

    public void setNom_classe(String nom_classe) {
        this.nom_classe = nom_classe;
    }

    public int getForce() {
        return puissance;
    }

    public void setForce(int force) {
        this.puissance = force;
    }

    public int getAgileté() {
        return agileté;
    }

    public void setAgileté(int agileté) {
        this.agileté = agileté;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "id_classe=" + id_classe +
                ", nom_classe='" + nom_classe + '\'' +
                ", puissance=" + puissance +
                ", agileté=" + agileté +
                ", constitution=" + constitution +
                ", intelligence=" + intelligence +
                '}';
    }
}
