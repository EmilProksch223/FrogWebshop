package at.technikumwien.webshop.controller;

import at.technikumwien.webshop.dto.PositionDTO;
import at.technikumwien.webshop.model.Position;
import at.technikumwien.webshop.service.PositionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;

    /////
    // Init
    /////
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    /////
    // Methods
    /////

    @PostMapping
    @ResponseStatus(code = CREATED)
    public Position createPosition(@RequestBody @Valid PositionDTO positionDTO, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return positionService.save(fromDTO(positionDTO), positionDTO.getProductId(), token);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        positionService.deletePosition(id, token);
        return ResponseEntity.noContent().build();
    }

    
    private static Position fromDTO(PositionDTO positionDTO) {
        return new Position(positionDTO.getId(),
                positionDTO.getQuantity());
    }


}
