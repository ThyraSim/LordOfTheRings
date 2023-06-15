package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.repository.ArmeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PersonnageService personnageService;


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
    void testIsArmeInUse() {
    }

    @Test
    void testFindArmeByDommageBetween() {
    }

    @Test
    void testFindArmesByClasse() {
    }
}