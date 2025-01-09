package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.Shelter;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Integer> {
}
