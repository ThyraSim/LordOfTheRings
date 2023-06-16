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

    // Retourne la liste de tous les personnages
    @GetMapping("/personnages")
    public String findAll(Model model){
        List<Personnage> personnages = personnageService.findAll();
        model.addAttribute("personnages", personnages);
        return "allPersonnages";
    }

    // Supprime un personnage par son identifiant
    @PostMapping("/personnages/delete")
    public ResponseEntity<String> deletePersonnage(@RequestParam("personnageId") Integer personnageId){

        Personnage tempPersonnage = personnageService.findById(personnageId);
        personnageService.delete(personnageId);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/personnages/redirectDelete")
                .build();
    }

    // Redirige vers une page après la suppression d'un personnage
    @GetMapping("/personnages/redirectDelete")
    public String redirectDelete(Model model){
        model.addAttribute("type", "deleted");
        model.addAttribute("item", "Personnage");
        return "success";
    }

    // Ajoute un nouveau personnage
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

    // Redirige vers une page après l'ajout d'un personnage
    @GetMapping("/personnages/redirectAdd")
    public String redirectAdd(Model model){
        model.addAttribute("type", "added");
        model.addAttribute("item", "Personnage");
        return "success";
    }

    // Modifie un personnage existant
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

        // Met à jour les propriétés de l'objet existant avec les nouvelles valeurs
        oldPersonnage.setNom_personnage(nom_personnage);
        oldPersonnage.setNiveau(niveau);
        oldPersonnage.setClasse(classe);
        oldPersonnage.setArme(arme);
        // ... mettre à jour les autres propriétés au besoin

        // Enregistre l'objet modifié
        personnageService.save(oldPersonnage);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/personnages/redirectEdit")
                .build();
    }

    // Redirige vers une page après la modification d'un personnage
    @GetMapping("/personnages/redirectEdit")
    public String redirectEdit(Model model){
        model.addAttribute("type", "edited");
        model.addAttribute("item", "Personnage");
        return "success";
    }

    // Affiche les détails d'un personnage spécifié par son identifiant
    @GetMapping("/personnages/{personnageId}")
    public String showPersonnage(@PathVariable Integer personnageId, Model model) {
        Personnage personnage = personnageService.findByIdWithArmeAndClassesAndCompte(personnageId);
        model.addAttribute("personnage", personnage);
        model.addAttribute("imagePath", "/images/"+personnage.getClasse().getImage());
        return "personnageDetails";
    }

    // Redirige vers les détails d'un personnage après avoir sélectionné un personnage
    @PostMapping("/personnagesChoice")
    public String choixPersonnage(@RequestParam("personnageId") String personnageId){
        return "redirect:/personnages/" + personnageId;
    }

    // Redirige vers un formulaire pour ajouter un personnage
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

    // Redirige vers un formulaire pour modifier un personnage
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
