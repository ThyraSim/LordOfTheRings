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