package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.repository.ArmeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArmeServiceImplTest {

    @Autowired
    private ArmeService armeService;

    @Autowired
    private ArmeRepository armeRepository;

    @Test
    void testFindAll() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void testFindByIdExist() {
        Arme arme= new Arme();
        arme.setNom_arme("test");
        armeService.save(arme);

        List<Arme> armeList= armeService.findAll();
        int dernierId = armeList.get(armeList.size()-1).getId_arme();
        armeService.findById(dernierId).getNom_arme();

        assertEquals("test",armeService.findById(dernierId).getNom_arme() );

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
    void testSave() {
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