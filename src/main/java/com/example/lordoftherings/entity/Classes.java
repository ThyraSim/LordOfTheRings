package com.example.lordoftherings.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "classe")
    private List<Personnage> personnages = new ArrayList<>();

    public Classes(){
    }

    public Classes( String nom_classe, int puissance, int agilete, int constitution, int intelligence, String image) {

        this.nom_classe = nom_classe;
        this.puissance = puissance;
        this.agilete = agilete;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.image = image;

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
        this.agilete = agilete;
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

    public List<Personnage> getPersonnages() {
        return personnages;
    }

    public void setPersonnages(List<Personnage> personnages) {
        this.personnages = personnages;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
