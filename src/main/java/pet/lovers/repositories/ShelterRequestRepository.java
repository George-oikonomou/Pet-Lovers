package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.lovers.entities.Shelter;
import pet.lovers.entities.ShelterRequest;
import pet.lovers.entities.Vet;

public interface ShelterRequestRepository extends JpaRepository<ShelterRequest, Integer> {
    ShelterRequest save (ShelterRequest shelterRequest);

    boolean existsByVetAndShelter(Vet vet, Shelter shelter);
}