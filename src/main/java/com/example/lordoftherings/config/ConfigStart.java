package com.example.lordoftherings.config;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.service.ArmeService;
import com.example.lordoftherings.service.ClassesService;
import com.example.lordoftherings.service.CompteService;
import com.example.lordoftherings.service.PersonnageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe de configuration pour l'initialisation de la base de données avec des données d'exemple.
 */

@Component
public class ConfigStart implements CommandLineRunner {
    private ArmeService armeService;
    private ClassesService classesService;
    private CompteService compteService;
    private PersonnageService personnageService;


    public ConfigStart(ArmeService armeService, ClassesService classesService, CompteService compteService, PersonnageService personnageService) {
        this.armeService = armeService;
        this.classesService = classesService;
        this.compteService = compteService;
        this.personnageService = personnageService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (compteService.findAll().isEmpty()) {
            initialisationBDD(armeService, classesService, compteService, personnageService);
        }
    }

    private void initialisationBDD(ArmeService armeService, ClassesService classesService, CompteService compteService, PersonnageService personnageService) {

        //on cree les armes
        List<Arme> initArmeList = new ArrayList<>();
        initArmeList.add(new Arme("Hachette", 10, 1.0, 80, "str"));
        initArmeList.add(new Arme("Épée", 15, 1.2, 90, "str"));
        initArmeList.add(new Arme("Marteau", 18, 1.8, 70, "str"));
        initArmeList.add(new Arme("Lance", 12, 1.5, 75, "str"));
        initArmeList.add(new Arme("Arc", 8, 3, 85, "agi"));
        initArmeList.add(new Arme("Dague", 7, 0.8, 95, "agi"));
        initArmeList.add(new Arme("Fusil", 20, 4, 85, "int"));
        initArmeList.add(new Arme("Bâton", 6, 2.5, 90, "int"));
        initArmeList.add(new Arme("baton chatcroyable", 3, 1.5, 100, "int"));


        for (Arme a :
                initArmeList) {
            armeService.save(a);
            System.out.println("Sauvegarde Arme " + a);
        }


        //on cree les classes
        List<Classes> initClassesList = new ArrayList<>();

        initClassesList.add(new Classes("Barbare", 5, 3, 5, 1, "Barbarian.jpg"));
        initClassesList.add(new Classes("Magichat", 1, 3, 2, 5, "Magichat.jpg"));
        initClassesList.add(new Classes("Wizard", 2, 5, 3, 2, "wizard.jpg"));
        initClassesList.add(new Classes("Rogue", 3, 3, 3, 3, "Rogue.jpg"));

        for (Classes c :
                initClassesList) {
            classesService.save(c);
            System.out.println("Sauvegarde Classes " + c);
        }

        //on creer les comptes
        List<Compte> initCompteList = new ArrayList<>();


        initCompteList.add(new Compte("Chat", "Poisson", "2023-05-23", false));
        initCompteList.add(new Compte("Orignal", "Cheese", "2023-05-19", true));
        initCompteList.add(new Compte("Marmotte", "Castor", "1991-09-20", true));
        initCompteList.add(new Compte("Tyzral", "Dino", "2001-05-03", true));
        initCompteList.add(new Compte("Knik", "Knok", "1991-09-20", true));


        for (Compte c :
                initCompteList) {
            compteService.save(c);
            System.out.println("Sauvegarde Compte " + c);
        }


        //on creer les personnages

        List<Personnage> initPersonnageList = new ArrayList<>();


        initPersonnageList.add(new Personnage("ChatHeroique", "2023-05-23", 1, compteService.findById(1), armeService.findById(1), classesService.findById(1)));
        initPersonnageList.add(new Personnage("ChatGratte", "2023-05-29", 10, compteService.findById(1), armeService.findById(5), classesService.findById(4)));
        initPersonnageList.add(new Personnage("George", "2023-05-29", 50, compteService.findById(1), armeService.findById(7), classesService.findById(3)));
        initPersonnageList.add(new Personnage("Patate", "2023-05-29", 60, compteService.findById(1), armeService.findById(9), classesService.findById(3)));
        initPersonnageList.add(new Personnage("Cheese", "2023-05-29", 70, compteService.findById(1), armeService.findById(8), classesService.findById(3)));
        initPersonnageList.add(new Personnage("Bruh", "2023-05-23", 49, compteService.findById(1), armeService.findById(2), classesService.findById(1)));
        initPersonnageList.add(new Personnage("Meh", "2023-05-23", 10, compteService.findById(1), armeService.findById(1), classesService.findById(2)));
        initPersonnageList.add(new Personnage("Dafuq", "2023-05-23", 50, compteService.findById(1), armeService.findById(3), classesService.findById(1)));


        initPersonnageList.add(new Personnage("Pikachu", "2023-06-01", 20, compteService.findById(2), armeService.findById(4), classesService.findById(1)));
        initPersonnageList.add(new Personnage("Charizard", "2023-06-02", 30, compteService.findById(2), armeService.findById(6), classesService.findById(2)));
        initPersonnageList.add(new Personnage("Blastoise", "2023-06-03", 40, compteService.findById(2), armeService.findById(8), classesService.findById(3)));
        initPersonnageList.add(new Personnage("Mewtwo", "2023-06-04", 50, compteService.findById(2), armeService.findById(8), classesService.findById(4)));
        initPersonnageList.add(new Personnage("Gengar", "2023-06-05", 60, compteService.findById(2), armeService.findById(2), classesService.findById(1)));


        initPersonnageList.add(new Personnage("Arthur", "2023-06-01", 20, compteService.findById(3), armeService.findById(4), classesService.findById(1)));
        initPersonnageList.add(new Personnage("Merlin", "2023-06-02", 30, compteService.findById(3), armeService.findById(6), classesService.findById(2)));
        initPersonnageList.add(new Personnage("Galahad", "2023-06-03", 40, compteService.findById(3), armeService.findById(8), classesService.findById(3)));
        initPersonnageList.add(new Personnage("Lancelot", "2023-06-04", 50, compteService.findById(3), armeService.findById(4), classesService.findById(4)));
        initPersonnageList.add(new Personnage("Guinevere", "2023-06-05", 60, compteService.findById(3), armeService.findById(2), classesService.findById(1)));


        initPersonnageList.add(new Personnage("Potter", "2023-06-06", 25, compteService.findById(4), armeService.findById(9), classesService.findById(3)));
        initPersonnageList.add(new Personnage("Granger", "2023-06-07", 35, compteService.findById(4), armeService.findById(9), classesService.findById(3)));
        initPersonnageList.add(new Personnage("Weasley", "2023-06-08", 45, compteService.findById(4), armeService.findById(9), classesService.findById(3)));

        initPersonnageList.add(new Personnage("God", "0000-00-00", 9999, compteService.findById(5), armeService.findById(9), classesService.findById(2)));

        for (Personnage p :
                initPersonnageList) {
            personnageService.save(p);
            System.out.println("Sauvegarde personnage " + p);
        }

    }
}


