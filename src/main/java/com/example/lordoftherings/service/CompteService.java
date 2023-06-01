package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Compte;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompteService {
    List<Compte> findAll();

    void delete(Integer id_compte);

    Compte findById(Integer id_compte);

    Compte save(Compte compte);

    Compte findCompteWithPersonnagesById(Integer id_compte);
}
