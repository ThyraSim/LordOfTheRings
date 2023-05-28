package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.service.ArmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArmeControlleur {
    private ArmeService armeService;

    @Autowired
    public ArmeControlleur(ArmeService armeService){
        this.armeService = armeService;
    }

    @GetMapping("/armes")
    public List<Arme> findAll(){
        return armeService.findAll();
    }

    @DeleteMapping("/armes/{armeId}")
    public String deleteArme(@PathVariable Integer armeId){
        Arme tempArme = armeService.findById(armeId);
        armeService.delete(armeId);
        return "Arme supprimée : " + armeId;
    }

    @PostMapping("/armes")
    public Arme addArme(@RequestBody Arme arme){
        return this.armeService.save(arme);
    }
}
