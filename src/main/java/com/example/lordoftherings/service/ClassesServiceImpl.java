package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.repository.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassesServiceImpl implements ClassesService{

    private ClassesRepository classesRepository;

    @Autowired
    public ClassesServiceImpl(ClassesRepository classesRepository){
        this.classesRepository = classesRepository;
    }

    @Override
    public List<Classes> findAll() {
        return this.classesRepository.findAll();
    }

    @Override
    public void delete(Integer id_classe) {
        classesRepository.deleteById(id_classe);
    }

    @Override
    public Classes findById(Integer id_classe) {
        Optional<Classes> classes =  classesRepository.findById(id_classe);
        Classes tempClasses = null;
        //s'il trouve la classe => il va la chercher
        if (classes.isPresent()) {
            tempClasses = classes.get();
        } else {
            throw new RuntimeException("La classe non trouvée -" + id_classe);
        }

        return tempClasses;
    }

    @Override
    public Classes save(Classes classes) {
        return this.classesRepository.save(classes);
    }

    @Override
    public boolean isClasseInUse(Integer id_classe) {
        Classes classe = findById(id_classe);
        return !classe.getPersonnages().isEmpty();
    }
}
