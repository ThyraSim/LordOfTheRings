package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.repository.PersonnageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des personnages.
 */
@Service
public class PersonnageServiceImpl implements PersonnageService {
    private PersonnageRepository personnageRepository;

    public PersonnageServiceImpl(PersonnageRepository personnageRepository) {
        this.personnageRepository = personnageRepository;
    }

    /**
     * Récupère tous les personnages.
     *
     * @return la liste des personnages
     */
    @Override
    public List<Personnage> findAll() {
        return this.personnageRepository.findAll();
    }

    /**
     * Supprime un personnage en fonction de son identifiant.
     *
     * @param id_personnage l'identifiant du personnage à supprimer
     */
    @Override
    public void delete(Integer id_personnage) {
        personnageRepository.deleteById(id_personnage);
    }

    /**
     * Récupère un personnage en fonction de son identifiant.
     *
     * @param id_personnage l'identifiant du personnage à récupérer
     * @return le personnage correspondant à l'identifiant
     * @throws RuntimeException si le personnage n'est pas trouvé
     */
    @Override
    public Personnage findById(Integer id_personnage) {
        Optional<Personnage> personnage = personnageRepository.findById(id_personnage);
        Personnage tempPersonnage = null;
        //s'il trouve le personnage => il va le chercher
        if (personnage.isPresent()) {
            tempPersonnage = personnage.get();
        } else {
            throw new RuntimeException("Le personnage non trouvé -" + id_personnage);
        }
        return tempPersonnage;
    }

    /**
     * Enregistre un personnage.
     *
     * @param personnage le personnage à enregistrer
     * @return le personnage enregistré
     */
    @Override
    public Personnage save(Personnage personnage) {
        return this.personnageRepository.save(personnage);
    }

    /**
     * Récupère un personnage avec son arme, ses classes et son compte en fonction de son identifiant.
     *
     * @param id_personnage l'identifiant du personnage à récupérer avec l'arme, les classes et le compte associés
     * @return le personnage correspondant à l'identifiant avec l'arme, les classes et le compte associés
     */
    @Override
    public Personnage findByIdWithArmeAndClassesAndCompte(Integer id_personnage) {
        return personnageRepository.findByIdWithArmeAndClassesAndCompte(id_personnage);
    }
}
