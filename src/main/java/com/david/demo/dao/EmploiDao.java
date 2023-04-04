package com.david.demo.dao;

import com.david.demo.model.Emploi;
import com.david.demo.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploiDao extends JpaRepository<Emploi, Integer> {
    Emploi findByNom(String nom);
}
