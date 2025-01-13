package pet.lovers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.lovers.entities.Adopter;

import java.util.Optional;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, Integer> {
    Optional<Adopter> findById(Integer userId);

}
