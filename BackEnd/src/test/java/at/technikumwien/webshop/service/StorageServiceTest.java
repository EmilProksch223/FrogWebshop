package at.technikumwien.webshop.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import at.technikumwien.webshop.model.File;
import at.technikumwien.webshop.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class StorageServiceTest {

    private StorageService storageService;

    @Mock
    private FileRepository fileRepository;

    @BeforeEach
    public void setUp() {
        storageService = new StorageService(fileRepository);
    }

    @Test
    void shouldDeleteFile() {
        File fileEntity = new File();
        fileEntity.setPath("test.jpg");
        when(fileRepository.findById(any())).thenReturn(Optional.of(fileEntity));

        storageService.deleteFile(1L);

        verify(fileRepository, times(1)).deleteById(any());
    }
}
