package pet.lovers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.lovers.entities.Shelter;
import pet.lovers.entities.ShelterRequest;
import pet.lovers.entities.Vet;
import pet.lovers.repositories.ShelterRequestRepository;

import java.util.List;

@Service
public class ShelterRequestService {

    private final ShelterRequestRepository shelterRequestRepository;

    @Autowired
    public ShelterRequestService(ShelterRequestRepository shelterRequestRepository) {
        this.shelterRequestRepository = shelterRequestRepository;
    }

    public void saveShelterRequest(ShelterRequest shelterRequest) {
        shelterRequestRepository.save(shelterRequest);
    }

    public boolean existsByVetAndShelter(Vet vet, Shelter shelter) {
        if (vet == null || shelter == null) {
            return false;
        }
        return !shelterRequestRepository.findByVetAndShelter(vet, shelter).isEmpty();
    }


    public List<ShelterRequest> getShelterRequestsByShelter(Integer id) {
        return shelterRequestRepository.findByShelterId(id);
    }


    public void deleteShelterRequest(ShelterRequest request) {
        shelterRequestRepository.delete(request);
    }
}