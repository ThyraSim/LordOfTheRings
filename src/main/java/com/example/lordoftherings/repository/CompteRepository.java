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

    /**
     * Recherche un compte avec ses personnages associés en utilisant l'ID du compte.
     *
     * @param compteId ID du compte
     * @return Compte trouvé avec les personnages associés
     */
    @Query("SELECT c FROM Compte c LEFT JOIN FETCH c.personnages WHERE c.id_compte = :compteId")
    Compte findCompteWithPersonnagesById(@Param("compteId") Integer compteId);


    /**
     * Recherche les comptes ayant le statut premium activé.
     *
     * @return La liste des comptes premium.
     */
    List<Compte> findCompteByPremiumIsTrue();

    /**
     * Recherche les comptes par nom d'utilisateur correspondant à un motif donné.
     *
     * @param saisi Le motif à utiliser pour la recherche.
     * @return La liste des comptes correspondants.
     */
    @Query("select c From Compte c WHERE c.nom_utilisateur LIKE %:saisi%")
    public List<Compte> findByNom_utilisateur(String saisi);


}

