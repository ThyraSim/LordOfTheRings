package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/")
    public String sayHello(){
        return "Hello World";
    }

    @DeleteMapping("/comptes/{compteId}")
    public String deleteCompte(@PathVariable Integer compteId){

        Compte tempCompte = compteService.findById(compteId);
        compteService.delete(compteId);
        return  "Compte supprimé : " + compteId;
    }

    @PostMapping("/comptes")
    public Compte addCompte(@RequestBody Compte compte){

        return this.compteService.save(compte);
    }
}
