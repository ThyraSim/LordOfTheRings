package com.example.lordoftherings.controleurJSON;



import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.service.CompteService;
import com.example.lordoftherings.service.PersonnageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    public class JSONPersonnageControlleur {
        private PersonnageService personnageService;
        private CompteService compteService;

        @Autowired
        public JSONPersonnageControlleur(PersonnageService personnageService, CompteService compteService){
            this.personnageService = personnageService;
            this.compteService = compteService;
        }

        @GetMapping("/JSON/personnages")
        public List<Personnage> findAll(){
            return personnageService.findAll();
        }

        @GetMapping("/JSON/personnages/{personnageId}")
        public Personnage findById(@PathVariable Integer personnageId){

            return personnageService.findByIdWithArmeAndClassesAndCompte(personnageId);
        }

        @DeleteMapping("/JSON/personnages/{personnageId}")
        public String deletePersonnage(@PathVariable Integer personnageId){

            Personnage temppersonnage = personnageService.findById(personnageId);
            personnageService.delete(personnageId);
            return  "personnage supprimé : " + personnageId;
        }

        @PostMapping("/JSON/comptes/{compteId}/personnages")
        public Personnage addpersonnage(@PathVariable int compteId,
                                        @RequestBody Personnage personnage){
            personnage.setCompte(compteService.findById(compteId));
            return this.personnageService.save(personnage);
        }

        @PutMapping("/JSON/personnages/edit/{personnageId}")
        public ResponseEntity<String> modifyPersonnage(@PathVariable Integer personnageId, @RequestBody Personnage newPersonnage){
            Personnage oldPersonnage = personnageService.findById(personnageId);

            if (oldPersonnage == null) {
                return ResponseEntity.notFound().build();
            }

            // Update the properties of the existing item with the new values
            oldPersonnage.setNom_personnage(newPersonnage.getNom_personnage());
            oldPersonnage.setDate_creation(newPersonnage.getDate_creation());
            oldPersonnage.setNiveau(newPersonnage.getNiveau());
            // ... update other properties as needed

            // Save the modified item
            personnageService.save(oldPersonnage);

            return ResponseEntity.ok("Item modified successfully");
        }
    }

