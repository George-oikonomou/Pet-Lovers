package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.Shelter;
import pet.lovers.entities.EmploymentRequest;
import pet.lovers.entities.Vet;

import java.util.List;

@Repository
public interface EmploymentRequestRepository extends JpaRepository<EmploymentRequest, Integer> {
    List<EmploymentRequest> findByVetAndShelter(Vet vet, Shelter shelter);
}