package com.example.lordoftherings.repository;

import com.example.lordoftherings.entity.Arme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmeRepository extends JpaRepository<Arme, Integer> {

}
