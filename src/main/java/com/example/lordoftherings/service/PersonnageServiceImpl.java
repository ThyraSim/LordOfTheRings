package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.repository.CompteRepository;
import com.example.lordoftherings.repository.PersonnageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonnageServiceImpl implements PersonnageService{
    private PersonnageRepository personnageRepository;

    @Autowired
    public PersonnageServiceImpl(PersonnageRepository personnageRepository){
        this.personnageRepository = personnageRepository;
    }
    @Override
    public List<Personnage> findAll() {
        return this.personnageRepository.findAll();
    }

    @Override
    public void delete(Integer id_compte) {
        personnageRepository.deleteById(id_compte);
    }

    @Override
    public Personnage findById(Integer id_personnage) {
        Optional<Personnage> personnage =  personnageRepository.findById(id_personnage);
        Personnage tempPersonnage = null;
        //s'il trouve le personnage => il va le chercher
        if (personnage.isPresent()) {
            tempPersonnage = personnage.get();
        } else {
            throw new RuntimeException("Le personnage non trouvé -" + id_personnage);
        }

        return tempPersonnage;
    }

    @Override
    public Personnage save(Personnage personnage) {
        return this.personnageRepository.save(personnage);
    }

    @Override
    public List<Personnage> recherchePersonnageByClasse(String classe) {
        return personnageRepository.recherchePersonnageByClasse(classe);
    }

    @Override
    public Personnage findByIdWithArmeAndClassesAndCompte(Integer id_personnage) {
        return personnageRepository.findByIdWithArmeAndClassesAndCompte(id_personnage);
    }
}
