package com.david.demo.controller;

import com.david.demo.dao.PaysDao;
import com.david.demo.model.Pays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PaysController {
    @Autowired
    private PaysDao paysDao;

//    public PaysController(PaysDao paysDao) {
//        this.paysDao = paysDao;
//    }


    @GetMapping("/liste-pays")
    public List<Pays> getListePays() {
        return paysDao.findAll();
    }

    @GetMapping("/pays-{nom}")
    public Pays getPays(@PathVariable String nom) {
        return paysDao.findByNom(nom);
    }

    @GetMapping("/pays/{id}")
    public ResponseEntity<Pays> getPaysById(@PathVariable int id){
        Optional<Pays> optional = paysDao.findById(id);
        if(optional.isPresent())return new ResponseEntity<Pays>(optional.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping("/admin/pays")
    public ResponseEntity<Pays> addPays(@RequestBody Pays... listePays){
        boolean update = true;
        try {
            for (Pays pays : listePays) {
                if (pays.getId() != null) {
                    Optional<Pays> optional = paysDao.findById(pays.getId());
                    if (optional.isPresent()) {
                        paysDao.save(pays);
                    } else {
                        //si tentative d'insertion d'pays avec un id qui n'existe pas
                        throw new Exception("entative d'insertion d'pays avec un id qui n'existe pas");
                    }
                } else {
                    paysDao.save(pays);
                    update = false;
                }
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (update) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/pays/{id}")
    public ResponseEntity<Pays> deleteUser(@PathVariable int id){
        Optional<Pays> paysAsupprimer = paysDao.findById(id);
        if(paysAsupprimer.isPresent()){
            paysDao.deleteById(id);
            return new ResponseEntity<>(paysAsupprimer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/supprimerPays")
    public int deleteUsers(@RequestBody int... ids){
        int nb = 0;
        for (int id: ids) {
            paysDao.deleteById(id);
            nb++;
        }
        return nb;
    }

}
