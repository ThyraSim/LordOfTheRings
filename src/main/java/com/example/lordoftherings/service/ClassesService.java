package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Classes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassesService {
    List<Classes> findAll();

    void delete(Integer id_classe);

    Classes findById(Integer id_classe);

    Classes save(Classes classes);
}
