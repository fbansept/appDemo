package com.david.demo.dao;

import com.david.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByPrenom(String prenom);

    Optional<Utilisateur> findByEmail(String email);
}
