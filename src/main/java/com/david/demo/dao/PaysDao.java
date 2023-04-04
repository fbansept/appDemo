package com.david.demo.dao;

import com.david.demo.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaysDao extends JpaRepository<Pays, Integer> {
    Pays findByNom(String nom);
}
