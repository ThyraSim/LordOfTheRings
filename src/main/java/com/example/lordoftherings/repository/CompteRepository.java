package com.example.lordoftherings.repository;

import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.entity.Personnage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {
    @Query("SELECT c FROM Compte c LEFT JOIN FETCH c.personnages WHERE c.id_compte = :compteId")
    Compte findCompteWithPersonnagesById(@Param("compteId") Integer compteId);
}
