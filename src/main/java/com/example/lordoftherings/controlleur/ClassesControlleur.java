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

    @PostMapping("/classesChoice")
    public String choixClasse(@RequestParam("classeId") String classeId){
        return "redirect:/classes/" + classeId;
    }

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

    @PostMapping("/classes/delete")
    public ResponseEntity<String> deleteClasse(@RequestParam("classeId") Integer classeId){
        Classes classe = classesService.findById(classeId);
        List<Personnage> personnages = classe.getPersonnages();
        if (classesService.isClasseInUse(classeId)) {
            // At least one personnage is using the classe, return a response indicating it cannot be deleted
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

    @GetMapping("/classes/redirectDelete")
    public String redirectDelete(Model model){
        model.addAttribute("type", "deleted");
        model.addAttribute("item", "Classe");
        return "success";
    }

    @GetMapping("/classes/redirectBadDelete")
    public String redirectBadDelete(Model model){
        model.addAttribute("item", "Classe");
        return "badDelete";
    }

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

    @GetMapping("/classes/redirectAdd")
    public String redirectAdd(Model model){
        model.addAttribute("type", "added");
        model.addAttribute("item", "Classe");
        return "success";
    }

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

        // Save the modified item
        classesService.save(classe);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/classes/redirectEdit")
                .build();
    }

    @GetMapping("/classes/redirectEdit")
    public String redirectEdit(Model model){
        model.addAttribute("type", "edited");
        model.addAttribute("item", "Classe");
        return "success";
    }

    @PostMapping("/classesChange")
    public String sendChangeForm(@RequestParam("classeId") Integer classeId, Model model){
        Classes tempClasse = classesService.findById(classeId);
        model.addAttribute("classe", tempClasse);
        return "classeForm";
    }

    @PostMapping("/classes/addForm")
    public String redirectFormAddClasse(){

        return "classeAdd";
    }
}
