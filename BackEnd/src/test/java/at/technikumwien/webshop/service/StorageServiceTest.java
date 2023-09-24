package at.technikumwien.webshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import at.technikumwien.webshop.model.File;
import at.technikumwien.webshop.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

// TODO: TestClass
@ExtendWith(SpringExtension.class)
public class StorageServiceTest {

    private StorageService storageService;

    @Mock
    private FileRepository fileRepository;

    @BeforeEach
    void setUp() {
        storageService = new StorageService(fileRepository);
    }

    @Test
    void testStore() throws IOException {
        // Mocks
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("sld-175-counterspell.jpg");
        when(multipartFile.getInputStream()).thenReturn(mock(InputStream.class));

        // Test
        File savedFile = storageService.store(multipartFile);

        // Assertions
        assertNotNull(savedFile);
        assertNotNull(savedFile.getPath());

        // Ermittle den generierten Dateinamen im Test
        String generatedFilename = savedFile.getPath();

        // Erwarte den generierten Dateinamen in der Service-Methode
        String expectedFilename = "frontend/img/Produkte_img/" + generatedFilename;

        assertEquals(expectedFilename, generatedFilename);

        verify(multipartFile, times(1)).getOriginalFilename();
        verify(multipartFile, times(1)).getInputStream();
    }

    @Test
    void testServe() throws IOException {
        // Mocks
        File fileEntity = new File();
        fileEntity.setPath("test.jpg");
        when(fileRepository.findById(any())).thenReturn(Optional.of(fileEntity));

        // Test
        Resource resource = storageService.serve(fileEntity);

        // Assertions
        assertNotNull(resource);
        assertTrue(resource.exists());
        assertTrue(resource.isReadable());
    }

    @Test
    void testDeleteFile() {
        // Mocks
        File fileEntity = new File();
        fileEntity.setPath("test.jpg");
        when(fileRepository.findById(any())).thenReturn(Optional.of(fileEntity));

        // Test
        storageService.deleteFile(1L);

        // Assertions
        verify(fileRepository, times(1)).deleteById(any());
    }

    @Test
    void testUpdateFile() throws IOException {
        // Mocks
        File existingFileEntity = new File();
        existingFileEntity.setPath("old.jpg");
        when(fileRepository.findById(any())).thenReturn(Optional.of(existingFileEntity));

        MultipartFile newFile = mock(MultipartFile.class);
        when(newFile.getOriginalFilename()).thenReturn("new.jpg");
        when(newFile.getInputStream()).thenReturn(mock(InputStream.class));

        // Test
        File updatedFile = storageService.updateFile(newFile, 1L);

        // Assertions
        assertNotNull(updatedFile);
        assertNotNull(updatedFile.getPath());
        verify(fileRepository, times(1)).save(any());
    }
}
