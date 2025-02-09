package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT p FROM Pet p " +
            "WHERE p.petStatus IN :statuses " +
            "AND p.userStatus = 'APPROVED' " +
            "AND (p.healthStatus = 'HEALTHY' OR p.healthStatus = 'RECOVERING') " +
            "AND p.shelter.userStatus = 'APPROVED'")
    List<Pet> findActivePetsByStatus(List<PetStatus> statuses);

}
