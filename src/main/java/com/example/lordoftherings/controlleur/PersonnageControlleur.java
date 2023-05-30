package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.service.PersonnageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/personnages/{personnageId}")
    public String showCompte(@PathVariable Integer personnageId){

        Personnage tempPersonnage = personnageService.findById(personnageId);
        return  tempPersonnage.toString();
    }

    @DeleteMapping("/personnages/delete/{personnageId}")
    public String deletePersonnage(@PathVariable Integer personnageId){

        Personnage temppersonnage = personnageService.findById(personnageId);
        personnageService.delete(personnageId);
        return  "personnage supprimé : " + personnageId;
    }

    @PostMapping("/personnages")
    public Personnage addpersonnage(@RequestBody Personnage personnage){

        return this.personnageService.save(personnage);
    }

    @PutMapping("/personnages/edit/{personnageId}")
    public ResponseEntity<String> modifyClasse(@PathVariable Integer personnageId, @RequestBody Personnage newPersonnage){
        Personnage oldPersonnage = personnageService.findById(personnageId);

        if (oldPersonnage == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the properties of the existing item with the new values
        oldPersonnage.setNom_personnage(newPersonnage.getNom_personnage());
        oldPersonnage.setId_classe(newPersonnage.getId_classe());
        oldPersonnage.setDate_creation(newPersonnage.getDate_creation());
        oldPersonnage.setNiveau(newPersonnage.getNiveau());
        oldPersonnage.setId_arme(newPersonnage.getId_arme());
        oldPersonnage.setId_compte(newPersonnage.getId_compte());
        // ... update other properties as needed

        // Save the modified item
        personnageService.save(oldPersonnage);

        return ResponseEntity.ok("Item modified successfully");
    }
}
