package com.david.demo.model;

import com.david.demo.view.VueUtilisateur;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Pays {
    @Id
    @JsonView(VueUtilisateur.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(length = 50, nullable = false)
    @JsonView(VueUtilisateur.class)
    private String nom;

}
