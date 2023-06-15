package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.repository.PersonnageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonnageServiceImplTest {

    @Autowired
    private PersonnageService personnageService;

    @Autowired
    private ArmeService armeService;

    @Autowired
    private CompteService compteService;

    @Autowired
    private ClassesService classesService;

    @Autowired
    private PersonnageRepository personnageRepository;

    @Test
    void testFindAll() {

        Personnage personnage1 = new Personnage();
        personnage1.setNom_personnage("personnage1");
        Personnage personnage2 = new Personnage();
        personnage2.setNom_personnage("personnage2");
        Personnage personnage3 = new Personnage();
        personnage3.setNom_personnage("personnage3");

        personnageService.save(personnage1);
        personnageService.save(personnage2);
        personnageService.save(personnage3);

        List<Personnage> personnageList = personnageService.findAll();
        int personnage3Id = personnageList.get(personnageList.size() - 1).getId_personnage();
        int personnage2Id = personnageList.get(personnageList.size() - 2).getId_personnage();
        int personnage1Id = personnageList.get(personnageList.size() - 3).getId_personnage();

        assertEquals("personnage1", personnageService.findById(personnage1Id).getNom_personnage());
        assertEquals("personnage2", personnageService.findById(personnage2Id).getNom_personnage());
        assertEquals("personnage3", personnageService.findById(personnage3Id).getNom_personnage());

        personnageService.delete(personnage3Id);
        personnageService.delete(personnage2Id);
        personnageService.delete(personnage1Id);
    }

    @Test
    void testDeleteExist() {
        Personnage personnage = new Personnage();

        personnageService.save(personnage);
        List<Personnage> personnageList = personnageService.findAll();
        int dernierId = personnageList.get(personnageList.size() - 1).getId_personnage();

        personnageService.delete(dernierId);

        assertThrows(RuntimeException.class,
                () -> personnageService.findById(dernierId),
                "Le personnage non trouvé -" + dernierId);
    }

    @Test
    void testDeleteNotExist() {
        int idNotExist = 0;

        int sizeBefore = personnageService.findAll().size();

        personnageService.delete(idNotExist);

        int sizeAfter = personnageService.findAll().size();

        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    void testFindByIdExist() {
        Personnage personnage = new Personnage();
        personnage.setNom_personnage("test");


        Classes classe = new Classes();
        classe.setNom_classe("test");

        personnageService.save(personnage);

        List<Personnage> personnageList = personnageService.findAll();
        int dernierId = personnageList.get(personnageList.size() - 1).getId_personnage();
        personnageService.findById(dernierId).getNom_personnage();

        assertEquals("test", personnageService.findById(dernierId).getNom_personnage());

        personnageService.delete(dernierId);


    }

    @Test
    void testFindByIdNotExist() {

        int idNotExist = 0;
        assertThrows(RuntimeException.class,
                () -> personnageService.findById(idNotExist),
                "Le personnage non trouvé -" + idNotExist);
    }


    @Test
    void testSavePersonnageOnly() {
        Personnage personnageTest = new Personnage();
        personnageTest.setNom_personnage("TestPersonnage");
        personnageTest.setDate_creation("2023-06-15");
        personnageTest.setNiveau(1);

        personnageService.save(personnageTest);

        List<Personnage> personnageList = personnageService.findAll();
        int dernierId = personnageList.get(personnageList.size() - 1).getId_personnage();
        Personnage findPersonnage = personnageService.findById(dernierId);
        assertEquals(personnageTest, findPersonnage);

        personnageService.delete(dernierId);
    }

    @Test
    void testSavePersonnageWithCompteNoCascade() {
        // Pas cascade pour Compte

        Compte compteTest = new Compte();
        compteTest.setNom_utilisateur("TestUser");
        compteTest.setMotDePasse("password");
        compteTest.setDate_creation("0000-00-00");
        compteTest.setPremium(true);

        Personnage personnageTest = new Personnage();
        personnageTest.setNom_personnage("test1");
        personnageTest.setDate_creation("0000-00-00");
        personnageTest.setNiveau(-1);
        personnageTest.setCompte(compteTest);

        personnageService.save(personnageTest);

        List<Compte> compteList = compteService.findAll();
        int dernierId = compteList.get(compteList.size() - 1).getId_compte();
        Compte findCompte = compteService.findById(dernierId);

        List<Personnage> personnageList = personnageService.findAll();
        Personnage savedPersonnage = personnageList.get(personnageList.size() - 1);

        assertEquals(personnageTest, savedPersonnage);
        assertNotEquals(compteTest, findCompte);

        compteService.delete(dernierId);
        personnageRepository.delete(savedPersonnage);
    }

    @Test
    void testSavePersonnageWithClassesNoCascade() {
        // Pas cascade pour Classes

        Classes classesTest = new Classes();
        classesTest.setNom_classe("classesTest");

        Personnage personnageTest = new Personnage();
        personnageTest.setNom_personnage("test1");
        personnageTest.setDate_creation("0000-00-00");
        personnageTest.setNiveau(-1);
        personnageTest.setClasse(classesTest);

        personnageService.save(personnageTest);

        List<Classes> classesList = classesService.findAll();
        int dernierId = classesList.get(classesList.size() - 1).getId_classe();
        Classes findClasses = classesService.findById(dernierId);

        List<Personnage> personnageList = personnageService.findAll();
        Personnage savedPersonnage = personnageList.get(personnageList.size() - 1);

        assertEquals(personnageTest, savedPersonnage);
        assertNotEquals(classesTest, findClasses);

        classesService.delete(dernierId);
        personnageRepository.delete(savedPersonnage);
    }

    @Test
    void testSavePersonnageWithArmeNoCascade() {
        // Pas cascade pour arme

        Arme armeTest = new Arme();
        armeTest.setNom_arme("ArmeTest");

        Personnage personnageTest = new Personnage();
        personnageTest.setNom_personnage("test1");
        personnageTest.setDate_creation("0000-00-00");
        personnageTest.setNiveau(-1);
        personnageTest.setArme(armeTest);

        personnageService.save(personnageTest);

        List<Arme> armeList = armeService.findAll();
        int dernierId = armeList.get(armeList.size() - 1).getId_arme();
        Arme findArme = armeService.findById(dernierId);

        List<Personnage> personnageList = personnageService.findAll();
        Personnage savedPersonnage = personnageList.get(personnageList.size() - 1);

        assertEquals(personnageTest, savedPersonnage);
        assertNotEquals(armeTest, findArme);


        armeService.delete(dernierId);


    }


    @Test
    void testFindByIdWithArmeAndClassesAndCompte() {
        // Créer des données de test
        Compte compte = new Compte();
        compte.setNom_utilisateur("TestUser");
        compte.setMotDePasse("password");
        compte.setDate_creation("0000-00-00");
        compte.setPremium(true);
        compteService.save(compte);

        Arme arme = new Arme();
        arme.setNom_arme("ArmeTest");
        armeService.save(arme);

        Classes classe = new Classes();
        classe.setNom_classe("ClassesTest");
        classesService.save(classe);

        Personnage personnage = new Personnage();
        personnage.setNom_personnage("test1");
        personnage.setDate_creation("0000-00-00");
        personnage.setNiveau(-1);
        personnage.setCompte(compte);
        personnage.setArme(arme);
        personnage.setClasse(classe);
        personnageService.save(personnage);

        List<Personnage> personnageList = personnageService.findAll();
        Personnage personnageTrouve = personnageList.get(personnageList.size() - 1);


        // Effectuer les test
        assertNotNull(personnageTrouve);
     assertEquals(personnage,personnageTrouve);

        // Nettoyer les données de test
        personnageService.delete(personnage.getId_personnage());
        compteService.delete(compte.getId_compte());
        armeService.delete(arme.getId_arme());
        classesService.delete(classe.getId_classe());
    }
}