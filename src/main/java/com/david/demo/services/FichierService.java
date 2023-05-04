package com.david.demo.services;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FichierService {

    @Value("${dossier.upload}")
    private String dossierUpload;

    public void transfertVersDossierUpload(MultipartFile fichier, String nomFichier) throws IOException {

        Path cheminDosserUpload = Paths.get(dossierUpload);
        if(!Files.exists(cheminDosserUpload)) {
            Files.createDirectories(cheminDosserUpload);
        }
        Path destination = Paths.get(cheminDosserUpload + "\\" + nomFichier);
        Files.copy(fichier.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("dossierUpload = " + dossierUpload);
        System.out.println("fichier = " + fichier.getOriginalFilename());
        System.out.println("nomFichier = " + nomFichier);
    }

    public byte[] recupererFichier(String nomFichier) throws IOException {

//        Path destination = Paths.get(dossierUpload+"/"+nomFichier);// retrieve the image by its name
//
//        try {
//            return IOUtils.toByteArray(destination.toUri());
//        } catch (IOException e) {
//            throw new FileNotFoundException(e.getMessage());
//        }
        return Files.readAllBytes(Paths.get(dossierUpload + "/" + nomFichier));
    }
}
