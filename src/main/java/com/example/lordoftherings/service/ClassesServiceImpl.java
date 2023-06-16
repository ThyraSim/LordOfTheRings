package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.repository.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service de gestion des classes.
 */
@Service
public class ClassesServiceImpl implements ClassesService {

    private ClassesRepository classesRepository;

    @Autowired
    public ClassesServiceImpl(ClassesRepository classesRepository) {
        this.classesRepository = classesRepository;
    }

    /**
     * Récupère la liste de toutes les classes.
     *
     * @return la liste de toutes les classes
     */
    @Override
    public List<Classes> findAll() {
        return this.classesRepository.findAll();
    }

    /**
     * Supprime une classe par son identifiant.
     *
     * @param id_classe l'identifiant de la classe à supprimer
     */
    @Override
    public void delete(Integer id_classe) {
        classesRepository.deleteById(id_classe);
    }

    /**
     * Recherche une classe par son identifiant.
     *
     * @param id_classe l'identifiant de la classe à rechercher
     * @return la classe correspondante
     * @throws RuntimeException si la classe n'est pas trouvée
     */
    @Override
    public Classes findById(Integer id_classe) {
        Optional<Classes> classes = classesRepository.findById(id_classe);
        Classes tempClasses = null;
        //s'il trouve la classe => il va la chercher
        if (classes.isPresent()) {
            tempClasses = classes.get();
        } else {
            throw new RuntimeException("La classe non trouvée -" + id_classe);
        }

        return tempClasses;
    }

    /**
     * Enregistre une classe.
     *
     * @param classes la classe à enregistrer
     * @return la classe enregistrée
     */
    @Override
    public Classes save(Classes classes) {
        return this.classesRepository.save(classes);
    }

    /**
     * Vérifie si une classe est utilisée par des personnages.
     *
     * @param id_classe l'identifiant de la classe à vérifier
     * @return true si la classe est utilisée, false sinon
     */
    @Override
    public boolean isClasseInUse(Integer id_classe) {
        Classes classe = findById(id_classe);
        return !classe.getPersonnages().isEmpty();
    }
}
