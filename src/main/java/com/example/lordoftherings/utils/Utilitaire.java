package com.example.lordoftherings.utils;

/**
 * Classe utilitaire contenant des méthodes utilitaires.
 */
public class Utilitaire {

    /**
     * Récupère l'extension de fichier à partir du nom de fichier.
     *
     * @param filename le nom de fichier
     * @return l'extension de fichier
     */
    public static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex >= 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }

}
