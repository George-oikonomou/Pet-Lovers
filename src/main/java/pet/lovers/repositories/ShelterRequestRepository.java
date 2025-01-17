package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.Shelter;
import pet.lovers.entities.ShelterRequest;
import pet.lovers.entities.Vet;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelterRequestRepository extends JpaRepository<ShelterRequest, Integer> {
    List<ShelterRequest> findByVetAndShelter(Vet vet, Shelter shelter);

    List<ShelterRequest> findByShelterId(Integer id);
}