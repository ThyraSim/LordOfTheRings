package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArmeService {
    List<Arme> findAll();

    void delete(Integer id_arme);

    Arme findById(Integer id_arme);

    Arme save(Arme arme);

    List<Arme> rechercheArmeTypeStat(String typeStat);


}
