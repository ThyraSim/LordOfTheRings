package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.*;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.service.ArmeService;
import com.example.lordoftherings.service.ClassesService;
import com.example.lordoftherings.service.CompteService;
import com.example.lordoftherings.service.PersonnageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PersonnageControlleur {
    private PersonnageService personnageService;
    private ClassesService classesService;
    private CompteService compteService;
    private ArmeService armeService;

    @Autowired
    public PersonnageControlleur(PersonnageService personnageService, ClassesService classesService, CompteService compteService, ArmeService armeService) {
        this.personnageService = personnageService;
        this.classesService = classesService;
        this.compteService = compteService;
        this.armeService = armeService;
    }

    @GetMapping("/personnages")
    public String findAll(Model model){
        List<Personnage> personnages = personnageService.findAll();
        model.addAttribute("personnages", personnages);
        return "allPersonnages";
    }

    @PostMapping("/personnages/delete")
    public ResponseEntity<String> deletePersonnage(@RequestParam("personnageId") Integer personnageId){

        Personnage tempPersonnage = personnageService.findById(personnageId);
        personnageService.delete(personnageId);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/personnages/redirectDelete")
                .build();
    }

    @GetMapping("/personnages/redirectDelete")
    public String redirectDelete(Model model){
        model.addAttribute("type", "deleted");
        model.addAttribute("item", "Personnage");
        return "success";
    }

    @PostMapping("/personnages/add")
    public ResponseEntity<String> addPersonnage(@RequestParam("nom_personnage") String nom_personnage,
                                            @RequestParam("niveau") Integer niveau,
                                            @RequestParam("classeId") Integer classeId,
                                            @RequestParam("armeId") Integer armeId,
                                            @RequestParam("compteId") Integer compteId){
        Classes classe = classesService.findById(classeId);
        Arme arme = armeService.findById(armeId);
        Compte compte = compteService.findById(compteId);
        Personnage personnage = new Personnage();
        personnage.setNom_personnage(nom_personnage);
        personnage.setNiveau(niveau);
        personnage.setClasse(classe);
        personnage.setArme(arme);
        personnage.setCompte(compte);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        personnage.setDate_creation(formattedDate);
        personnageService.save(personnage);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/personnages/redirectAdd")
                .build();
    }

    @GetMapping("/personnages/redirectAdd")
    public String redirectAdd(Model model){
        model.addAttribute("type", "added");
        model.addAttribute("item", "Personnage");
        return "success";
    }

    @PostMapping("/personnages/edit")
    public ResponseEntity<String> modifyClasse(@RequestParam("nom_personnage") String nom_personnage,
                                               @RequestParam("niveau") Integer niveau,
                                               @RequestParam("classeId") Integer classeId,
                                               @RequestParam("armeId") Integer armeId,
                                               @RequestParam("personnageId") Integer personnageId){
        Personnage oldPersonnage = personnageService.findById(personnageId);

        if (oldPersonnage == null) {
            return ResponseEntity.notFound().build();
        }
        Classes classe = classesService.findById(classeId);
        Arme arme = armeService.findById(armeId);

        // Update the properties of the existing item with the new values
        oldPersonnage.setNom_personnage(nom_personnage);
        oldPersonnage.setNiveau(niveau);
        oldPersonnage.setClasse(classe);
        oldPersonnage.setArme(arme);
        // ... update other properties as needed

        // Save the modified item
        personnageService.save(oldPersonnage);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/personnages/redirectEdit")
                .build();
    }

    @GetMapping("/personnages/redirectEdit")
    public String redirectEdit(Model model){
        model.addAttribute("type", "edited");
        model.addAttribute("item", "Personnage");
        return "success";
    }

    @GetMapping("/personnages/{id}")
    public String showPersonnage(@PathVariable Integer id, Model model) {
        Personnage personnage = personnageService.findByIdWithArmeAndClassesAndCompte(id);
        model.addAttribute("personnage", personnage);
        return "personnageDetails";
    }

    @PostMapping("/personnagesChoice")
    public String choixPersonnage(@RequestParam("personnageId") String personnageId){
        return "redirect:/personnages/" + personnageId;
    }

    @PostMapping("/personnages/addForm")
    public String redirectFormAddPersonnage(Model model,
                                            @RequestParam("compteId") Integer compteId){
        List<Classes> classes = classesService.findAll();
        List<Arme> armes = armeService.findAll();
        model.addAttribute("compteId", compteId);
        model.addAttribute("classes", classes);
        model.addAttribute("armes", armes);
        return "addPersonnage";
    }

    @PostMapping("/personnagesChange")
    public String sendChangeForm(@RequestParam("personnageId") Integer personnageId, Model model){
        Personnage tempPersonnage = personnageService.findById(personnageId);
        List<Classes> classes = classesService.findAll();
        List<Arme> armes = armeService.findAll();
        model.addAttribute("personnage", tempPersonnage);
        model.addAttribute("classes", classes);
        model.addAttribute("armes", armes);
        return "personnageForm";
    }
}
