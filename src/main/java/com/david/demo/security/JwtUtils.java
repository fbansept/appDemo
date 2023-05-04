package com.david.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class JwtUtils {
    public String generateJwt(MyUserDetails userDetails) {

        Map<String, Object> donnees = new HashMap<>();
        donnees.put("prenom", userDetails.getUtilisateur().getPrenom());
        donnees.put("nom", userDetails.getUtilisateur().getNom());
        donnees.put("email", userDetails.getUtilisateur().getEmail());
        donnees.put("role", userDetails.getUtilisateur().getRole().getNom());


        return Jwts.builder()
                    .setClaims(donnees)
                    .setSubject(userDetails.getUsername())
                    .signWith(SignatureAlgorithm.HS256, "i'm a secret")
                    .compact();
        }

        public Claims getData(String jwt){
            return Jwts.parser()
                    .setSigningKey("i'm a secret")
                    .parseClaimsJws(jwt)
                    .getBody();
        }

        public boolean isTokenValid(String jwt){
            try {
                Claims donnees = getData(jwt);
            }catch (SignatureException e){
                return false;
            }
            return true;
        }
}
