package com.david.demo.controller;

import com.david.demo.dao.UtilisateurDao;
import com.david.demo.model.Utilisateur;

import com.david.demo.security.JwtUtils;
import com.david.demo.security.MyUserDetails;
import com.david.demo.view.VueUtilisateur;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
public class ConnexionController {
    @Autowired
    AuthenticationManager authentificationManager;

    @Autowired
    UtilisateurDao utilisateurDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/connexion")
    @CrossOrigin
    public ResponseEntity<String> connexion(@RequestBody Utilisateur utilisateur){
        try {
            authentificationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(utilisateur.getEmail(), utilisateur.getMotDePasse())
            );
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(jwtUtils.generateJwt(new MyUserDetails(utilisateur)), HttpStatus.OK);
    }

    @PostMapping("/inscription")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> inscription(@RequestBody Utilisateur utilisateur){
        if(utilisateur.getId() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (utilisateur.getMotDePasse().length()<=3){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (utilisateur.getEmail() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(utilisateur.getEmail()).matches()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Utilisateur> optional = utilisateurDao.findByEmail(utilisateur.getEmail());
        if (optional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String hash = passwordEncoder.encode(utilisateur.getMotDePasse());
        utilisateur.setMotDePasse(hash);
        utilisateurDao.save(utilisateur);
        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }

    
}
