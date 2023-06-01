package com.example.lordoftherings.repository;

import com.example.lordoftherings.entity.Personnage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnageRepository extends JpaRepository<Personnage, Integer> {
    @Query("SELECT p FROM Personnage p JOIN FETCH p.arme a JOIN FETCH p.classe c JOIN FETCH p.compte u WHERE p.id_personnage = :id")
    Personnage findByIdWithArmeAndClassesAndCompte(@Param("id") Integer id);
}
