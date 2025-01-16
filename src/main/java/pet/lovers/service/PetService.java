package pet.lovers.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pet.lovers.entities.*;
import pet.lovers.repositories.PetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional
    public List<Pet> getPets(){
        return petRepository.findAll();
    }

    @Transactional
    public List<Pet> getPetsByUserStatus(UserStatus status){
        return petRepository.findByUserStatus(status);
    }

    @Transactional
    public void approvePet(Integer petId){
        Pet pet = petRepository.findById(petId).orElseThrow();
        pet.setUserStatus(UserStatus.APPROVED);
        petRepository.save(pet);
    }

    public List<Pet> findByShelter(Shelter shelter) {
        return petRepository.findByShelter(shelter);
    }


    public Optional<Pet> findById(Integer id) {
        return petRepository.findById(id);
    }

    @Transactional
    public void rejectPet(Integer petId){
        Pet pet = petRepository.findById(petId).orElseThrow();
        pet.setUserStatus(UserStatus.REJECTED);
        petRepository.save(pet);
    }

    @Transactional
    public Integer savePet(Pet pet){
        petRepository.save(pet);
        return pet.getId();
    }

    @Transactional
    public void deletePet(Integer petId) {
        petRepository.deleteById(petId);
    }

    public void updateHealthStatus(int petId, String healthStatus) {
        Pet pet = petRepository.findById(petId).orElseThrow();
        pet.setHealthStatus(HealthStatus.valueOf(healthStatus));
        petRepository.save(pet);
    }

    @Transactional
    public List<Pet> getPetsByPetStatus(List<PetStatus> statuses) {
        return petRepository.findByPetStatusIn(statuses);
    }

    public boolean existsByName(String name) {
        return petRepository.existsByName(name);
    }

    @Transactional
    public void updatePetStatus(Pet pet, PetStatus petStatus) {
        pet.setPetStatus(petStatus);
        petRepository.save(pet);
    }
}
