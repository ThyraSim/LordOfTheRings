package com.example.lordoftherings.controleurJSON;




import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    public class JSONCompteControlleur {
        private CompteService compteService;

        @Autowired
        public JSONCompteControlleur(CompteService compteService){
            this.compteService = compteService;
        }

        @GetMapping("JSON/comptes")
        public List<Compte> findAll(){
            return compteService.findAll();
        }

        @GetMapping("JSON/comptes/{compteId}")
        public Compte findById(@PathVariable Integer compteId){

            return compteService.findById(compteId);
        }

        @DeleteMapping("/JSON/comptes/{compteId}")
        public String deleteCompte(@PathVariable Integer compteId){

            Compte tempCompte = compteService.findById(compteId);
            compteService.delete(compteId);
            return  "Compte supprimé : " + compteId;
        }

        @PostMapping("/JSON/comptes")
        public Compte addCompte(@RequestBody Compte compte){

            return this.compteService.save(compte);
        }

        @PutMapping("/JSON/comptes/edit/{compteId}")
        public ResponseEntity<String> modifyCompte(@PathVariable Integer compteId, @RequestBody Compte newCompte){
            Compte oldCompte = compteService.findById(compteId);

            if (oldCompte == null) {
                return ResponseEntity.notFound().build();
            }

            // Update the properties of the existing item with the new values
            oldCompte.setDate_creation(newCompte.getDate_creation());
            oldCompte.setPremium(newCompte.isPremium());
            oldCompte.setNom_utilisateur(newCompte.getNom_utilisateur());
            // ... update other properties as needed

            // Save the modified item
            compteService.save(oldCompte);

            return ResponseEntity.ok("Item modified successfully");
        }

        @GetMapping("/JSON/Recherche/comptesPrenium")
        public List<Compte> recherComptePrenium(){
            return compteService.rechercheComptePrenium();

        }
        @GetMapping("/JSON/Recherche/compte/nomUtilisateur/{saisi}")
        public List<Compte> rechercheNomUtilisateurContenant(@PathVariable String saisi){
            return compteService.rechercheNomUtilisateurContenant(saisi);
        }
    }

