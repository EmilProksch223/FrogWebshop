package at.technikumwien.webshop.service;

import at.technikumwien.webshop.model.File;
import at.technikumwien.webshop.repository.FileRepository;
import at.technikumwien.webshop.service.StorageService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class StorageService {

    private final Path storageDirectory;
    private FileRepository fileRepository;

    public StorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        // Set the storage directory
        storageDirectory = Path.of("frontend/img/Produkte_img/");
    }

    public File store(MultipartFile file) throws IOException {
        String filename = generateUniqueFileName(file.getOriginalFilename());

        // Store the file in the storage directory
        Path filePath = storageDirectory.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        File fileEntity = new File();
        fileEntity.setPath(filename);

        return fileEntity;
    }

    public Resource serve(File fileEntity) throws IOException {
        String filename = fileEntity.getPath();
        // Retrieve the file from the storage directory
        Path filePath = storageDirectory.resolve(filename);
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new IOException("File not found or cannot be read: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new IOException("Invalid file path: " + filename, e);
        }
    }

    private String generateUniqueFileName(String originalFilename) {
        // Generate a unique filename
        // append a timestamp to the original filename
        long timestamp = System.currentTimeMillis();
        return timestamp + "_" + originalFilename;
    }

    public void deleteFile(Long fileId) {
        // Zuerst das File-Objekt aus der Datenbank abrufen
        Optional<File> optionalFile = fileRepository.findById(fileId);

        if (optionalFile.isPresent()) {
            File fileEntity = optionalFile.get();
            String filename = fileEntity.getPath();
            Path filePath = storageDirectory.resolve(filename);

            // Zuerst aus der Datenbank löschen
            fileRepository.deleteById(fileId);

            // Dann aus dem Dateisystem löschen
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    public File updateFile(MultipartFile file, Long fileId) throws IOException {
        Optional<File> optionalFile = fileRepository.findById(fileId);

        if (optionalFile.isPresent()) {
            File fileEntity = optionalFile.get();
            String oldFilename = fileEntity.getPath();
            String newFilename = file.getOriginalFilename();
            Path filePath = storageDirectory.resolve(oldFilename);
            System.out.println(filePath);
            

            if (!oldFilename.equals(newFilename)) {
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                File savedFile = store(file);
                fileEntity.setPath(savedFile.getPath());
                fileRepository.save(fileEntity);

                return savedFile;
            } else {

                return null;
            }
        } else {
            throw new FileNotFoundException("File not found with ID: " + fileId);
        }
    }

}
