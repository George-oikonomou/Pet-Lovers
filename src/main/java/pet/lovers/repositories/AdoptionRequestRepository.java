package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.AdoptionRequest;

import java.util.Optional;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Integer> {

    Optional<AdoptionRequest> findByShelter_Id(Integer shelterId);
}
