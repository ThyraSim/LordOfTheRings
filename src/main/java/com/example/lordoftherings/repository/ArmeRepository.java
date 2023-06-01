package com.example.lordoftherings.repository;

import com.example.lordoftherings.entity.Arme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmeRepository extends JpaRepository<Arme, Integer> {

    @Query("SELECT a FROM Arme a WHERE LOWER(a.type_stat) = LOWER( :typeStat)")
  List<Arme> RecherchebyTypeStat(@Param("typeStat") String typeStat);


}
