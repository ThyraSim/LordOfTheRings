package com.example.lordoftherings.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilitaireTest {



    @Test
    void testGetFileExtensionExist() {
        String filename =  "classeName.jpg";
        String classeName = "classeName";

        assertEquals("jpg",Utilitaire.getFileExtension(filename));

    }
    @Test
    void testGetFileExtensionNotExist() {
        String filename =  "classeName";
        String classeName = "classeName";

        assertEquals("",Utilitaire.getFileExtension(filename));

    }
}