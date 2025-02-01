package pet.lovers.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pet.lovers.entities.Pet;
import pet.lovers.entities.UserStatus;
import pet.lovers.entities.Vet;
import pet.lovers.repositories.ShelterRepository;
import pet.lovers.entities.Shelter;
import pet.lovers.repositories.VetRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Transactional
    public List<Shelter> getShelters(){
        return shelterRepository.findByUserStatus(UserStatus.APPROVED);
    }

    public List<Shelter> getAllShelters(){
        return shelterRepository.findAll();
    }

    @Transactional
    public List<Shelter> getSheltersByUserStatus(UserStatus status){
        return shelterRepository.findByUserStatus(status);
    }

    public Optional<Shelter> findByUserId(Integer id) {
        return shelterRepository.findById(id);
    }

    public Optional<Shelter> findActiveByUserId(Integer id) {
        return shelterRepository.findById(id).filter(this::isActiveShelter);
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

    public List<Shelter> findByVet(Vet vet) {
        return shelterRepository.findByVet(vet);
    }

    public boolean isActiveShelter(Shelter shelter) {
        return shelter.getUserStatus() == UserStatus.APPROVED;
    }

    public void updateShelter(Shelter shelter) {
        shelterRepository.save(shelter);
    }
}