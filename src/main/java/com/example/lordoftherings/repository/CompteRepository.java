package com.example.lordoftherings.repository;

import com.example.lordoftherings.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {

    List<Compte> findCompteByPremiumIsTrue();

    @Query("select c From Compte c WHERE c.nom_utilisateur LIKE %:saisi%")
    public List<Compte> findByNom_utilisateur (@PathVariable String saisi);


}
