package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.Adopter;
import pet.lovers.entities.AdoptionRequest;
import pet.lovers.entities.User;

import java.util.Optional;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, Integer> {
    Optional<Adopter> findByAdoptionRequest(AdoptionRequest adoptionRequest); //may be more useful in User repository
}
