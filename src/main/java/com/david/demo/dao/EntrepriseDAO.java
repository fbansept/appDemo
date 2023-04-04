package com.david.demo.dao;

import com.david.demo.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EntrepriseDAO extends JpaRepository<Entreprise, Integer> {
    Entreprise findByNom(String nom);

}
