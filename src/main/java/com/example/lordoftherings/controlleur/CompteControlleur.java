package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompteControlleur {
    private CompteService compteService;

    @Autowired
    public CompteControlleur(CompteService compteService){
        this.compteService = compteService;
    }

    @GetMapping("/comptes")
    public List<Compte> findAll(){
        return compteService.findAll();
    }

    @GetMapping("/comptes/{compteId}")
    public String showCompte(@PathVariable Integer compteId){

        Compte tempCompte = compteService.findById(compteId);
        return  tempCompte.toString();
    }

    @DeleteMapping("/comptes/delete/{compteId}")
    public String deleteCompte(@PathVariable Integer compteId){

        Compte tempCompte = compteService.findById(compteId);
        compteService.delete(compteId);
        return  "Compte supprimé : " + compteId;
    }

    @PostMapping("/comptes")
    public Compte addCompte(@RequestBody Compte compte){

        return this.compteService.save(compte);
    }

    @PutMapping("/comptes/edit/{compteId}")
    public ResponseEntity<String> modifyClasse(@PathVariable Integer compteId, @RequestBody Compte newCompte){
        Compte oldCompte = compteService.findById(compteId);

        if (oldCompte == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the properties of the existing item with the new values
        oldCompte.setDate_creation(newCompte.getDate_creation());
        oldCompte.setPremium(newCompte.isPremium());
        oldCompte.setNom_utilisateur(newCompte.getNom_utilisateur());
        oldCompte.setNombre_personnages(newCompte.getNombre_personnages());
        // ... update other properties as needed

        // Save the modified item
        compteService.save(oldCompte);

        return ResponseEntity.ok("Item modified successfully");
    }

    @GetMapping("/Recherche/comptesPrenium")
    public List<Compte> recherComptePrenium(){
        return compteService.rechercheComptePrenium();

    }
@GetMapping("/Recherche/compte/nomUtilisateur/{saisi}")
    public List<Compte> rechercheNomUtilisateurContenant(@PathVariable String saisi){
        return compteService.rechercheNomUtilisateurContenant(saisi);
}
}
