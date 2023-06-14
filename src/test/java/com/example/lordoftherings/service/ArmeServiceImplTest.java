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

        List<Arme> armeList= armeService.findAll();
        int arme3Id = armeList.get(armeList.size()-1).getId_arme();
        int arme2Id = armeList.get(armeList.size()-2).getId_arme();
        int arme1Id = armeList.get(armeList.size()-3).getId_arme();



        assertEquals("arme1",armeService.findById(arme1Id).getNom_arme());
        assertEquals("arme2",armeService.findById(arme2Id).getNom_arme());
        assertEquals("arme3",armeService.findById(arme3Id).getNom_arme());



        armeService.delete(arme3Id);
        armeService.delete(arme2Id);
        armeService.delete(arme1Id);


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