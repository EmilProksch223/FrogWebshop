package at.technikumwien.webshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.technikumwien.webshop.dto.AddressDTO;
import at.technikumwien.webshop.model.Address;
import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.service.AddressService;
import at.technikumwien.webshop.service.UserService;

@RestController
@RequestMapping
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    @Autowired
    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @GetMapping("/addresses")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @PostMapping("/users/{id}/address")
    public ResponseEntity<Address> createAddress(@PathVariable("id") Long id, @RequestBody Address address) {
        Optional<User> userOptional = userService.getUserById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        address.setUser(user);
        Address createdAddress = addressService.createAddress(address);

        return ResponseEntity.ok(createdAddress);
    }

    private Address fromDTO(AddressDTO addressDTO) {
        Address address = new Address();
        address.setFirstName(addressDTO.getFirstName());
        address.setLastName(addressDTO.getLastName());
        address.setGender(addressDTO.getGender());
        address.setStreet(addressDTO.getStreet());
        address.setAddressLine2(addressDTO.getAddressLine2());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        return address;
    }
}