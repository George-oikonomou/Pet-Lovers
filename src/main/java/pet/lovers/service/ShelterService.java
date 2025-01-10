package pet.lovers.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pet.lovers.entities.UserStatus;
import pet.lovers.repositories.ShelterRepository;
import pet.lovers.entities.Shelter;
import java.util.List;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Transactional
    public List<Shelter> getShelters(){
        return shelterRepository.findAll();
    }

    @Transactional
    public List<Shelter> getSheltersByUserStatus(UserStatus status){
        return shelterRepository.findByUserStatus(status);
    }

    @Transactional
    public void approveShelter(Integer shelterId){
        Shelter shelter = shelterRepository.findById(shelterId).orElseThrow();
        shelter.setUserStatus(UserStatus.APPROVED);
        shelterRepository.save(shelter);
    }

    @Transactional
    public void rejectShelter(Integer shelterId){
        Shelter shelter = shelterRepository.findById(shelterId).orElseThrow();
        shelter.setUserStatus(UserStatus.REJECTED);
        shelterRepository.save(shelter);
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
