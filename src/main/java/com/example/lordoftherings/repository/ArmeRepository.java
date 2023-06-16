package com.example.lordoftherings.repository;

import com.example.lordoftherings.entity.Arme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmeRepository extends JpaRepository<Arme, Integer> {

    /**
     * Recherche les armes dont le dommage est compris entre une valeur minimale et une valeur maximale.
     *
     * @param minDmg Valeur minimale du dommage
     * @param maxDmg Valeur maximale du dommage
     * @return Liste des armes trouvées
     */
    @Query("SELECT a FROM Arme a WHERE a.dommage BETWEEN :minDmg AND :maxDmg")
    List<Arme> findArmeByDommageBetween(int minDmg, int maxDmg );


    /**
     * Recherche les armes utilisées par des personnages d'une classe spécifique.
     *
     * @param classe Nom de la classe
     * @return Liste des armes trouvées
     */
    @Query("SELECT a FROM Arme a INNER JOIN Personnage p ON p.arme.id_arme=a.id_arme INNER JOIN Classes c ON c.id_classe = p.classe.id_classe WHERE c.nom_classe = :classe")
    List<Arme> findArmesByClasse( String classe);


    /**
     * Recherche et renvoie une liste d'armes en fonction du type de statistique spécifié.
     *
     * @param typeStat Le type de statistique des armes à rechercher.
     * @return Une liste d'armes correspondant au type de statistique spécifié.
     */
    @Query("SELECT a FROM Arme a WHERE a.type_stat = :typeStat")
    List<Arme> findByTypeStat(String typeStat);





}
