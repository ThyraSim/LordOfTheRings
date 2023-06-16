package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface du service de gestion des comptes.
 */
@Service
public interface CompteService {
    /**
     * Récupère la liste de tous les comptes.
     *
     * @return la liste de tous les comptes
     */
    List<Compte> findAll();

    /**
     * Supprime un compte par son identifiant.
     *
     * @param id_compte l'identifiant du compte à supprimer
     */
    void delete(Integer id_compte);

    /**
     * Recherche un compte par son identifiant.
     *
     * @param id_compte l'identifiant du compte à rechercher
     * @return le compte correspondant
     * @throws RuntimeException si le compte n'est pas trouvé
     */
    Compte findById(Integer id_compte);

    /**
     * Enregistre un compte.
     *
     * @param compte le compte à enregistrer
     * @return le compte enregistré
     */
    Compte save(Compte compte);

    /**
     * Recherche un compte avec ses personnages par son identifiant.
     *
     * @param id_compte l'identifiant du compte à rechercher
     * @return le compte correspondant avec ses personnages
     */
    Compte findCompteWithPersonnagesById(Integer id_compte);

    /**
     * Recherche les comptes premium.
     *
     * @return La liste des comptes premium.
     */
    List<Compte> rechercheComptePrenium();


    /**
     * Recherche les comptes dont le nom d'utilisateur contient la chaîne spécifiée.
     *
     * @param saisi La chaîne à rechercher dans les noms d'utilisateur des comptes.
     * @return La liste des comptes dont le nom d'utilisateur contient la chaîne spécifiée.
     */
    List<Compte> rechercheNomUtilisateurContenant(String saisi);
}
