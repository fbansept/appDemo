package com.david.demo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.util.Arrays;

@EnableWebSecurity
public class ConfigSecu extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyUDS myUDS;
    @Autowired
    JwFilter jwFilter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password("u_").roles("UTILISATEUR")
//                .and()
//                .withUser("admin")
//                .password("a&")
//                .roles("ADMINISTRATEUR");
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT email, mot_de_passe, 1 FROM utilisateur WHERE email=?")
//                .authoritiesByUsernameQuery("SELECT email, IF(is_admin, 'ADMINISTRATEUR','UTILISATEUR') FROM utilisateur WHERE email=?")
////                .rolePrefix("ROLE_")
//                .passwordEncoder(new BCryptPasswordEncoder())
//        ;
        auth.userDetailsService(myUDS);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(httpServletRequest -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.applyPermitDefaultValues();
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
            corsConfiguration.setAllowedHeaders(
                    Arrays.asList("X-Requested-With", "Origin", "Content-Type",
                            "Accept", "Authorization","Access-Control-Allow-Origin"));
            return corsConfiguration;
            })
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/inscription", "/connexion", "/utilisateurs","/utilisateur/**", "/utilisateur").permitAll()
                .antMatchers("/admin/**").hasRole("ADMINISTRATEUR")
                .antMatchers("/**").hasAnyRole("UTILISATEUR", "ADMINISTRATEUR")
                //
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ;

        http.addFilterBefore(jwFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder encoding(){
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
