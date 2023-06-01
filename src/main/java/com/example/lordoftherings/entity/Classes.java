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
    private int agilete;
    private int constitution;
    private int intelligence;

    public Classes(){

    }

    public Classes(Integer id_classe, String nom_classe, int puissance, int agilete, int constitution, int intelligence) {
        this.id_classe = id_classe;
        this.nom_classe = nom_classe;
        this.puissance = puissance;
        this.agilete = agilete;
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

    public int getPuissance() {
        return puissance;
    }

    public void setPuissance(int Puissance) {
        this.puissance = Puissance;
    }

    public int getAgilete() {
        return agilete;
    }

    public void setAgilete(int agilete) {
        this.agilete = Classes.this.agilete;
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
                ", agilete=" + agilete +
                ", constitution=" + constitution +
                ", intelligence=" + intelligence +
                '}';
    }
}
