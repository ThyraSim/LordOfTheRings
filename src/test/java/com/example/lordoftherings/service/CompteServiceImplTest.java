package com.example.lordoftherings.service;

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
    }

    @Test
    void testDelete() {
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
    void testSave() {
    }

    @Test
    void testFindCompteWithPersonnagesById() {
    }
}