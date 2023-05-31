package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.service.ArmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArmeControlleur {
    private ArmeService armeService;

    @Autowired
    public ArmeControlleur(ArmeService armeService) {
        this.armeService = armeService;
    }

    @GetMapping("/armes")
    public List<Arme> findAll() {
        return armeService.findAll();
    }

    @GetMapping("/armes/{armeId}")
    public String showCompte(@PathVariable Integer armeId) {

        Arme tempArme = armeService.findById(armeId);
        return tempArme.toString();
    }

    @DeleteMapping("/armes/delete/{armeId}")
    public String deleteArme(@PathVariable Integer armeId) {
        Arme tempArme = armeService.findById(armeId);
        armeService.delete(armeId);
        return "Arme supprimée : " + armeId;
    }

    @PostMapping("/armes")
    public Arme addArme(@RequestBody Arme arme) {
        return this.armeService.save(arme);
    }

    @PutMapping("/armes/edit/{armeId}")
    public ResponseEntity<String> modifyArme(@PathVariable Integer armeId, @RequestBody Arme newArme) {
        Arme oldArme = armeService.findById(armeId);

        if (oldArme == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the properties of the existing item with the new values
        oldArme.setNom_arme(newArme.getNom_arme());
        oldArme.setDommage(newArme.getDommage());
        oldArme.setPortee(newArme.getPortee());
        oldArme.setPrecission(newArme.getPrecission());
        oldArme.setType_stat(newArme.getType_stat());
        // ... update other properties as needed

        // Save the modified item
        armeService.save(oldArme);

        return ResponseEntity.ok("Item modified successfully");
    }

//    @GetMapping("/recherche/armes/dmg/{dmg}")

        @GetMapping("/recherche/armes/typeStat/{typeStat}")
    public List<Arme> RechercheByTypeStat(String typeStat){

        return armeService.rechercheArmeTypeStat(typeStat);

    }

}