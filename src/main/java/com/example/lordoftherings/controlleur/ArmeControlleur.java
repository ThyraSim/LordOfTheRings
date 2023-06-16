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

/**
 * Contrôleur pour la gestion des armes.
 */
@Controller
public class ArmeControlleur {
    private ArmeService armeService;

    @Autowired
    public ArmeControlleur(ArmeService armeService){
        this.armeService = armeService;
    }

    /**
     * Traite la requête POST pour le choix d'une arme.
     *
     * @param armeId L'identifiant de l'arme choisie.
     * @return La redirection vers l'URL "/armes/{armeId}".
     */
    @PostMapping("/armesChoice")
    public String choixArme(@RequestParam("armeId") String armeId){
        return "redirect:/armes/" + armeId;
    }

    /**
     * Traite la requête GET pour afficher toutes les armes.
     *
     * @param model Le modèle de la vue.
     * @param sessionId L'identifiant de session (optionnel).
     * @return La vue "affichageArmes" ou la redirection vers l'URL "/comptes/{sessionId}" ou "login" en fonction de la présence de l'identifiant de session.
     */
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

    /**
     * Traite la requête GET pour afficher une arme spécifique.
     *
     * @param model Le modèle de la vue.
     * @param armeId L'identifiant de l'arme.
     * @param sessionId L'identifiant de session (optionnel).
     * @return La vue "singleArme" ou la redirection vers l'URL "/comptes/{sessionId}" ou "login" en fonction de la présence de l'identifiant de session.
     */
    @GetMapping("/armes/{armeId}")
    public String showArme(Model model,
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

    /**
     * Traite la requête POST pour supprimer une arme.
     *
     * @param armeId L'identifiant de l'arme à supprimer.
     * @return La réponse HTTP avec la redirection vers l'URL "/armes/redirectDelete" si la suppression réussit, sinon la redirection vers l'URL "/armes/redirectBadDelete".
     */
    @PostMapping("/armes/delete")
    public ResponseEntity<String> deleteArme(@RequestParam("armeId") Integer armeId){
        Arme arme = armeService.findById(armeId);
        List<Personnage> personnages = arme.getPersonnages();
        if (armeService.isArmeInUse(armeId)) {
            // Au moins un personnage utilise une arme, impossible de supprimer
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/armes/redirectBadDelete")
                    .build();
        }
        armeService.delete(armeId);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/armes/redirectDelete")
                .build();
    }

    /**
     * Traite la requête GET de redirection après la suppression réussie d'une arme.
     *
     * @param model Le modèle de la vue.
     * @return La vue "success" avec les informations de succès.
     */
    @GetMapping("/armes/redirectDelete")
    public String redirectDelete(Model model){
        model.addAttribute("type", "deleted");
        model.addAttribute("item", "Arme");
        return "success";
    }

    /**
     * Traite la requête GET de redirection après une tentative de suppression infructueuse d'une arme en raison de son utilisation par un personnage.
     *
     * @param model Le modèle de la vue.
     * @return La vue "badDelete" avec les informations d'échec de suppression.
     */
    @GetMapping("/armes/redirectBadDelete")
    public String redirectBadDelete(Model model){
        model.addAttribute("item", "Arme");
        return "badDelete";
    }

    /**
     * Traite la requête POST pour ajouter une nouvelle arme.
     *
     * @param nomArme Le nom de l'arme.
     * @param dommage Les dommages de l'arme.
     * @param portee La portée de l'arme.
     * @param precission La précision de l'arme.
     * @param type_stat Le type de statistiques de l'arme.
     * @return La réponse HTTP avec la redirection vers l'URL "/armes/redirectAdd" si l'ajout réussit.
     */
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

    /**
     * Traite la requête GET de redirection après l'ajout réussi d'une arme.
     *
     * @param model Le modèle de la vue.
     * @return La vue "success" avec les informations de succès.
     */
    @GetMapping("/armes/redirectAdd")
    public String redirectAdd(Model model){
        model.addAttribute("type", "added");
        model.addAttribute("item", "Arme");
        return "success";
    }

    /**
     * Traite la requête POST pour modifier une arme existante.
     *
     * @param armeId L'identifiant de l'arme à modifier.
     * @param nomArme Le nom de l'arme modifié.
     * @param dommage Les dommages de l'arme modifiés.
     * @param portee La portée de l'arme modifiée.
     * @param precission La précision de l'arme modifiée.
     * @param type_stat Le type de statistiques de l'arme modifié.
     * @return La réponse HTTP avec la redirection vers l'URL "/armes/redirectEdit" si la modification réussit.
     */
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

        // Mettre à jour les propriétés de l'objet existant avec les nouvelles valeurs
        oldArme.setNom_arme(nomArme);
        oldArme.setDommage(dommage);
        oldArme.setPortee(portee);
        oldArme.setPrecission(precission);
        oldArme.setType_stat(type_stat);
        // ... mettre à jour les autres propriétés si nécessaire

        // Enregistrer l'objet modifié
        armeService.save(oldArme);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/armes/redirectEdit")
                .build();
    }

    /**
     * Traite la requête GET de redirection après la modification réussie d'une arme.
     *
     * @param model Le modèle de la vue.
     * @return La vue "success" avec les informations de succès.
     */
    @GetMapping("/armes/redirectEdit")
    public String redirectEdit(Model model){
        model.addAttribute("type", "edited");
        model.addAttribute("item", "Arme");
        return "success";
    }

    /**
     * Traite la requête POST pour envoyer le formulaire de modification d'une arme.
     *
     * @param armeId L'identifiant de l'arme à modifier.
     * @param model Le modèle de la vue.
     * @return La vue "armeForm" avec les détails de l'arme à modifier.
     */
    @PostMapping("/armesChange")
    public String sendChangeForm(@RequestParam("armeId") Integer armeId, Model model){
        Arme tempArme = armeService.findById(armeId);
        model.addAttribute("arme", tempArme);
        return "armeForm";
    }

    /**
     * Traite la requête POST pour rediriger vers le formulaire d'ajout d'une arme.
     *
     * @return La vue "armeAdd" pour le formulaire d'ajout d'une arme.
     */
    @PostMapping("/armes/addForm")
    public String redirectFormAddArme(){
        return "armeAdd";
    }
}
