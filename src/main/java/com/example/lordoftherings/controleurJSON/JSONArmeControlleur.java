package com.example.lordoftherings.controleurJSON;

import com.example.lordoftherings.entity.Arme;
import com.example.lordoftherings.service.ArmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur JSON pour les opérations liées aux armes.
 */
@RestController
public class JSONArmeControlleur {
    private ArmeService armeService;

    @Autowired
    public JSONArmeControlleur(ArmeService armeService) {
        this.armeService = armeService;
    }

    /**
     * Récupère toutes les armes.
     *
     * @return Liste de toutes les armes.
     */
    @GetMapping("/JSON/armes")
    public List<Arme> findAll() {
        return armeService.findAll();
    }

    /**
     * Récupère une arme par son identifiant.
     *
     * @param armeId Identifiant de l'arme.
     * @return L'arme correspondante.
     */
    @GetMapping("/JSON/armes/{armeId}")
    public Arme findById(@PathVariable Integer armeId) {
        return armeService.findById(armeId);
    }

    /**
     * Recherche des armes selon une plage de dommages.
     *
     * @param minDmg Valeur minimale des dommages.
     * @param maxDmg Valeur maximale des dommages.
     * @return Liste des armes correspondantes à la plage de dommages.
     */
    @GetMapping("/JSON/armes/recherche/dmg/{minDmg}/{maxDmg}")
    public List<Arme> rechercheArme(@PathVariable int minDmg, @PathVariable int maxDmg) {
        return armeService.findArmeByDommageBetween(minDmg, maxDmg);
    }

    /**
     * Recherche des armes appartenant à une classe spécifique.
     *
     * @param classe Nom de la classe.
     * @return Liste des armes de la classe spécifiée.
     */
    @GetMapping("/JSON/armes/recherche/classe/{classe}")
    public List<Arme> findArmesByClasse(@PathVariable String classe) {
        return armeService.findArmesByClasse(classe);
    }

    /**
     * Supprime une arme.
     *
     * @param armeId Identifiant de l'arme à supprimer.
     * @return Message indiquant la suppression de l'arme.
     */
    @DeleteMapping("/JSON/armes/{armeId}")
    public String deleteArme(@PathVariable Integer armeId) {
        Arme tempArme = armeService.findById(armeId);
        armeService.delete(armeId);
        return "Arme supprimée : " + armeId;
    }

    /**
     * Ajoute une nouvelle arme.
     *
     * @param arme Objet Arme à ajouter.
     * @return L'arme ajoutée.
     */
    @PostMapping("/JSON/armes/add")
    public Arme addArme(@RequestBody Arme arme) {
        return armeService.save(arme);
    }

    /**
     * Modifie une arme existante.
     *
     * @param armeId  Identifiant de l'arme à modifier.
     * @param newArme Nouvelles données de l'arme.
     * @return L'arme modifiée.
     */
    @PutMapping("/JSON/armes/edit/{armeId}")
    public Arme modifyArme(@PathVariable Integer armeId, @RequestBody Arme newArme) {
        Arme oldArme = armeService.findById(armeId);

        // Met à jour les propriétés de l'objet existant avec les nouvelles valeurs
        oldArme.setNom_arme(newArme.getNom_arme());
        oldArme.setDommage(newArme.getDommage());
        oldArme.setPortee(newArme.getPortee());
        oldArme.setPrecission(newArme.getPrecission());
        oldArme.setType_stat(newArme.getType_stat());
        // ... met à jour les autres propriétés si nécessaire

        // Enregistre l'objet modifié
        return armeService.save(oldArme);
    }

    /**
     * Recherche des armes par type de statistique.
     *
     * @param typeStat Type de statistique à rechercher.
     * @return Liste des armes correspondant au type de statistique.
     */
    @GetMapping("/JSON/recherche/armes/typeStat/{typeStat}")
    public List<Arme> RechercheByTypeStat(@PathVariable String typeStat) {
        return armeService.rechercheArmeTypeStat(typeStat);
    }
}
