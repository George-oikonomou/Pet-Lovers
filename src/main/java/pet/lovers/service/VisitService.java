package pet.lovers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.lovers.entities.Visit;
import pet.lovers.repositories.VisitRepository;

import java.util.List;

@Service
public class VisitService {
    @Autowired
    private VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public List<Visit> getVisitsByShelterId(Integer id) {
        return visitRepository.findVisitsByShelterId(id);
    }

    public void save(Visit visit) {
        visitRepository.save(visit);
    }
}