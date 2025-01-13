package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.AdoptionRequest;
import pet.lovers.entities.Shelter;

import java.util.List;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Integer> {
    List<AdoptionRequest> findByShelter(Shelter shelter);

    List<AdoptionRequest> findByAdopterId(Integer adopterId);

}
