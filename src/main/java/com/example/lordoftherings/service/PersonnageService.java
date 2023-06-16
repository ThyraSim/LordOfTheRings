package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.repository.PersonnageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface du service de gestion des personnages.
 */
@Service
public interface PersonnageService {
    /**
     * Récupère tous les personnages.
     *
     * @return la liste des personnages
     */
    List<Personnage> findAll();

    /**
     * Supprime un personnage en fonction de son identifiant.
     *
     * @param id_personnage l'identifiant du personnage à supprimer
     */
    void delete(Integer id_personnage);

    /**
     * Récupère un personnage en fonction de son identifiant.
     *
     * @param id_personnage l'identifiant du personnage à récupérer
     * @return le personnage correspondant à l'identifiant
     * @throws RuntimeException si le personnage n'est pas trouvé
     */
    Personnage findById(Integer id_personnage);

    /**
     * Enregistre un personnage.
     *
     * @param personnage le personnage à enregistrer
     * @return le personnage enregistré
     */
    Personnage save(Personnage personnage);

    /**
     * Récupère un personnage avec son arme, ses classes et son compte en fonction de son identifiant.
     *
     * @param id_personnage l'identifiant du personnage à récupérer avec l'arme, les classes et le compte associés
     * @return le personnage correspondant à l'identifiant avec l'arme, les classes et le compte associés
     */
    Personnage findByIdWithArmeAndClassesAndCompte(Integer id_personnage);
}
