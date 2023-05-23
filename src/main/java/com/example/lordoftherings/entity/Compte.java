package com.example.lordoftherings.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_compte;
    private String nom_utilisateur;
    private String date_creation;
    private boolean premium;
    private Integer nombre_personnages;
}
