package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.AdoptionRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Integer> {
    List<AdoptionRequest> findByShelterId(Integer adopterId);

    List<AdoptionRequest> findByAdopterId(Integer adopterId);

    Optional<AdoptionRequest> findByPetId(Integer petId);

}
