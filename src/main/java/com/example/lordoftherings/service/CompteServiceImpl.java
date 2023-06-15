package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompteServiceImpl implements CompteService{

    private CompteRepository compteRepository;

    @Autowired
    public CompteServiceImpl(CompteRepository compteRepository){
        this.compteRepository = compteRepository;
    }
    @Override
    public List<Compte> findAll() {
        return this.compteRepository.findAll();
    }

    @Override
    public void delete(Integer id_compte) {
        compteRepository.deleteById(id_compte);
    }

    @Override
    public Compte findById(Integer id_compte) {
        Optional<Compte> compte =  compteRepository.findById(id_compte);
        Compte tempCompte = null;
        //s'il trouve le compte => il va le chercher
        if (compte.isPresent()) {
            tempCompte = compte.get();
        } else {
            throw new RuntimeException("Le compte non trouvé -" + id_compte);
        }


        return tempCompte;
    }

    @Override
    public Compte save(Compte compte) {
        return this.compteRepository.save(compte);
    }

    @Override
    public Compte findCompteWithPersonnagesById(Integer compteId) {
        return compteRepository.findCompteWithPersonnagesById(compteId);
    }
}
