package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
}
