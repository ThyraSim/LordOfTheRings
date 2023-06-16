package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArmeService {
    /**
     * Récupère toutes les armes.
     *
     * @return Liste des armes
     */
    List<Arme> findAll();

    /**
     * Supprime une arme en utilisant son ID.
     *
     * @param id_arme ID de l'arme à supprimer
     */
    void delete(Integer id_arme);

    /**
     * Recherche une arme en utilisant son ID.
     *
     * @param id_arme ID de l'arme à rechercher
     * @return Arme correspondante, ou null si non trouvée
     */
    Arme findById(Integer id_arme);

    /**
     * Enregistre une nouvelle arme ou met à jour une arme existante.
     *
     * @param arme Arme à enregistrer ou mettre à jour
     * @return Arme enregistrée ou mise à jour
     */
    Arme save(Arme arme);

    /**
     * Vérifie si une arme est utilisée par au moins un personnage.
     *
     * @param id_arme ID de l'arme à vérifier
     * @return true si l'arme est utilisée, false sinon
     */
    boolean isArmeInUse(Integer id_arme);

    /**
     * Recherche les armes dont le dommage se situe entre deux valeurs.
     *
     * @param minDmg Valeur minimale du dommage
     * @param maxDmg Valeur maximale du dommage
     * @return Liste des armes correspondantes
     */
    List<Arme> findArmeByDommageBetween(int minDmg, int maxDmg);

    /**
     * Recherche les armes associées à une classe spécifique.
     *
     * @param classe Nom de la classe
     * @return Liste des armes correspondantes
     */
    List<Arme> findArmesByClasse(String classe);

    /**
     * Recherche les armes selon le type de statistique.
     *
     * @param typeStat Le type de statistique des armes à rechercher.
     * @return La liste des armes correspondantes.
     */
    List<Arme> rechercheArmeTypeStat(String typeStat);
}
