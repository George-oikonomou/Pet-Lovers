package pet.lovers.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pet.lovers.repositories.ShelterRepository;
import pet.lovers.entities.Shelter;
import java.util.List;

@Service
public class ShelterService {
    ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Transactional
    public List<Shelter> getShelters(){
        return shelterRepository.findAll();
    }

    @Transactional
    public void saveShelter(Shelter shelter){
        shelterRepository.save(shelter);
    }

    @Transactional
    public void deleteShelter(Integer shelterId) {
        shelterRepository.deleteById(shelterId);
    }
}
