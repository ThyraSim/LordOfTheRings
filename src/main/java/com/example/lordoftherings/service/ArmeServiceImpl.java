package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.repository.ArmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service ArmeService.
 */
@Service
public class ArmeServiceImpl implements ArmeService {
    private ArmeRepository armeRepository;

    /**
     * Constructeur de la classe ArmeServiceImpl.
     *
     * @param armeRepository Repository des armes
     */
    @Autowired
    public ArmeServiceImpl(ArmeRepository armeRepository) {
        this.armeRepository = armeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Arme> findAll() {
        return this.armeRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id_arme) {
        armeRepository.deleteById(id_arme);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Arme findById(Integer id_arme) {
        Optional<Arme> arme = armeRepository.findById(id_arme);
        Arme tempArme = null;
        // S'il trouve l'arme, il la récupère
        if (arme.isPresent()) {
            tempArme = arme.get();
        } else {
            throw new RuntimeException("L'arme non trouvée -" + id_arme);
        }
        return tempArme;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Arme save(Arme arme) {
        return this.armeRepository.save(arme);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isArmeInUse(Integer id_arme) {
        Arme arme = findById(id_arme);
        return !arme.getPersonnages().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Arme> findArmeByDommageBetween(int minDmg, int maxDmg) {
        return armeRepository.findArmeByDommageBetween(minDmg, maxDmg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Arme> findArmesByClasse(String classe) {
        return armeRepository.findArmesByClasse(classe);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Arme> rechercheArmeTypeStat(String typeStat) {
        return armeRepository.findByTypeStat(typeStat);
    }
}
