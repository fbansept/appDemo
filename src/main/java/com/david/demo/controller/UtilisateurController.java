package com.david.demo.controller;

import com.david.demo.dao.UtilisateurDao;
import com.david.demo.model.Role;
import com.david.demo.model.Utilisateur;
import com.david.demo.security.JwtUtils;
import com.david.demo.services.FichierService;
import com.david.demo.view.VueUtilisateur;
import com.fasterxml.jackson.annotation.JsonView;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    FichierService fichierService;


    @GetMapping("/utilisateurs")
    @JsonView(VueUtilisateur.class)
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurDao.findAll();
    }

    @GetMapping("/admin/utilisateur-{prenom}")
    @JsonView(VueUtilisateur.class)
    public Utilisateur getUtilisateur(@PathVariable String prenom) {
        return utilisateurDao.findByPrenom(prenom);
    }

    @GetMapping("/utilisateur/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable int id){
        Optional<Utilisateur> optional = utilisateurDao.findById(id);
        if(optional.isPresent())return new ResponseEntity<Utilisateur>(optional.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @Transactional
//    @PostMapping("admin/utilisateur")
//    public ResponseEntity<Utilisateur> addUtilisateur(@RequestBody Utilisateur... utilisateurs){
//        //!!!Attention de bien envoyé un tableau d'objets!!!
//        boolean update = true;
//        try {
//            for (Utilisateur utilisateur : utilisateurs) {
//                if (utilisateur.getId() != null) {
//                    Optional<Utilisateur> optional = utilisateurDao.findById(utilisateur.getId());
//                    if (optional.isPresent()) {
//                        Utilisateur utilisateurAModifier = optional.get();
//                        utilisateurAModifier.setPrenom(utilisateur.getPrenom());
//                        utilisateurAModifier.setNom(utilisateur.getNom());
//                        utilisateurAModifier.setEmail(utilisateur.getEmail());
//                        if (utilisateur.getPays().getId() != null)  utilisateurAModifier.setPays(utilisateur.getPays());
//                        utilisateurDao.save(utilisateurAModifier);
//                    } else {
//                        //si tentative d'insertion d'utilisateur avec un id qui n'existe pas
//                        throw new Exception("tentative d'insertion d'utilisateur avec un id qui n'existe pas");
//                    }
//                } else {
//                    String hash = passwordEncoder.encode("1234");
//                    utilisateur.setMotDePasse(hash);
//                    Role role = new Role();
//                    role.setId(2);
//                    utilisateur.setRole(role);
//                    utilisateurDao.save(utilisateur);
//                    update = false;
//                }
//            }
//        }catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        if (update) return new ResponseEntity<>(HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    @Transactional
    @PostMapping("admin/utilisateur")
    public ResponseEntity<Utilisateur> addUtilisateur(
            @RequestPart("utilisateur") Utilisateur utilisateur,
            @Nullable @RequestParam("image") MultipartFile image
            ){
        boolean update = true;
        try {
                if (utilisateur.getId() != null) {
                    Optional<Utilisateur> optional = utilisateurDao.findById(utilisateur.getId());
                    if (optional.isPresent()) {
                        Utilisateur utilisateurAModifier = optional.get();
                        utilisateurAModifier.setPrenom(utilisateur.getPrenom());
                        utilisateurAModifier.setNom(utilisateur.getNom());
                        utilisateurAModifier.setEmail(utilisateur.getEmail());
                        if (utilisateur.getPays().getId() != null)  utilisateurAModifier.setPays(utilisateur.getPays());
                        if (image != null){
                            try{
                                String nomImage = UUID.randomUUID()+"_"+ image.getOriginalFilename();
                                fichierService.transfertVersDossierUpload(image, nomImage);
                                utilisateurAModifier.setNomImageprofil(nomImage);
                            }catch (IOException e){
                                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                        }
                        utilisateurDao.save(utilisateurAModifier);
                    } else {
                        //si tentative d'insertion d'utilisateur avec un id qui n'existe pas
                        throw new Exception("tentative d'insertion d'utilisateur avec un id qui n'existe pas");
                    }
                } else {
                    String hash = passwordEncoder.encode("1234");
                    utilisateur.setMotDePasse(hash);
                    Role role = new Role();
                    role.setId(2);
                    utilisateur.setRole(role);
                    if (image != null){
                        try{
                            String nomImage = UUID.randomUUID()+"_"+ image.getOriginalFilename();
                            fichierService.transfertVersDossierUpload(image, utilisateur.getNom()+"."+utilisateur.getPrenom()+".jpg");
                            utilisateur.setNomImageprofil(nomImage);
                        }catch (IOException e){
                            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                    utilisateurDao.save(utilisateur);
                    update = false;
                }

        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (update) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/utilisateur/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> deleteUser(@PathVariable int id){
        Optional<Utilisateur> utilisateurAsupprimer = utilisateurDao.findById(id);
        if(utilisateurAsupprimer.isPresent()){
            utilisateurDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
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

    @GetMapping("/image-profil/{id}")
    public ResponseEntity<byte[]> getImageProfil(@PathVariable int id){
        Optional<Utilisateur> utilisateur = utilisateurDao.findById(id);
        if (utilisateur.isPresent()){
            String nomImage = utilisateur.get().getNomImageprofil();
            if (nomImage != null){
                try{
//                    byte[] image = fichierService.recupererFichier(nomImage);
//                    HttpHeaders enTete = new HttpHeaders();
//                    String mimeType = Files.probeContentType(new File(nomImage).toPath());
//                    enTete.setContentType(MediaType.valueOf(mimeType));
//                    return new ResponseEntity<>(image, enTete, HttpStatus.OK);

                    //ou


                    return ResponseEntity.ok()
                            .contentType(MediaType.valueOf( Files.probeContentType(new File(nomImage).toPath()) )) // définie le mime type
                            .body(fichierService.recupererFichier(nomImage));
                }catch (IOException e){
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
                }catch (Exception e){
                    System.out.println("erreur mime type");
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/utilisateur-par-pays/{pays}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<List<Utilisateur>> getUtilisateurAllemand(@PathVariable String pays){
        return new ResponseEntity<>(utilisateurDao.trouverUtilisateurParPays(pays), HttpStatus.OK);
    }
}
