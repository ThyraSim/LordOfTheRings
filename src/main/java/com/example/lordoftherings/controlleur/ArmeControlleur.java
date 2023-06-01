package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.service.ArmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArmeControlleur {
    private ArmeService armeService;

    @Autowired
    public ArmeControlleur(ArmeService armeService){
        this.armeService = armeService;
    }

    @PostMapping("/armesChoice")
    public String choixArme(@RequestParam("armeId") String armeId){
        return "redirect:/armes/" + armeId;
    }
    @GetMapping("/armes")
    public String findAll(Model model,
                          @CookieValue(name = "sessionId", required = false) Integer sessionId){
        if(sessionId != null && sessionId != 0){
            return "redirect:/comptes/"+sessionId;
        } else if(sessionId == null){
            return "login";
        }

        List<Arme> armes = armeService.findAll();
        model.addAttribute("armes", armes);
        return "affichageArmes";
    }

    @GetMapping("/armes/{armeId}")
    public String showCompte(Model model,
                             @PathVariable Integer armeId,
                             @CookieValue(name = "sessionId", required = false) Integer sessionId){

        if(sessionId != null && sessionId != 0){
            return "redirect:/comptes/"+sessionId;
        } else if(sessionId == null){
            return "login";
        }
        Arme tempArme = armeService.findById(armeId);
        model.addAttribute("arme", tempArme);
        return "singleArme";
    }

    @PostMapping("/armes/delete")
    public ResponseEntity<String> deleteArme(@RequestParam("armeId") Integer armeId){
        Arme arme = armeService.findById(armeId);
        List<Personnage> personnages = arme.getPersonnages();
        if (armeService.isArmeInUse(armeId)) {
            // At least one personnage is using the arme, return a response indicating it cannot be deleted
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/armes/redirectBadDelete")
                    .build();
        }
        armeService.delete(armeId);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/armes/redirectDelete")
                .build();
    }

    @GetMapping("/armes/redirectDelete")
    public String redirectDelete(Model model){
        model.addAttribute("type", "deleted");
        model.addAttribute("item", "Arme");
        return "success";
    }

    @GetMapping("/armes/redirectBadDelete")
    public String redirectBadDelete(Model model){
        model.addAttribute("item", "Arme");
        return "badDelete";
    }

    @PostMapping("/armes/add")
    public ResponseEntity<String> addArme(@RequestParam("nom_arme") String nomArme,
                        @RequestParam("dommage") double dommage,
                        @RequestParam("portee") double portee,
                        @RequestParam("precission") int precission,
                        @RequestParam("type_stat") String type_stat){
        Arme newArme = new Arme();
        newArme.setNom_arme(nomArme);
        newArme.setDommage(dommage);
        newArme.setPortee(portee);
        newArme.setPrecission(precission);
        newArme.setType_stat(type_stat);

        armeService.save(newArme);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/armes/redirectAdd")
                .build();
    }

    @GetMapping("/armes/redirectAdd")
    public String redirectAdd(Model model){
        model.addAttribute("type", "added");
        model.addAttribute("item", "Arme");
        return "success";
    }

    @PostMapping("/armes/edit")
    public ResponseEntity<String> modifyArme(@RequestParam("armeId") Integer armeId,
                                             @RequestParam("nom_arme") String nomArme,
                                             @RequestParam("dommage") double dommage,
                                             @RequestParam("portee") double portee,
                                             @RequestParam("precission") int precission,
                                             @RequestParam("type_stat") String type_stat){
        Arme oldArme = armeService.findById(armeId);

        if (oldArme == null) {
            return ResponseEntity.notFound().build();
        }

        // Update the properties of the existing item with the new values
        oldArme.setNom_arme(nomArme);
        oldArme.setDommage(dommage);
        oldArme.setPortee(portee);
        oldArme.setPrecission(precission);
        oldArme.setType_stat(type_stat);
        // ... update other properties as needed

        // Save the modified item
        armeService.save(oldArme);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/armes/redirectEdit")
                .build();
    }

    @GetMapping("/armes/redirectEdit")
    public String redirectEdit(Model model){
        model.addAttribute("type", "edited");
        model.addAttribute("item", "Arme");
        return "success";
    }

    @PostMapping("/armesChange")
    public String sendChangeForm(@RequestParam("armeId") Integer armeId, Model model){
        Arme tempArme = armeService.findById(armeId);
        model.addAttribute("arme", tempArme);
        return "armeForm";
    }

    @PostMapping("/armes/addForm")
    public String redirectFormAddArme(){

        return "armeAdd";
    }
}
