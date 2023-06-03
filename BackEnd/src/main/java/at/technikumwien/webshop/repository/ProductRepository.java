package at.technikumwien.webshop.repository;

import java.util.List;

import at.technikumwien.webshop.model.Product;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link JpaRepository} für {@link Product Produkte}.
 * Der erste Generic, hier {@link Product},
 * gibt die Entität an, die das Repository verwaltet.
 * Der zweite Generic, hier {@link Long},
 * gibt den Datentyp des Primary Keys der Entität an.
 * Das ist die Variable, welche wir mit {@link Id} annotiert haben.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByManaType(String manaType);
}
