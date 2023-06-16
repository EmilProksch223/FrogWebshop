package at.technikumwien.webshop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import at.technikumwien.webshop.model.Address;
import at.technikumwien.webshop.repository.AddressRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
}