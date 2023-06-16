package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;
import com.example.lordoftherings.utils.Utilitaire;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ClassesControlleur {
    private ClassesService classesService;

    @Autowired
    public ClassesControlleur(ClassesService classesService){
        this.classesService = classesService;
    }

    /**
     * Traite la requête POST pour choisir une classe.
     *
     * @param classeId L'identifiant de la classe choisie.
     * @return La redirection vers l'URL "/classes/{classeId}".
     */
    @PostMapping("/classesChoice")
    public String choixClasse(@RequestParam("classeId") String classeId){
        return "redirect:/classes/" + classeId;
    }

    /**
     * Traite la requête GET pour afficher toutes les classes.
     *
     * @param model Le modèle de la vue.
     * @param sessionId L'identifiant de session (cookie).
     * @return La vue "affichageClasses" avec la liste des classes.
     */
    @GetMapping("/classes")
    public String findAll(Model model,
                          @CookieValue(name = "sessionId", required = false) Integer sessionId){
        if(sessionId != null && sessionId != 0){
            return "redirect:/comptes/"+sessionId;
        } else if(sessionId == null){
            return "login";
        }

        List<Classes> classes = classesService.findAll();
        model.addAttribute("classes", classes);
        return "affichageClasses";
    }

    /**
     * Traite la requête GET pour afficher une classe spécifique.
     *
     * @param model Le modèle de la vue.
     * @param classeId L'identifiant de la classe à afficher.
     * @param sessionId L'identifiant de session (cookie).
     * @return La vue "singleClasse" avec les détails de la classe.
     */
    @GetMapping("/classes/{classeId}")
    public String showClasse(Model model,
                             @PathVariable Integer classeId,
                             @CookieValue(name = "sessionId", required = false) Integer sessionId){

        if(sessionId != null && sessionId != 0){
            return "redirect:/comptes/"+sessionId;
        } else if(sessionId == null){
            return "login";
        }
        Classes tempClasse = classesService.findById(classeId);
        model.addAttribute("classe", tempClasse);
        model.addAttribute("imagePath", "/images/"+tempClasse.getImage());
        return "singleClasse";
    }

    /**
     * Traite la requête POST pour supprimer une classe.
     *
     * @param classeId L'identifiant de la classe à supprimer.
     * @return La réponse HTTP avec la redirection vers l'URL "/classes/redirectDelete" si la suppression réussit.
     */
    @PostMapping("/classes/delete")
    public ResponseEntity<String> deleteClasse(@RequestParam("classeId") Integer classeId){
        Classes classe = classesService.findById(classeId);
        List<Personnage> personnages = classe.getPersonnages();
        if (classesService.isClasseInUse(classeId)) {
            // Au moins un personnage utilise une classe, impossible de supprimer
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/classes/redirectBadDelete")
                    .build();
        }

        String imagePath = "src/main/resources/static/images/" + classe.getImage();
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            imageFile.delete();
        }
        classesService.delete(classeId);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/classes/redirectDelete")
                .build();
    }

    /**
     * Traite la requête GET de redirection après la suppression réussie d'une classe.
     *
     * @param model Le modèle de la vue.
     * @return La vue "success" avec les informations de succès.
     */
    @GetMapping("/classes/redirectDelete")
    public String redirectDelete(Model model){
        model.addAttribute("type", "deleted");
        model.addAttribute("item", "Classe");
        return "success";
    }

    /**
     * Traite la requête GET de redirection en cas de suppression invalide d'une classe.
     *
     * @param model Le modèle de la vue.
     * @return La vue "badDelete" avec les informations de suppression invalide.
     */
    @GetMapping("/classes/redirectBadDelete")
    public String redirectBadDelete(Model model){
        model.addAttribute("item", "Classe");
        return "badDelete";
    }

    /**
     * Traite la requête POST pour ajouter une classe.
     *
     * @param nomClasse Le nom de la classe à ajouter.
     * @param puissance La puissance de la classe à ajouter.
     * @param agilete L'agilité de la classe à ajouter.
     * @param constitution La constitution de la classe à ajouter.
     * @param intelligence L'intelligence de la classe à ajouter.
     * @param file Le fichier image de la classe à ajouter.
     * @return La réponse HTTP avec la redirection vers l'URL "/classes/redirectAdd" si l'ajout réussit.
     */
    @PostMapping("/classes/add")
    public ResponseEntity<String> addClasse(@RequestParam("nom_classe") String nomClasse,
                                            @RequestParam("puissance") int puissance,
                                            @RequestParam("agilete") int agilete,
                                            @RequestParam("constitution") int constitution,
                                            @RequestParam("intelligence") int intelligence,
                                            @RequestParam("image") MultipartFile file){
        String uploadDirectory = "src/main/resources/static/images";
        String originalFilename = file.getOriginalFilename();
        String filename = nomClasse + "." + Utilitaire.getFileExtension(originalFilename);
        String filePath = uploadDirectory + "/" + filename;

        boolean check = true;
        List<Classes> classes = classesService.findAll();
        boolean fileExists = classes.stream().anyMatch(classe -> classe.getImage().equals(filePath));
        if(!fileExists){
            try {
                File destFile = new File(filePath);
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.ok("Failed");
            }
        }

        Classes classe = new Classes();
        classe.setNom_classe(nomClasse);
        classe.setPuissance(puissance);
        classe.setAgilete(agilete);
        classe.setConstitution(constitution);
        classe.setIntelligence(intelligence);
        classe.setImage(filename);

        classesService.save(classe);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/classes/redirectAdd")
                .build();
    }

    /**
     * Traite la requête GET de redirection après l'ajout réussi d'une classe.
     *
     * @param model Le modèle de la vue.
     * @return La vue "success" avec les informations de succès.
     */
    @GetMapping("/classes/redirectAdd")
    public String redirectAdd(Model model){
        model.addAttribute("type", "added");
        model.addAttribute("item", "Classe");
        return "success";
    }

    /**
     * Traite la requête POST pour modifier une classe existante.
     *
     * @param classeId L'identifiant de la classe à modifier.
     * @param nomClasse Le nouveau nom de la classe.
     * @param puissance La nouvelle puissance de la classe.
     * @param agilete La nouvelle agilité de la classe.
     * @param constitution La nouvelle constitution de la classe.
     * @param intelligence La nouvelle intelligence de la classe.
     * @return La réponse HTTP avec la redirection vers l'URL "/classes/redirectEdit" si la modification réussit.
     */
    @PostMapping("/classes/edit")
    public ResponseEntity<String> modifyClasse(@RequestParam("classeId") Integer classeId,
                                               @RequestParam("nom_classe") String nomClasse,
                                               @RequestParam("puissance") int puissance,
                                               @RequestParam("agilete") int agilete,
                                               @RequestParam("constitution") int constitution,
                                               @RequestParam("intelligence") int intelligence){

        Classes classe = classesService.findById(classeId);

        if (classe == null) {
            return ResponseEntity.notFound().build();
        }

        classe.setNom_classe(nomClasse);
        classe.setPuissance(puissance);
        classe.setAgilete(agilete);
        classe.setConstitution(constitution);
        classe.setIntelligence(intelligence);

        // Enregistrer l'élément modifié
        classesService.save(classe);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/classes/redirectEdit")
                .build();
    }

    /**
     * Traite la requête GET de redirection après la modification réussie d'une classe.
     *
     * @param model Le modèle de la vue.
     * @return La vue "success" avec les informations de succès.
     */
    @GetMapping("/classes/redirectEdit")
    public String redirectEdit(Model model){
        model.addAttribute("type", "edited");
        model.addAttribute("item", "Classe");
        return "success";
    }

    /**
     * Traite la requête POST pour envoyer le formulaire de modification de classe.
     *
     * @param classeId L'identifiant de la classe à modifier.
     * @param model Le modèle de la vue.
     * @return La vue "classeForm" avec les détails de la classe à modifier.
     */
    @PostMapping("/classesChange")
    public String sendChangeForm(@RequestParam("classeId") Integer classeId, Model model){
        Classes tempClasse = classesService.findById(classeId);
        model.addAttribute("classe", tempClasse);
        return "classeForm";
    }

    /**
     * Traite la requête POST de redirection vers le formulaire d'ajout de classe.
     *
     * @return La vue "classeAdd" pour le formulaire d'ajout de classe.
     */
    @PostMapping("/classes/addForm")
    public String redirectFormAddClasse(){

        return "classeAdd";
    }
}
