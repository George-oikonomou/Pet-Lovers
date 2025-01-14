package pet.lovers.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.lovers.entities.Vet;
import pet.lovers.repositories.VetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VetService {

    private VetRepository vetRepository;

    public Vet registerVet(Vet vet) {
        return vetRepository.save(vet);
    }

    @Transactional
    public List<Vet> getAllVets() {
        return vetRepository.findAll();
    }

    @Transactional
    public Optional<Vet> getVetById(Integer id) {
        return vetRepository.findById(id);
    }

    @Transactional
    public void deleteVet(Integer id) {
        vetRepository.deleteById(id);
    }

    @Autowired
    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    public Vet getLoggedInVet(String username) {
        return vetRepository.findByUsername(username);
    }
}