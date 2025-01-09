package pet.lovers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.lovers.entities.Vet;
import pet.lovers.repositories.VetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VetService {

    @Autowired
    private VetRepository vetRepository;

    public Vet registerVet(Vet vet) {
        return vetRepository.save(vet);
    }

    public List<Vet> getAllVets() {
        return vetRepository.findAll();
    }

    public Optional<Vet> getVetById(Integer id) {
        return vetRepository.findById(id);
    }

    public void deleteVet(Integer id) {
        vetRepository.deleteById(id);
    }
}
