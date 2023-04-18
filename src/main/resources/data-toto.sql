INSERT INTO pays(nom) VALUES ("France"),("Belgique"),("Suisse"),("Luxembourg"),("Italie");
INSERT INTO entreprise(nom) VALUES ("Google"),("Microsoft"),("Apple"),("Oracle"),("IBM");
INSERT into role(nom) VALUES ("ROLE_ADMINISTRATEUR"),("ROLE_UTILISATEUR");

INSERT INTO utilisateur(prenom, nom, pays_id,entreprise_id, email, mot_de_passe, role_id) VALUES
         ("John", "Doe",1,2, "a@a.fr", "$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",1),
         ("Jane", "Doe",2,3, "b@b.fr","$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",2),
         ("Jane", "Doe",2,3, "c@c.fr","$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",2),
         ("Jack", "Doe",3,4,"d@d.fr","$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",1),
         ("Steeve","Smith",4,5,"e@e.fr","$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",2),
         ("Arre","Truc",4,5,"r@r.fr","$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",2),
         ("Jane", "Doe",2,3, "jane.doe@yahoo.com", "$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",2),
         ("Bob", "Smith",3,4, "bob.smith@hotmail.com", "$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",1),
         ("Alice", "Johnson",4,3, "alice.johnson@outlook.com", "$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",2),
         ("Sam", "Wilson",5,1, "sam.wilson@gmail.com", "$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",1),
         ("Emily", "Davis",1,2, "emily.davis@yahoo.com", "$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",2),
         ("Max", "Brown",2,3, "max.brown@hotmail.com", "$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",1),
         ("Ella", "Taylor",3,1, "ella.taylor@gmail.com", "$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",2),
         ("David", "Clark",4,2, "david.clark@outlook.com", "$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",1),
         ("Sophia", "Garcia",2,3, "sophia.garcia@yahoo.com", "$2y$10$ODv3tF1YNzlMGHueQE4UMO4jRZyMycigfA5SRRuv0VUKzkEK9WyY.",2);





INSERT INTO emploi(nom) VALUES ("Developpeur"),("Architecte"),("Chef de projet"),("Chef de produit"),("Designer");
INSERT INTO recherche_emploi_utilisateur(utilisateur_id, emploi_id) VALUES (1,2),(1,3),(2,1),(2,3),(3,1),(3,2),(4,1),(4,2),(4,3);

