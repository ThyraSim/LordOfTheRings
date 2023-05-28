package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
