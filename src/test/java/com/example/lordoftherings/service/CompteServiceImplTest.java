package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Compte;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompteServiceImplTest {

    @Autowired
    private CompteService compteService;
    @Test
    void testFindAll() {
        Compte compte1 = new Compte();
        compte1.setNom_utilisateur("compte1");
        Compte compte2 = new Compte();
        compte2.setNom_utilisateur("compte2");
        Compte compte3 = new Compte();
        compte3.setNom_utilisateur("compte3");

        compteService.save(compte1);
        compteService.save(compte2);
        compteService.save(compte3);

        List<Compte> compteList = compteService.findAll();
        int compte3Id = compteList.get(compteList.size() - 1).getId_compte();
        int compte2Id = compteList.get(compteList.size() - 2).getId_compte();
        int compte1Id = compteList.get(compteList.size() - 3).getId_compte();

        assertEquals("compte1", compteService.findById(compte1Id).getNom_utilisateur());
        assertEquals("compte2", compteService.findById(compte2Id).getNom_utilisateur());
        assertEquals("compte3", compteService.findById(compte3Id).getNom_utilisateur());

        compteService.delete(compte3Id);
        compteService.delete(compte2Id);
        compteService.delete(compte1Id);
    }

    @Test
    void testDeleteExist() {
        Compte compte = new Compte();

        compteService.save(compte);
        List<Compte> compteList = compteService.findAll();
        int dernierId = compteList.get(compteList.size() - 1).getId_compte();

        compteService.delete(dernierId);

        assertThrows(RuntimeException.class,
                () -> compteService.findById(dernierId),
                "Le compte non trouvé -" + dernierId);
    }

    @Test
    void testDeleteNotExist() {

        int idNotExist = 0;

        int sizeBefore = compteService.findAll().size();

        compteService.delete(idNotExist);

        int sizeAfter = compteService.findAll().size();

        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    void testFindByIdExist() {
        Compte compte = new Compte();
        compte.setNom_utilisateur("test");


        compteService.save(compte);

        List<Compte> compteList = compteService.findAll();
        int dernierId = compteList.get(compteList.size() - 1).getId_compte();
        compteService.findById(dernierId).getNom_utilisateur();

        assertEquals("test", compteService.findById(dernierId).getNom_utilisateur());

        compteService.delete(dernierId);
    }

    @Test
    void testFindByIdNotExist() {

        int idNotExist = 0;
        assertThrows(RuntimeException.class,
                () -> compteService.findById(idNotExist),
                "Le compte non trouvé -" + idNotExist);
    }

    @Test
    void testSaveCompteOnly() {
        Compte compteTest = new Compte();
        compteTest.setNom_utilisateur("testuser");
        compteTest.setMotDePasse("password");
        compteTest.setDate_creation("2023-06-15");
        compteTest.setPremium(true);

        compteService.save(compteTest);

        List<Compte> compteList = compteService.findAll();
        int dernierId = compteList.get(compteList.size() - 1).getId_compte();
        Compte findCompte = compteService.findById(dernierId);
        assertEquals(compteTest, findCompte);

        compteService.delete(dernierId);
    }

    @Test
    void testFindCompteWithPersonnagesById() {
    }
}