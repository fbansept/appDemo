package com.david.demo.security;

import com.david.demo.dao.UtilisateurDao;
import com.david.demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUDS implements UserDetailsService {
    @Autowired
    private UtilisateurDao utilisateurDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(username);

        if(utilisateur.isEmpty()){
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }


        return new MyUserDetails(utilisateur.get());
    }
}
