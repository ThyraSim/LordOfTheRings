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
    public CompteControlleur(CompteService compteService) {
        this.compteService = compteService;
    }

    /**
     * Traite la requête POST pour rediriger vers un compte spécifique.
     *
     * @param compteId L'identifiant du compte à afficher.
     * @return La redirection vers l'URL "/comptes/{compteId}" pour afficher le compte.
     */
    @PostMapping("/comptesChoice")
    public String choixCompte(@RequestParam("compteId") String compteId) {
        return "redirect:/comptes/" + compteId;
    }

    /**
     * Traite la requête GET pour afficher tous les comptes.
     *
     * @param model      Le modèle de la vue.
     * @param sessionId  La valeur du cookie "sessionId".
     * @return La vue "affichageCompte" avec la liste des comptes.
     */
    @GetMapping("/comptes")
    public String findAll(Model model,
                          @CookieValue(name = "sessionId", required = false) Integer sessionId) {
        if (sessionId != null && sessionId != 0) {
            return "redirect:/comptes/" + sessionId;
        } else if (sessionId == null) {
            return "login";
        }

        List<Compte> comptes = compteService.findAll();
        model.addAttribute("comptes", comptes);
        return "affichageCompte";
    }

    /**
     * Traite la requête GET de redirection après la suppression réussie d'un compte.
     *
     * @param model Le modèle de la vue.
     * @return La vue "success" avec les informations de succès.
     */
    @GetMapping("/comptes/redirectDelete")
    public String redirectDelete(Model model) {
        model.addAttribute("type", "deleted");
        model.addAttribute("item", "Compte");
        return "success";
    }

    /**
     * Traite la requête GET pour afficher un compte spécifique.
     *
     * @param model     Le modèle de la vue.
     * @param compteId  L'identifiant du compte à afficher.
     * @param sessionId La valeur du cookie "sessionId".
     * @return La vue "compteUser" avec les détails du compte.
     */
    @GetMapping("/comptes/{compteId}")
    public String showCompte(Model model,
                             @PathVariable Integer compteId,
                             @CookieValue(name = "sessionId", required = false) Integer sessionId) {
        if (sessionId != null && sessionId != 0 && sessionId != compteId) {
            return "redirect:/comptes/" + sessionId;
        } else if (sessionId == null) {
            return "login";
        }

        Compte tempCompte = compteService.findById(compteId);
        model.addAttribute("compte", tempCompte);
        return "compteUser";
    }

    /**
     * Traite la requête POST pour supprimer un compte.
     *
     * @param compteId  L'identifiant du compte à supprimer.
     * @param sessionId La valeur du cookie "sessionId".
     * @return La réponse HTTP avec une redirection vers "/comptes/redirectDelete" après la suppression.
     */
    @PostMapping("/comptes/delete")
    public ResponseEntity<String> deleteCompte(@RequestParam("compteId") Integer compteId,
                                               @CookieValue(name = "sessionId", required = false) Integer sessionId) {

        if (sessionId.equals(compteId)) {
            ResponseCookie deletedCookie = ResponseCookie.from("sessionId", "")
                    .maxAge(0)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .build();

            // Définir le cookie supprimé dans les en-têtes de la réponse
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, deletedCookie.toString());

            // Renvoyer la réponse avec le cookie supprimé
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

    /**
     * Traite la requête POST pour ajouter un compte.
     *
     * @param nomUtilisateur Le nom d'utilisateur du compte.
     * @param motDePasse     Le mot de passe du compte.
     * @param premium        La valeur indiquant si le compte est premium ou non.
     * @return La réponse HTTP avec une redirection vers "/comptes/redirectAdd" après l'ajout du compte.
     */
    @PostMapping("/comptes/add")
    public ResponseEntity<String> addCompte(@RequestParam("nom_utilisateur") String nomUtilisateur,
                                            @RequestParam("motDePasse") String motDePasse,
                                            @RequestParam(value = "premium", required = false) Boolean premium) {
        if (premium == null) {
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

        compteService.save(newCompte);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/comptes/redirectAdd")
                .build();
    }

    /**
     * Traite la requête GET de redirection après l'ajout réussi d'un compte.
     *
     * @param model Le modèle de la vue.
     * @return La vue "success" avec les informations de succès.
     */
    @GetMapping("/comptes/redirectAdd")
    public String redirectAdd(Model model) {
        model.addAttribute("type", "added");
        model.addAttribute("item", "Compte");
        return "success";
    }

    /**
     * Traite la requête POST pour modifier un compte.
     *
     * @param compteId      L'identifiant du compte à modifier.
     * @param nomUtilisateur Le nouveau nom d'utilisateur du compte.
     * @param motDePasse     Le nouveau mot de passe du compte.
     * @param premium        La nouvelle valeur indiquant si le compte est premium ou non.
     * @return La réponse HTTP avec une redirection vers "/comptes/redirectEdit" après la modification du compte.
     */
    @PostMapping("/comptes/edit")
    public ResponseEntity<String> modifyClasse(@RequestParam("compteId") Integer compteId,
                                               @RequestParam("nom_utilisateur") String nomUtilisateur,
                                               @RequestParam("motDePasse") String motDePasse,
                                               @RequestParam(value = "premium", required = false) Boolean premium) {
        Compte oldCompte = compteService.findById(compteId);

        if (oldCompte == null) {
            return ResponseEntity.notFound().build();
        }

        if (premium == null) {
            premium = false;
        }

        // Mettre à jour les propriétés de l'objet existant avec les nouvelles valeurs
        oldCompte.setPremium(premium);
        oldCompte.setNom_utilisateur(nomUtilisateur);
        oldCompte.setMotDePasse(motDePasse);
        // ... mettre à jour d'autres propriétés si nécessaire

        // Enregistrer l'objet modifié
        compteService.save(oldCompte);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/comptes/redirectEdit")
                .build();
    }

    /**
     * Traite la requête POST pour envoyer le formulaire de modification d'un compte.
     *
     * @param compteId L'identifiant du compte à modifier.
     * @param model    Le modèle de la vue.
     * @return La vue "compteForm" avec les détails du compte à modifier.
     */
    @PostMapping("/comptesChange")
    public String sendChangeForm(@RequestParam("compteId") Integer compteId, Model model) {
        Compte tempCompte = compteService.findById(compteId);
        model.addAttribute("compte", tempCompte);
        return "compteForm";
    }

    /**
     * Traite la requête POST pour rediriger vers le formulaire d'ajout d'un compte.
     *
     * @return La vue "addCompte" pour afficher le formulaire d'ajout d'un compte.
     */
    @PostMapping("/comptes/addForm")
    public String redirectFormAddCompte() {
        return "addCompte";
    }
}
