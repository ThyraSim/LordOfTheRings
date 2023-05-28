package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.service.PersonnageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonnageControlleur {
    private PersonnageService personnageService;

    @Autowired
    public PersonnageControlleur(PersonnageService personnageService){
        this.personnageService = personnageService;
    }

    @GetMapping("/personnages")
    public List<Personnage> findAll(){
        return personnageService.findAll();
    }

    @DeleteMapping("/personnages/{personnageId}")
    public String deletePersonnage(@PathVariable Integer personnageId){

        Personnage temppersonnage = personnageService.findById(personnageId);
        personnageService.delete(personnageId);
        return  "personnage supprimé : " + personnageId;
    }

    @PostMapping("/personnages")
    public Personnage addpersonnage(@RequestBody Personnage personnage){

        return this.personnageService.save(personnage);
    }
}
