package com.example.lordoftherings.controlleur;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CompteControlleur {
    private CompteService compteService;

    @Autowired
    public CompteControlleur(CompteService compteService){
        this.compteService = compteService;
    }

    @PostMapping("/comptesChoice")
    public String choixCompte(@RequestParam("compteId") String compteId){
        return "redirect:/comptes/" + compteId;
    }

    @GetMapping("/comptes")
    public String findAll(Model model){
        List<Compte> comptes = compteService.findAll();
        model.addAttribute("comptes", comptes);
        return "affichageCompte";
    }

    @GetMapping("/comptes/{compteId}")
    public String showCompte(Model model, @PathVariable Integer compteId){

        Compte tempCompte = compteService.findById(compteId);
        model.addAttribute("compte", tempCompte);
        return "compteUser";
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
    public ResponseEntity<String> modifyClasse(@PathVariable Integer compteId,
                                               @RequestParam("nom_utilisateur") String nomUtilisateur,
                                               @RequestParam("motDePasse") String motDePasse,
                                               @RequestParam("premium") boolean premium,
                                               @RequestParam("nombre_personnages") int nombrePersonnages){
        Compte oldCompte = compteService.findById(compteId);

        if (oldCompte == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the properties of the existing item with the new values
        oldCompte.setPremium(premium);
        oldCompte.setNom_utilisateur(nomUtilisateur);
        oldCompte.setNombre_personnages(nombrePersonnages);
        oldCompte.setMotDePasse(motDePasse);
        // ... update other properties as needed

        // Save the modified item
        compteService.save(oldCompte);

        return ResponseEntity.ok("Item modified successfully");
    }

    @PostMapping("/comptesChange")
    public String sendChangeForm(@RequestParam("compteId") Integer compteId, Model model){
        Compte tempCompte = compteService.findById(compteId);
        model.addAttribute("compte", tempCompte);
        return "compteForm";
    }
}
