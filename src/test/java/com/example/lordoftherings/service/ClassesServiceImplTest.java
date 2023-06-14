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
        Classes classe1 = new Classes();
        classe1.setNom_classe("classe1");
        Classes classe2 = new Classes();
        classe2.setNom_classe("classe2");
        Classes classe3 = new Classes();
        classe3.setNom_classe("classe3");

        classesService.save(classe1);
        classesService.save(classe2);
        classesService.save(classe3);

        List<Classes> classeList = classesService.findAll();
        int classe3Id = classeList.get(classeList.size() - 1).getId_classe();
        int classe2Id = classeList.get(classeList.size() - 2).getId_classe();
        int classe1Id = classeList.get(classeList.size() - 3).getId_classe();

        assertEquals("classe1", classesService.findById(classe1Id).getNom_classe());
        assertEquals("classe2", classesService.findById(classe2Id).getNom_classe());
        assertEquals("classe3", classesService.findById(classe3Id).getNom_classe());

        classesService.delete(classe3Id);
        classesService.delete(classe2Id);
        classesService.delete(classe1Id);
    }

    @Test
    void testDeleteExist() {
        Classes classe = new Classes();


        classesService.save(classe);
        List<Classes> classeList = classesService.findAll();
        int dernierId = classeList.get(classeList.size() - 1).getId_classe();

        classesService.delete(dernierId);

        assertThrows(RuntimeException.class,
                () -> classesService.findById(dernierId),
                "La classe non trouvée -" + dernierId);
    }

    @Test
    void testDeleteNotExist() {

        int idNotExist = 0;

        int sizeBefore = classesService.findAll().size();

        classesService.delete(idNotExist);

        int sizeAfter = classesService.findAll().size();

        assertEquals(sizeBefore, sizeAfter);
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