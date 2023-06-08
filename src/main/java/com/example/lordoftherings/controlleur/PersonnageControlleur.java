package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.service.ArmeService;
import com.example.lordoftherings.service.ClassesService;
import com.example.lordoftherings.service.CompteService;
import com.example.lordoftherings.service.PersonnageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonnageControlleur {
    private PersonnageService personnageService;
    private CompteService compteService;
    private ClassesService classesService;
    private ArmeService armeService;

    @Autowired
    public PersonnageControlleur(PersonnageService personnageService, CompteService compteService, ClassesService classesService, ArmeService armeService) {
        this.personnageService = personnageService;
        this.compteService = compteService;
        this.classesService = classesService;
        this.armeService = armeService;
    }

    @GetMapping("/personnages")
    public List<Personnage> findAll(){
        return personnageService.findAll();
    }

    @GetMapping("/personnages/{personnageId}")
    public Personnage findById(@PathVariable Integer personnageId){

        return personnageService.findByIdWithArmeAndClassesAndCompte(personnageId);
    }

    @DeleteMapping("/personnages/{personnageId}")
    public String deletePersonnage(@PathVariable Integer personnageId){

        Personnage temppersonnage = personnageService.findById(personnageId);
        personnageService.delete(personnageId);
        return  "personnage supprimé : " + personnageId;
    }

    @PostMapping("comptes/{compteId}/personnages")
    public Personnage addpersonnage(@RequestBody Personnage personnage, @PathVariable Integer compteId){
        personnage.setCompte(compteService.findById(compteId));
        return this.personnageService.save(personnage);
    }

    @PutMapping("/personnages/edit/{personnageId}")
    public Personnage modifyClasse(@PathVariable Integer personnageId, @RequestBody Personnage newPersonnage){
        Personnage oldPersonnage = personnageService.findById(personnageId);

        // Update the properties of the existing item with the new values
        oldPersonnage.setNom_personnage(newPersonnage.getNom_personnage());
        oldPersonnage.setDate_creation(newPersonnage.getDate_creation());
        oldPersonnage.setNiveau(newPersonnage.getNiveau());
        oldPersonnage.setClasse(classesService.findById(newPersonnage.getClasse().getId_classe()));
        oldPersonnage.setArme(armeService.findById(newPersonnage.getArme().getId_arme()));
        // ... update other properties as needed

        // Save the modified item

        return personnageService.save(oldPersonnage);
    }
}
