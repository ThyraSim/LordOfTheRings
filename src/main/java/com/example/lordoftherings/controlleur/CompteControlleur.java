package com.example.lordoftherings.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.service.CompteService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public String findAll(Model model,
                          @CookieValue(name = "sessionId", required = false) Integer sessionId){
        if(sessionId != null && sessionId != 0){
            return "redirect:/comptes/"+sessionId;
        } else if(sessionId == null){
            return "login";
        }

        List<Compte> comptes = compteService.findAll();
        model.addAttribute("comptes", comptes);
        return "affichageCompte";
    }

    @GetMapping("/comptes/redirectDelete")
    public String redirectDelete(Model model){
        model.addAttribute("type", "deleted");
        model.addAttribute("item", "Compte");
        return "success";
    }

    @GetMapping("/comptes/{compteId}")
    public String showCompte(Model model,
                             @PathVariable Integer compteId,
                             @CookieValue(name = "sessionId", required = false) Integer sessionId){
        if(sessionId != null && sessionId != 0 && sessionId != compteId){
            return "redirect:/comptes/"+sessionId;
        } else if(sessionId == null){
            return "login";
        }

        Compte tempCompte = compteService.findById(compteId);
        model.addAttribute("compte", tempCompte);
        return "compteUser";
    }

    @PostMapping("/comptes/delete")
    public ResponseEntity<String> deleteCompte(@RequestParam("compteId") Integer compteId,
                                               @CookieValue(name = "sessionId", required = false) Integer sessionId){

        if(sessionId.equals(compteId)){
            ResponseCookie deletedCookie = ResponseCookie.from("sessionId", "")
                    .maxAge(0)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .build();

            // Set the deleted cookie in the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, deletedCookie.toString());

            // Return the response with the deleted cookie
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.LOCATION, "/comptes/redirectDelete")
                    .headers(headers)
                    .build();
        }

        Compte tempCompte = compteService.findById(compteId);
        compteService.delete(compteId);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/comptes/redirectDelete")
                .build();
    }

    @PostMapping("/comptes/add")
    public ResponseEntity<String> addCompte(@RequestParam("nom_utilisateur") String nomUtilisateur,
                                            @RequestParam("motDePasse") String motDePasse,
                                            @RequestParam(value = "premium", required = false) Boolean premium,
                                            @RequestParam("nombre_personnages") int nombrePersonnages){
        if(premium == null){
            premium = false;
        }
        Compte newCompte = new Compte();
        newCompte.setMotDePasse(motDePasse);
        newCompte.setPremium(premium);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        newCompte.setDate_creation(formattedDate);
        newCompte.setNom_utilisateur(nomUtilisateur);
        newCompte.setNombre_personnages(nombrePersonnages);

        compteService.save(newCompte);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/comptes/redirectAdd")
                .build();
    }

    @GetMapping("/comptes/redirectAdd")
    public String redirectAdd(Model model){
        model.addAttribute("type", "added");
        model.addAttribute("item", "Compte");
        return "success";
    }

    @PostMapping("/comptes/edit")
    public ResponseEntity<String> modifyClasse(@RequestParam("compteId") Integer compteId,
                                               @RequestParam("nom_utilisateur") String nomUtilisateur,
                                               @RequestParam("motDePasse") String motDePasse,
                                               @RequestParam(value = "premium", required = false) Boolean premium,
                                               @RequestParam("nombre_personnages") int nombrePersonnages){
        Compte oldCompte = compteService.findById(compteId);

        if (oldCompte == null) {
            return ResponseEntity.notFound().build();
        }

        if(premium == null){
            premium = false;
        }

        // Update the properties of the existing item with the new values
        oldCompte.setPremium(premium);
        oldCompte.setNom_utilisateur(nomUtilisateur);
        oldCompte.setNombre_personnages(nombrePersonnages);
        oldCompte.setMotDePasse(motDePasse);
        // ... update other properties as needed

        // Save the modified item
        compteService.save(oldCompte);


        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/comptes/redirectEdit")
                .build();
    }

    @GetMapping("/comptes/redirectEdit")
    public String redirectEdit(Model model){
        model.addAttribute("type", "edited");
        model.addAttribute("item", "Compte");
        return "success";
    }

    @PostMapping("/comptesChange")
    public String sendChangeForm(@RequestParam("compteId") Integer compteId, Model model){
        Compte tempCompte = compteService.findById(compteId);
        model.addAttribute("compte", tempCompte);
        return "compteForm";
    }

    @PostMapping("/comptes/addForm")
    public String redirectFormAddCompte(){
        return "addCompte";
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
