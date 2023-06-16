package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.repository.CompteRepository;
import com.example.lordoftherings.repository.PersonnageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompteServiceImplTest {

    @Autowired
    private CompteService compteService;

    @Autowired
    private
    PersonnageService personnageService;

    @Autowired
    PersonnageRepository personnageRepository;

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
    void testSaveCompteWithPersonnageCascade() {
        // Avec cascade pour personnage

        // Créer des données de test
        Compte compteTest = new Compte();
        compteTest.setNom_utilisateur("TestUser");
        compteTest.setMotDePasse("password");
        compteTest.setDate_creation("0000-00-00");
        compteTest.setPremium(true);

        List<Personnage> listPersonnage = new ArrayList<>();

        Personnage personnageTest1 = new Personnage();
        personnageTest1.setNom_personnage("test1");
        personnageTest1.setDate_creation("0000-00-00");
        personnageTest1.setNiveau(-1);
        personnageTest1.setCompte(compteTest);
        listPersonnage.add(personnageTest1);


        compteTest.setPersonnages(listPersonnage);

        compteService.save(compteTest);

        List<Compte> compteList = compteService.findAll();
        int dernierId = compteList.get(compteList.size() - 1).getId_compte();
        Compte findCompte = compteService.findById(dernierId);

        List<Personnage> personnageList = personnageService.findAll();
        Personnage personnage1 = personnageList.get(personnageList.size() - 1);


        assertEquals(personnageTest1, personnage1);


        assertEquals(compteTest, findCompte);


        compteService.delete(dernierId);
        personnageRepository.delete(personnage1);


    }

    @Test
    void testFindCompteWithPersonnagesByIdExistingId() {
        // Create test data
        Compte compte = new Compte();
        compte.setNom_utilisateur("TestUser");
        compte.setMotDePasse("password");
        compte.setDate_creation("0000-00-00");
        compte.setPremium(true);
        compteService.save(compte);

        Personnage personnage1 = new Personnage();
        personnage1.setNom_personnage("test1");
        personnage1.setDate_creation("0000-00-00");
        personnage1.setNiveau(-1);
        personnage1.setCompte(compte);
        personnageService.save(personnage1);

        Personnage personnage2 = new Personnage();
        personnage2.setNom_personnage("test2");
        personnage2.setDate_creation("0000-00-00");
        personnage2.setNiveau(1);
        personnage2.setCompte(compte);
        personnageService.save(personnage2);


        Compte foundCompte = compteService.findCompteWithPersonnagesById(compte.getId_compte());
        assertNotNull(foundCompte);
        assertEquals(compte, foundCompte);
        assertEquals(2, foundCompte.getPersonnages().size());


        personnageService.delete(personnage1.getId_personnage());
        personnageService.delete(personnage2.getId_personnage());
        compteService.delete(compte.getId_compte());
    }


    @Test
    void testFindCompteWithPersonnagesById_NonExistingId() {

        Integer nonExistingId = 0;
        Compte nonExistingCompte = compteService.findCompteWithPersonnagesById(nonExistingId);
        assertNull(nonExistingCompte);
    }
}