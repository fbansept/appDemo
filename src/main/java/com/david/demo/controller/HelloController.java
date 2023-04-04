package com.david.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
        public String hello() {
            return "<h1>Le serveur marche mais il n'y a rien ici</h1>";
        }
}
