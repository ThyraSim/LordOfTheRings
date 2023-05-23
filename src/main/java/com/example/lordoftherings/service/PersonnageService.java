package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.repository.PersonnageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonnageService {
    List<Personnage> findAll();

    void delete(Integer id_personnage);

    Personnage findById(Integer id_personnage);

    Personnage save(Personnage personnage);
}
