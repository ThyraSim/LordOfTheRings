package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des comptes.
 */
@Service
public class CompteServiceImpl implements CompteService {

    private CompteRepository compteRepository;

    @Autowired
    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    /**
     * Récupère tous les comptes.
     *
     * @return la liste des comptes
     */
    @Override
    public List<Compte> findAll() {
        return this.compteRepository.findAll();
    }

    /**
     * Supprime un compte en fonction de son identifiant.
     *
     * @param id_compte l'identifiant du compte à supprimer
     */
    @Override
    public void delete(Integer id_compte) {
        compteRepository.deleteById(id_compte);
    }

    /**
     * Récupère un compte en fonction de son identifiant.
     *
     * @param id_compte l'identifiant du compte à récupérer
     * @return le compte correspondant à l'identifiant
     * @throws RuntimeException si le compte n'est pas trouvé
     */
    @Override
    public Compte findById(Integer id_compte) {
        Optional<Compte> compte = compteRepository.findById(id_compte);
        Compte tempCompte = null;
        // S'il trouve le compte, il le récupère
        if (compte.isPresent()) {
            tempCompte = compte.get();
        } else {
            throw new RuntimeException("Le compte non trouvé -" + id_compte);
        }
        return tempCompte;
    }

    /**
     * Enregistre un compte.
     *
     * @param compte le compte à enregistrer
     * @return le compte enregistré
     */
    @Override
    public Compte save(Compte compte) {
        return this.compteRepository.save(compte);
    }

    /**
     * Récupère un compte avec ses personnages en fonction de son identifiant.
     *
     * @param compteId l'identifiant du compte à récupérer avec les personnages
     * @return le compte correspondant à l'identifiant avec les personnages associés
     */
    @Override
    public Compte findCompteWithPersonnagesById(Integer compteId) {
        return compteRepository.findCompteWithPersonnagesById(compteId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Compte> rechercheComptePrenium() {
        return compteRepository.findCompteByPremiumIsTrue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Compte> rechercheNomUtilisateurContenant(String saisi) {
        return compteRepository.findByNom_utilisateur(saisi);
    }
}
