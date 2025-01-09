package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
}
