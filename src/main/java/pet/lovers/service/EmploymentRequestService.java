package pet.lovers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.lovers.entities.Shelter;
import pet.lovers.entities.EmploymentRequest;
import pet.lovers.entities.Vet;
import pet.lovers.repositories.EmploymentRequestRepository;
import java.util.Optional;

@Service
public class EmploymentRequestService {

    private final EmploymentRequestRepository employmentRequestRepository;

    @Autowired
    public EmploymentRequestService(EmploymentRequestRepository employmentRequestRepository) {
        this.employmentRequestRepository = employmentRequestRepository;
    }

    public void saveEmploymentRequest(EmploymentRequest employmentRequest) {
        employmentRequestRepository.save(employmentRequest);
    }

    public boolean existsByVetAndShelter(Vet vet, Shelter shelter) {
        return employmentRequestRepository.findByVetAndShelter(vet, shelter).isPresent();
    }

    public Optional<EmploymentRequest> findByVetAndShelter(Vet vet, Shelter shelter) {
        return employmentRequestRepository.findByVetAndShelter(vet, shelter);
    }

    public void deleteEmploymentRequest(EmploymentRequest request) {
        employmentRequestRepository.delete(request);
    }
}