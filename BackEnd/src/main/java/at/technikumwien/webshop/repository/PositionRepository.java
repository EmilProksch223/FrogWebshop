package at.technikumwien.webshop.repository;

import at.technikumwien.webshop.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    // noop
}
