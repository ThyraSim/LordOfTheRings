package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.repository.ArmeRepository;
import com.example.lordoftherings.repository.ClassesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassesServiceImplTest {

    @Autowired
    private ClassesService classesService;

    @Autowired
    private ClassesRepository classesRepository;

    @Test
    void testFindAll() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void testFindByIdExist() {
        Classes classe= new Classes();
        classe.setNom_classe("test");
        classesService.save(classe);

        List<Classes> classesList= classesService.findAll();
        int dernierId = classesList.get(classesList.size()-1).getId_classe();
        classesService.findById(dernierId).getNom_classe();

        assertEquals("test",classesService.findById(dernierId).getId_classe() );

        classesService.delete(dernierId);
    }
    @Test
    void testFindByIdNotExist() {

        int idNotExist = 0;
        assertThrows(RuntimeException.class,
                () -> classesService.findById(idNotExist),
                "La classe non trouvée -" + idNotExist);
    }

    @Test
    void testSave() {
    }

    @Test
    void testIsClasseInUse() {
    }
}