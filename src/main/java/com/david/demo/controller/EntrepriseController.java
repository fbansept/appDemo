package com.david.demo.controller;

import com.david.demo.dao.EntrepriseDAO;
import com.david.demo.model.Entreprise;
import com.david.demo.view.VueEntreprise;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EntrepriseController {

    @Autowired
    private EntrepriseDAO entrepriseDAO;

    @GetMapping("/entreprise/liste")
    @JsonView(VueEntreprise.class)
    public List<Entreprise> getListeEntreprise() {
        return entrepriseDAO.findAll();
    }

    @GetMapping("/entreprise/{id}")
    @JsonView(VueEntreprise.class)
    public Entreprise getEntreprise(@PathVariable int id) {
        return entrepriseDAO.findById(id).orElse(null);
    }

    @DeleteMapping("/admin/entreprise/{id}")
    public ResponseEntity<Entreprise> deleteEntreprise(@PathVariable int id) {
        Optional<Entreprise> optional = entrepriseDAO.findById(id);
        if (optional.isPresent()) {
            entrepriseDAO.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/entreprise")
    @JsonView(VueEntreprise.class)
    public ResponseEntity<Entreprise> addEntreprise(@RequestBody Entreprise... entreprises) {
        boolean update = true;
            for (Entreprise entreprise : entreprises) {
                if (entreprise.getId() != null) {
                    Optional<Entreprise> optional = entrepriseDAO.findById(entreprise.getId());
                    if (optional.isPresent()) {
                        entrepriseDAO.save(entreprise);
                    } else {
                        //si tentative d'insertion d'entreprise avec un id qui n'existe pas
                        System.out.println("tentative d'insertion d'entreprise avec un id qui n'existe pas");
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                }else{
                    entrepriseDAO.save(entreprise);
                    update = false;
                }
            }
        if (update) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
