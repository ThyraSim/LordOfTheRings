package com.example.lordoftherings.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Classe représentant une classe dans le jeu "Seigneur des Anneaux".
 */
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
    @OneToMany(mappedBy = "classe",fetch = FetchType.EAGER)
    private List<Personnage> personnages = new ArrayList<>();

    public Classes(){
    }

    /**
     * Constructeur de la classe Classes.
     *
     * @param nom_classe    le nom de la classe
     * @param puissance     la puissance de la classe
     * @param agilete       l'agilité de la classe
     * @param constitution  la constitution de la classe
     * @param intelligence  l'intelligence de la classe
     * @param image         l'image représentant la classe
     */

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

    /**
     * Ces fonctions permet de comparer les classes par tous leurs attributs
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classes classes = (Classes) o;
        return puissance == classes.puissance && agilete == classes.agilete && constitution == classes.constitution && intelligence == classes.intelligence && Objects.equals(id_classe, classes.id_classe) && Objects.equals(nom_classe, classes.nom_classe) && Objects.equals(image, classes.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_classe, nom_classe, puissance, agilete, constitution, intelligence, image);
    }
}
