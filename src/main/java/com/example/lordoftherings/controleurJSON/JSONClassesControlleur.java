package com.example.lordoftherings.controleurJSON;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Classes;
import  com.example.lordoftherings.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JSONClassesControlleur {
    private ClassesService classesService;

    @Autowired
    public JSONClassesControlleur(ClassesService classesService) {
        this.classesService = classesService;
    }

    /**
     * Récupère toutes les classes.
     *
     * @return Liste des classes.
     */
    @GetMapping("/JSON/classes")
    public List<Classes> findAll() {
        return classesService.findAll();
    }

    /**
     * Supprime une classe en utilisant son identifiant (classeId).
     *
     * @param classeId Identifiant de la classe à supprimer.
     * @return Message indiquant la classe supprimée.
     */
    @DeleteMapping("/JSON/classes/{classeId}")
    public String deleteClasse(@PathVariable Integer classeId) {
        Classes tempClasse = classesService.findById(classeId);
        classesService.delete(classeId);
        return "Classe supprimée : " + classeId;
    }

    /**
     * Ajoute une nouvelle classe.
     *
     * @param classes Classe à ajouter.
     * @return Classe ajoutée.
     */
    @PostMapping("/JSON/classes/add")
    public Classes addClasse(@RequestBody Classes classes) {
        return this.classesService.save(classes);
    }

    /**
     * Récupère une classe en utilisant son identifiant (classeId).
     *
     * @param classeId Identifiant de la classe à récupérer.
     * @return Classe correspondante à l'identifiant.
     */
    @GetMapping("/JSON/classes/{classeId}")
    public Classes findById(@PathVariable Integer classeId) {
        return classesService.findById(classeId);
    }

    /**
     * Modifie une classe existante en utilisant son identifiant (classeId) et les nouvelles données (newClasse).
     *
     * @param classeId  Identifiant de la classe à modifier.
     * @param newClasse Nouvelles données de la classe.
     * @return Classe modifiée.
     */
    @PutMapping("/JSON/classes/edit/{classeId}")
    public Classes modifyClasse(@PathVariable Integer classeId, @RequestBody Classes newClasse) {
        Classes oldClasses = classesService.findById(classeId);

        // Met à jour les propriétés de l'objet existant avec les nouvelles valeurs
        oldClasses.setNom_classe(newClasse.getNom_classe());
        oldClasses.setAgilete(newClasse.getAgilete());
        oldClasses.setConstitution(newClasse.getConstitution());
        oldClasses.setPuissance(newClasse.getPuissance());
        oldClasses.setIntelligence(newClasse.getIntelligence());
        // ... mettre à jour les autres propriétés si nécessaire

        return classesService.save(oldClasses);
    }
}
