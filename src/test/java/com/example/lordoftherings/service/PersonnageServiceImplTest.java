package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonnageServiceImplTest {

    @Autowired
    private PersonnageService personnageService;

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
    void testDelete() {
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
    void testSave() {
    }

    @Test
    void testFindByIdWithArmeAndClassesAndCompte() {
    }
}