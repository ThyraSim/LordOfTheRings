package com.example.lordoftherings.repository;

import com.example.lordoftherings.entity.Arme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmeRepository extends JpaRepository<Arme, Integer> {
    @Query("SELECT a FROM Arme a WHERE a.dommage BETWEEN :minDmg AND :maxDmg")
    List<Arme> findArmeByDommageBetween(int minDmg, int maxDmg );


    @Query("SELECT a FROM Arme a INNER JOIN Personnage p ON p.arme.id_arme=a.id_arme INNER JOIN Classes c ON c.id_classe = p.classe.id_classe WHERE c.nom_classe = :classe")
    List<Arme> findArmesByClasse( String classe);





}
