package com.david.demo.security;

import com.david.demo.model.Utilisateur;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private Utilisateur utilisateur;

    public MyUserDetails(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(utilisateur.getRole().getNom()));
    }
//        List<GrantedAuthority> roles =new ArrayList<>();
//        if(utilisateur.isAdmin()){
//            roles.add(new SimpleGrantedAuthority("ADMINISTRATEUR"));
//        }else{
//            roles.add(new SimpleGrantedAuthority("UTILISATEUR"));
//        }
//        return roles;
//        return List.of(new SimpleGrantedAuthority(utilisateur.isAdmin() ? "ROLE_ADMINISTRATEUR" : "ROLE_UTILISATEUR"));

    @Override
    public String getPassword() {
        return utilisateur.getMotDePasse();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
}
