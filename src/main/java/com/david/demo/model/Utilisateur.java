package com.david.demo.model;

import com.david.demo.view.VueEntreprise;
import com.david.demo.view.VueUtilisateur;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(VueUtilisateur.class)
    private Integer id;


    @Column(length = 50)
    @JsonView({VueUtilisateur.class , VueEntreprise.class})
    private String prenom;

    @Column(length = 50, nullable = false)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String nom;

    @ManyToOne
    @JsonView(VueUtilisateur.class)
    private Pays pays;

    @ManyToOne
    @JsonView({VueUtilisateur.class})
    private Role role;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String email;

    private String motDePasse;



    @ManyToMany
    @JoinTable(
            name = "recherche_emploi_utilisateur",
            inverseJoinColumns = @JoinColumn(name = "emploi_id")
    )
    @JsonView(VueUtilisateur.class)
    private Set<Emploi> emplois = new HashSet<>();

    @ManyToOne
    @JsonView(VueUtilisateur.class)
    private Entreprise entreprise;
}
