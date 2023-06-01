package com.example.lordoftherings.repository;

import com.example.lordoftherings.entity.Personnage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface PersonnageRepository extends JpaRepository<Personnage, Integer> {

    @Query("SELECT p FROM Personnage  p JOIN Classes  c ON (p.id_classe = c.id_classe) WHERE LOWER(c.nom_classe) = LOWER(:classe)")
    List<Personnage> recherchePersonnageByClasse( @PathVariable String classe);

}
