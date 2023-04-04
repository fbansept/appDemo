package com.david.demo.controller;

import com.david.demo.dao.UtilisateurDao;
import com.david.demo.model.Utilisateur;
import com.david.demo.security.JwtUtils;
import com.david.demo.view.VueUtilisateur;
import com.fasterxml.jackson.annotation.JsonView;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UtilisateurController {
    @Autowired
    private UtilisateurDao utilisateurDao;

//    public UtilisateurController(UtilisateurDao utilisateurDao) {
//        this.utilisateurDao = utilisateurDao;
//    }

    @Autowired
    JwtUtils jwtUtils;


    @GetMapping("/admin/utilisateurs")
    @JsonView(VueUtilisateur.class)
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurDao.findAll();
    }

    @GetMapping("/admin/utilisateur-{prenom}")
    @JsonView(VueUtilisateur.class)
    public Utilisateur getUtilisateur(@PathVariable String prenom) {
        return utilisateurDao.findByPrenom(prenom);
    }

    @GetMapping("/admin/utilisateur/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable int id){
        Optional<Utilisateur> optional = utilisateurDao.findById(id);
        if(optional.isPresent())return new ResponseEntity<Utilisateur>(optional.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping("/admin/utilisateur")
    public ResponseEntity<Utilisateur> addUtilisateur(@RequestBody Utilisateur... utilisateurs){
        boolean update = true;
        try {
            for (Utilisateur utilisateur : utilisateurs) {
                if (utilisateur.getId() != null) {
                    Optional<Utilisateur> optional = utilisateurDao.findById(utilisateur.getId());
                    if (optional.isPresent()) {
                        utilisateurDao.save(utilisateur);
                    } else {
                        //si tentative d'insertion d'utilisateur avec un id qui n'existe pas
                        throw new Exception("tentative d'insertion d'utilisateur avec un id qui n'existe pas");
                    }
                } else {
                    utilisateurDao.save(utilisateur);
                    update = false;
                }
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (update) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/utilisateur/{id}")
    public ResponseEntity<Utilisateur> deleteUser(@PathVariable int id){
        Optional<Utilisateur> utilisateurAsupprimer = utilisateurDao.findById(id);
        if(utilisateurAsupprimer.isPresent()){
            utilisateurDao.deleteById(id);
            return new ResponseEntity<>(utilisateurAsupprimer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/supprimerUtilisateurs")
    public int deleteUsers(@RequestBody int... ids){
        int nb = 0;
        for (int id: ids) {
            utilisateurDao.deleteById(id);
            nb++;
        }
        return nb;
    }
    @JsonView(VueUtilisateur.class)
    @GetMapping("/profil")
    public ResponseEntity<Utilisateur> getProfil(@RequestHeader("Authorization") String token){
        String jwt = token.substring(7);
        Claims donnees = jwtUtils.getData(jwt);
        return utilisateurDao.findByEmail(donnees.getSubject())
                .map(utilisateur -> new ResponseEntity<>(utilisateur, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
