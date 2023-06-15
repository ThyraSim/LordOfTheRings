package com.example.lordoftherings.service;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.entity.Classes;
import com.example.lordoftherings.entity.Personnage;
import com.example.lordoftherings.repository.ArmeRepository;
import com.example.lordoftherings.repository.ClassesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassesServiceImplTest {

    @Autowired
    private ClassesService classesService;

    @Autowired
    private PersonnageService personnageService;

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
    void testSaveClassesOnly() {
        Classes classesTest = new Classes();
        classesTest.setNom_classe("classeTest");
        classesTest.setPuissance(0);
        classesTest.setAgilete(0);
        classesTest.setConstitution(0);
        classesTest.setIntelligence(0);

        classesService.save(classesTest);


        List<Classes> classesList = classesService.findAll();
        int dernierId = classesList.get(classesList.size() - 1).getId_classe();
        Classes findClasses = classesService.findById(dernierId);
        assertEquals(classesTest, findClasses);


        classesService.delete(dernierId);
    }

    @Test
    void testSaveClassesWithPersonnageNoCascade() {
        // Pas de cascade donc pas de persistance

        Classes classesTest = new Classes();
        classesTest.setNom_classe("ClasseTest");
        classesTest.setPuissance(0);
        classesTest.setAgilete(0);
        classesTest.setConstitution(0);
        classesTest.setIntelligence(0);

        List<Personnage> listPersonnage = new ArrayList<>();

        Personnage personnageTest1 = new Personnage();
        personnageTest1.setNom_personnage("test1");
        personnageTest1.setDate_creation("0000-00-00");
        personnageTest1.setNiveau(-1);
        listPersonnage.add(personnageTest1);

        Personnage personnageTest2 = new Personnage();
        personnageTest2.setNom_personnage("test2");
        personnageTest2.setDate_creation("0000-00-00");
        personnageTest2.setNiveau(-1);
        listPersonnage.add(personnageTest2);

        classesTest.setPersonnages(listPersonnage);

        classesService.save(classesTest);

        List<Classes> classesList = classesService.findAll();
        int dernierId = classesList.get(classesList.size() - 1).getId_classe();
        Classes findClasses = classesService.findById(dernierId);

        List<Personnage> personnageList = personnageService.findAll();
        Personnage personnage1 = personnageList.get(personnageList.size() - 2);
        Personnage personnage2 = personnageList.get(personnageList.size() - 1);

        assertNotEquals(personnageTest1, personnage1);
        assertNotEquals(personnageTest2, personnage2);
        assertEquals(classesTest, findClasses);

        classesService.delete(dernierId);
    }

    @Test
    void testIsClasseInUse() {
    }
}