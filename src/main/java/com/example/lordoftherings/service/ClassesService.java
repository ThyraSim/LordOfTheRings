package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Classes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des classes.
 */
@Service
public interface ClassesService {
    /**
     * Récupère toutes les classes.
     *
     * @return Liste des classes
     */
    List<Classes> findAll();

    /**
     * Supprime une classe par son identifiant.
     *
     * @param id_classe Identifiant de la classe
     */
    void delete(Integer id_classe);

    /**
     * Recherche une classe par son identifiant.
     *
     * @param id_classe Identifiant de la classe
     * @return Classe correspondante
     */
    Classes findById(Integer id_classe);

    /**
     * Enregistre une classe.
     *
     * @param classes Classe à enregistrer
     * @return Classe enregistrée
     */
    Classes save(Classes classes);

    /**
     * Vérifie si une classe est utilisée par des personnages.
     *
     * @param id_classe Identifiant de la classe
     * @return True si la classe est utilisée, sinon False
     */
    boolean isClasseInUse(Integer id_classe);
}
