package at.technikumwien.webshop.controller;

import at.technikumwien.webshop.model.File;
import at.technikumwien.webshop.repository.FileRepository;

import at.technikumwien.webshop.service.StorageService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class FileHandlingController {

    private final StorageService storageService;
    private final FileRepository fileRepository;

    @Autowired
    public FileHandlingController(StorageService storageService, FileRepository fileRepository) {
        this.storageService = storageService;
        this.fileRepository = fileRepository;
    }

    @PostMapping()
    public String handleFileUpload(@RequestParam("file")MultipartFile file) throws IOException {

        File fileEntity = storageService.store(file);
        fileRepository.save(fileEntity);

        return fileEntity.getId().toString();
    }

    @GetMapping()
    public List<File> getAllFiles() {
        return storageService.getAllFiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getFilePath(@PathVariable Long id) {
        Optional<File> fileEntity = fileRepository.findById(id);
        if (fileEntity.isPresent()) {
            String filePath = fileEntity.get().getPath(); // Hier den Dateipfad entsprechend anpassen
            return ResponseEntity.ok(filePath);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}