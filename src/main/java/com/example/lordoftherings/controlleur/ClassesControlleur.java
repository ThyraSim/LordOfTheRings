package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassesControlleur {
    private ClassesService classesService;

    @Autowired
    public ClassesControlleur(ClassesService classesService){
        this.classesService = classesService;
    }

    @GetMapping("/classes")
    public List<Classes> findAll(){
        return classesService.findAll();
    }

    @DeleteMapping("/classes/{classeId}")
    public String deleteClasse(@PathVariable Integer classeId){
        Classes tempClasse = classesService.findById(classeId);
        classesService.delete(classeId);
        return "Classe supprimée : " + classeId;
    }

    @PostMapping("/classes")
    public Classes addClasse(@RequestBody Classes classes){
        return this.classesService.save(classes);
    }

    @GetMapping("/classes/{classeId}")
    public Classes findById(@PathVariable Integer classeId){

        return classesService.findById(classeId);
    }

    @PutMapping("/classes/edit/{classeId}")
    public ResponseEntity<String> modifyClasse(@PathVariable Integer classeID, @RequestBody Classes newClasse){
        Classes oldClasses = classesService.findById(classeID);

        if (oldClasses == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the properties of the existing item with the new values
        oldClasses.setNom_classe(newClasse.getNom_classe());
        oldClasses.setAgilete(newClasse.getAgilete());
        oldClasses.setConstitution(newClasse.getConstitution());
        oldClasses.setPuissance(newClasse.getPuissance());
        oldClasses.setIntelligence(newClasse.getIntelligence());
        // ... update other properties as needed

        // Save the modified item
        classesService.save(oldClasses);

        return ResponseEntity.ok("Item modified successfully");
    }
}
