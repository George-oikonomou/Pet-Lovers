package pet.lovers.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pet.lovers.repositories.AdopterRepository;

import java.util.List;
import java.util.Optional;

import pet.lovers.entities.Adopter;

@Service
public class AdopterService {
    AdopterRepository adopterRepository;


    public AdopterService(AdopterRepository adopterRepository) {
        this.adopterRepository = adopterRepository;
    }

    public Optional<Adopter> findByUserId(Integer id) {
        return adopterRepository.findById(id);
    }

    @Transactional
    public List<Adopter> getAdopters() {
        return adopterRepository.findAll();
    }

    @Transactional
    public void saveAdopter(Adopter adopter) {
        adopterRepository.save(adopter);
    }

    @Transactional
    public void deleteAdopter(Integer id) {
        adopterRepository.deleteById(id);
    }

}
