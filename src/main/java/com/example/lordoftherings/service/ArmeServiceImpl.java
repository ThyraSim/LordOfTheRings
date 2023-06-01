package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.repository.ArmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArmeServiceImpl implements ArmeService {
    private ArmeRepository armeRepository;

    @Autowired
    public ArmeServiceImpl(ArmeRepository armeRepository) {
        this.armeRepository = armeRepository;
    }

    @Override
    public List<Arme> findAll() {
        return this.armeRepository.findAll();
    }

    @Override
    public void delete(Integer id_arme) {
        armeRepository.deleteById(id_arme);
    }

    @Override
    public Arme findById(Integer id_arme) {
        Optional<Arme> arme =  armeRepository.findById(id_arme);
        Arme tempArme = null;
        //s'il trouve l'arme => il va la chercher
        if (arme.isPresent()) {
            tempArme = arme.get();
        } else {
            throw new RuntimeException("L'arme non trouvée -" + id_arme);
        }
        return tempArme;
    }

    @Override
    public Arme save(Arme arme) {
        return this.armeRepository.save(arme);
    }

    @Override
    public boolean isArmeInUse(Integer id_arme) {
        Arme arme = findById(id_arme);
        return !arme.getPersonnages().isEmpty();
    }
}
