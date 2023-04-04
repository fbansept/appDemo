package com.david.demo.model;

import com.david.demo.view.VueEntreprise;
import com.david.demo.view.VueUtilisateur;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;

import java.util.Set;

@Entity
@Getter
@Setter
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(VueEntreprise.class)
    private Integer id;

    @Column(length = 50, nullable = false)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String nom;

    @OneToMany(mappedBy ="entreprise")
    @JsonView(VueEntreprise.class)
    private Set<Utilisateur> listeUtilisateur = new HashSet<>();
}
