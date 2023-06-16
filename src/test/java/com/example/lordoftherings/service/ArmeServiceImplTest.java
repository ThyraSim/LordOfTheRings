package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.repository.ArmeRepository;
import com.example.lordoftherings.repository.ClassesRepository;
import com.example.lordoftherings.repository.PersonnageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArmeServiceImplTest {

    @Autowired
    private ArmeService armeService;
    @Autowired
    private ArmeRepository armeRepository;

    @Autowired
    private PersonnageService personnageService;

    @Autowired
    private PersonnageRepository personnageRepository;

    @Autowired
    private ClassesRepository classesRepository;


    @Test
    void testFindAll() {
        Arme arme1 = new Arme();
        arme1.setNom_arme("arme1");
        Arme arme2 = new Arme();
        arme2.setNom_arme("arme2");
        Arme arme3 = new Arme();
        arme3.setNom_arme("arme3");

        armeService.save(arme1);
        armeService.save(arme2);
        armeService.save(arme3);

        List<Arme> armeList = armeService.findAll();
        int arme3Id = armeList.get(armeList.size() - 1).getId_arme();
        int arme2Id = armeList.get(armeList.size() - 2).getId_arme();
        int arme1Id = armeList.get(armeList.size() - 3).getId_arme();


        assertEquals("arme1", armeService.findById(arme1Id).getNom_arme());
        assertEquals("arme2", armeService.findById(arme2Id).getNom_arme());
        assertEquals("arme3", armeService.findById(arme3Id).getNom_arme());


        armeService.delete(arme3Id);
        armeService.delete(arme2Id);
        armeService.delete(arme1Id);


    }

    @Test
    void testDeleteExist() {
        Arme arme = new Arme();
        arme.setNom_arme("test");
        armeService.save(arme);
        List<Arme> armeList = armeService.findAll();
        int dernierId = armeList.get(armeList.size() - 1).getId_arme();

        armeService.delete(dernierId);

        assertThrows(RuntimeException.class,
                () -> armeService.findById(dernierId),
                "L'arme non trouvée - " + dernierId);
    }

    @Test
    void testDeleteNotExist() {

        int idNotExist = 0;

        int sizeBefore = armeService.findAll().size();

        armeService.delete(idNotExist);

        int sizeAfter = armeService.findAll().size();

        assertEquals(sizeBefore, sizeAfter);
    }


    @Test
    void testFindByIdExist() {
        Arme arme = new Arme();
        arme.setNom_arme("test");
        armeService.save(arme);

        List<Arme> armeList = armeService.findAll();
        int dernierId = armeList.get(armeList.size() - 1).getId_arme();
        armeService.findById(dernierId).getNom_arme();

        assertEquals("test", armeService.findById(dernierId).getNom_arme());

        armeService.delete(dernierId);

    }

    @Test
    void testFindByIdNotExist() {

        int idNotExist = 0;
        assertThrows(RuntimeException.class,
                () -> armeService.findById(idNotExist),
                "L'arme non trouvée - " + idNotExist);
    }

    @Test
    void testSaveArmeOnly() {
        Arme armeTest = new Arme();
        armeTest.setNom_arme("ArmeTest");
        armeTest.setDommage(0);
        armeTest.setPortee(80);
        armeTest.setType_stat("test");


        armeService.save(armeTest);


        List<Arme> armeList = armeService.findAll();
        int dernierId = armeList.get(armeList.size() - 1).getId_arme();
        Arme findarme = armeService.findById(dernierId);

        assertEquals(armeTest,findarme);


        armeService.delete(dernierId);
    }

    @Test
    void testSaveArmeWithPersonnageNoCascade() {
        //ici pas de cascade donc pas suposser trouvé

        Arme armeTest = new Arme();
        armeTest.setNom_arme("ArmeTest");
        armeTest.setDommage(0);
        armeTest.setPortee(80);
        armeTest.setType_stat("test");

        List<Personnage> listPersonnage = new ArrayList<>();

        Personnage personnageTest1 = new Personnage();
        personnageTest1.setNom_personnage("test1");
        personnageTest1.setDate_creation("0000-00-00");
        personnageTest1.setNiveau(-1);
        listPersonnage.add(personnageTest1);

        Personnage personnageTest2 = new Personnage();
        personnageTest1.setNom_personnage("test2");
        personnageTest1.setDate_creation("0000-00-00");
        personnageTest1.setNiveau(-1);
        listPersonnage.add(personnageTest2);

        armeTest.setPersonnages(listPersonnage);

        armeService.save(armeTest);


        List<Arme> armeList = armeService.findAll();
        int dernierId = armeList.get(armeList.size() - 1).getId_arme();
        Arme findarme = armeService.findById(dernierId);

        List<Personnage> personnageList = personnageService.findAll();
        Personnage personnage1 = personnageList.get(personnageList.size() - 2);
        Personnage personnage2 = personnageList.get(personnageList.size() - 1);


        assertNotEquals(personnageTest1,personnage1);
        assertNotEquals(personnageTest2,personnage2);
        assertEquals(armeTest,findarme);


        armeService.delete(dernierId);
    }

    @Test
    void testIsArmeInUseTrue() {

        //Création donnée pour le test
        Personnage personnageWithArme = new Personnage();
        Arme armeUsedByPersonnage = new Arme();


        armeService.save(armeUsedByPersonnage);

        // retrouve id de l'arme utilisé
        List<Arme> listAllArme = armeService.findAll();
        int armeUsedByPersonageID = listAllArme.get(listAllArme.size() - 1).getId_arme();

        //assignation de l'arme au personnage
        personnageWithArme.setArme(armeUsedByPersonnage);

        personnageService.save(personnageWithArme);


        //test
        assertEquals (true,
                armeService.isArmeInUse(armeUsedByPersonageID));


        //nettoyage
        personnageRepository.delete(personnageWithArme);
        armeRepository.delete(armeUsedByPersonnage);

    }

    @Test
    void testIsArmeInUseFalse() {

        //Création donnée pour le test
        Arme armeNotUsed = new Arme();
        armeService.save(armeNotUsed);

        // retrouve id de l'arme utilisé
        List<Arme> listAllArme = armeService.findAll();
        int armeUsedByPersonageID = listAllArme.get(listAllArme.size() - 1).getId_arme();

        //test
        assertEquals (false,
                armeService.isArmeInUse(armeUsedByPersonageID));

        //nettoyage
               armeRepository.delete(armeNotUsed);
    }

    @Test
    void testFindArmeByDommageBetween10And20() {
// Création pour le test
        Arme armeDmg9 = new Arme();
        armeDmg9.setDommage(9);
        Arme armeDmg10 = new Arme();
        armeDmg10.setDommage(10);
        Arme armeDmg11 = new Arme();
        armeDmg11.setDommage(11);
        Arme armeDmg19 = new Arme();
        armeDmg19.setDommage(19);
        Arme armeDmg20 = new Arme();
        armeDmg20.setDommage(20);
        Arme armeDmg21 = new Arme();
        armeDmg21.setDommage(21);

        armeService.save(armeDmg9);
        armeService.save(armeDmg10);
        armeService.save(armeDmg11);
        armeService.save(armeDmg19);
        armeService.save(armeDmg20);
        armeService.save(armeDmg21);

        Boolean armeDmg9Trouve = false;
        Boolean armeDmg10Trouve = false;
        Boolean armeDmg11Trouve = false;
        Boolean armeDmg19Trouve = false;
        Boolean armeDmg20Trouve = false;
        Boolean armeDmg21Trouve = false;

        List<Arme> listeArmeBetween10And20 = armeService.findArmeByDommageBetween(10, 20);

        for (Arme arme : listeArmeBetween10And20) {
            if (arme.equals(armeDmg9)) {
                armeDmg9Trouve = true;
            } else if (arme.equals(armeDmg10)) {
                armeDmg10Trouve = true;
            } else if (arme.equals(armeDmg11)) {
                armeDmg11Trouve = true;
            } else if (arme.equals(armeDmg19)) {
                armeDmg19Trouve = true;
            } else if (arme.equals(armeDmg20)) {
                armeDmg20Trouve = true;
            } else if (arme.equals(armeDmg21)) {
                armeDmg21Trouve = true;
            }
        }

        assertEquals(false,armeDmg9Trouve);
        assertEquals(true,armeDmg10Trouve);
        assertEquals(true,armeDmg11Trouve);
        assertEquals(true,armeDmg19Trouve);
        assertEquals(true,armeDmg20Trouve);
        assertEquals(false,armeDmg21Trouve);

        //Nettoyage

        armeRepository.delete(armeDmg9);
        armeRepository.delete(armeDmg10);
        armeRepository.delete(armeDmg11);
        armeRepository.delete(armeDmg19);
        armeRepository.delete(armeDmg20);
        armeRepository.delete(armeDmg21);

    }

    @Test
    void testFindArmesByClasse() {

        //création instance pour le test
        Classes classeTest = new Classes();
        classeTest.setNom_classe("classeTest");
        classesRepository.save(classeTest);

        Arme armeUseByClasseTest = new Arme();
        armeRepository.save(armeUseByClasseTest);
        Arme armeNotUseByClasseTest = new Arme();
        armeRepository.save(armeNotUseByClasseTest);
        Arme armeNotUsedAtAll = new Arme();
        armeRepository.save(armeNotUsedAtAll);


        Personnage personnageWithClasseTest = new Personnage();
        Personnage personnageWithoutClasseTest = new Personnage();

        personnageWithClasseTest.setClasse(classeTest);
        personnageWithoutClasseTest.setClasse(null);

        personnageWithClasseTest.setArme(armeUseByClasseTest);
        personnageWithoutClasseTest.setArme(armeNotUseByClasseTest);

        personnageRepository.save(personnageWithClasseTest);
        personnageRepository.save(personnageWithoutClasseTest);


       List<Arme> listeArmeUsedByClasseUsingArme = armeService.findArmesByClasse("classeTest");

       Boolean armeUseByClasseTestFound = false;
        Boolean armeNotUseByClasseTestFound = false;
        Boolean armeNotUsedAtAllFound = false;

        for (Arme arme:
             listeArmeUsedByClasseUsingArme) {
            if (arme.equals(armeUseByClasseTest)) {
                armeUseByClasseTestFound = true;
            }
            if (arme.equals(armeNotUseByClasseTest)) {
                armeNotUseByClasseTestFound = true;
            }
            if (arme.equals(armeNotUsedAtAll)) {
                armeNotUsedAtAllFound = true;
            }

        }

        assertEquals(true,armeUseByClasseTestFound);
        assertEquals(false,armeNotUseByClasseTestFound);
        assertEquals(false,armeNotUsedAtAllFound);

        personnageRepository.delete(personnageWithClasseTest);
        personnageRepository.delete(personnageWithoutClasseTest);
        classesRepository.delete(classeTest);
        armeRepository.delete(armeUseByClasseTest);
        armeRepository.delete(armeNotUseByClasseTest);
        armeRepository.delete(armeNotUsedAtAll);


    }
}