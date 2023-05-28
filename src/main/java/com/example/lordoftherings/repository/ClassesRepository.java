package com.example.lordoftherings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.lordoftherings.entity.Classes;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Integer> {

}
