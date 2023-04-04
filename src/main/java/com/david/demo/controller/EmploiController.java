package com.david.demo.controller;

import com.david.demo.dao.EmploiDao;
import com.david.demo.model.Emploi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class EmploiController {
    @Autowired
    private EmploiDao emploiDao;

//    public EmploiController(EmploiDao emploiDao) {
//        this.emploiDao = emploiDao;
//    }


    @GetMapping("/liste-emploi")
    public List<Emploi> getListeEmploi() {
        return emploiDao.findAll();
    }

    @GetMapping("/emploi-{nom}")
    public Emploi getEmploi(@PathVariable String nom) {
        return emploiDao.findByNom(nom);
    }

    @GetMapping("/emploi/{id}")
    public ResponseEntity<Emploi> getEmploiById(@PathVariable int id){
        Optional<Emploi> optional = emploiDao.findById(id);
        if(optional.isPresent())return new ResponseEntity<Emploi>(optional.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping("/admin/emploi")
    public ResponseEntity<Emploi> addEmploi(@RequestBody Emploi... listeEmploi){
        boolean update = true;
        try {
            for (Emploi emploi : listeEmploi) {
                if (emploi.getId() != null) {
                    Optional<Emploi> optional = emploiDao.findById(emploi.getId());
                    if (optional.isPresent()) {
                        emploiDao.save(emploi);
                    } else {
                        //si tentative d'insertion d'emploi avec un id qui n'existe pas
                        throw new Exception("entative d'insertion d'emploi avec un id qui n'existe pas");
                    }
                } else {
                    emploiDao.save(emploi);
                    update = false;
                }
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (update) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/emploi/{id}")
    public ResponseEntity<Emploi> deleteUser(@PathVariable int id){
        Optional<Emploi> emploiAsupprimer = emploiDao.findById(id);
        if(emploiAsupprimer.isPresent()){
            emploiDao.deleteById(id);
            return new ResponseEntity<>(emploiAsupprimer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/supprimerEmploi")
    public int deleteUsers(@RequestBody int... ids){
        int nb = 0;
        for (int id: ids) {
            emploiDao.deleteById(id);
            nb++;
        }
        return nb;
    }

}
