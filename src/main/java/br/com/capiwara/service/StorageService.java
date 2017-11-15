package br.com.capiwara.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    private final Path roPath = Paths.get("/var/cardoso/");

    public void upload(MultipartFile file) {
        try {
            System.out.println(roPath.getRoot());
            Files.copy(file.getInputStream(), this.roPath.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL");
        }
    }

}
