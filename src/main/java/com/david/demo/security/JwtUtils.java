package com.david.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



@Service
public class JwtUtils {
    public String generateJwt(UserDetails userDetails) {
        return Jwts.builder()
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
