package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.Pet;
import pet.lovers.entities.PetStatus;
import pet.lovers.entities.Shelter;
import pet.lovers.entities.UserStatus;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByUserStatus(UserStatus status);
    Boolean existsByName(String name);
    List<Pet> findByShelter(Shelter shelter);
    List<Pet> findByPetStatusIn(List<PetStatus> statuses);
}
