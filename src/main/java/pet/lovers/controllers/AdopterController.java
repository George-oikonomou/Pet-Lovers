package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pet.lovers.entities.*;
import pet.lovers.service.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/adopter")
public class AdopterController {

    private PetService petService;
    private AdoptionRequestService adoptionRequestService;
    private AdopterService adopterService;

    private VisitService visitService;



    public AdopterController(VisitService visitService, PetService petService, AdoptionRequestService adoptionRequestService, AdopterService adopterService) {
        this.visitService = visitService;
        this.petService = petService;
        this.adoptionRequestService = adoptionRequestService;
        this.adopterService = adopterService;
    }

    @GetMapping("/pets")
    public String listAvailablePets(Model model) {
        List<Pet> pets = petService.getPetsByPetStatus(PetStatus.AVAILABLE);
        model.addAttribute("pets", pets);
        return "adopter/pets";
    }

    @GetMapping("/pets/{id}")
    public String viewPetDetails(@PathVariable Integer id, Model model) {
        Pet pet = petService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));
        model.addAttribute("pet", pet);
        return "adopter/pet-details";
    }

    @PreAuthorize("hasRole('ROLE_ADOPTER')")
    @GetMapping("/pets/{id}/request-adoption")
    public String newAdoptionRequest(@PathVariable Integer id, Model model) {
        Pet pet = petService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        Adopter adopter = adopterService.getCurrentUser();

        Visit visit = new Visit(LocalDateTime.now(), pet.getShelter(), adopter, pet);

        model.addAttribute("adoptionRequest",visit);
        return "adopter/new-adoption-request";
    }

    @PreAuthorize("hasRole('ROLE_ADOPTER')")
    @PostMapping("/pets/{id}/request-adoption")
    public String saveAdoptionRequest(@PathVariable Integer id, @ModelAttribute Visit visit, Model model) {

        Adopter adopter = adopterService.getCurrentUser();

        Pet pet = petService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        AdoptionRequest adoptionRequest = new AdoptionRequest(visit.getDateTime(), visit.getShelter(), adopter, visit.getPet());

        // Set necessary fields for the adoption request
        adoptionRequest.setPet(pet);
        adoptionRequest.setAdopter(adopter);
        adoptionRequest.setDateTime(adoptionRequest.getDateTime());
        Visit visitRequest = new Visit(visit.getDateTime(), pet.getShelter(), adopter, pet);
        visitService.save(visitRequest);

        // Save the adoption request
        adoptionRequestService.save(adoptionRequest);

        // Add success message to the model
        String message = "Adoption request for '" + pet.getName() + "' submitted successfully!";
        model.addAttribute("msg", message);

        return "redirect:/adopter";
    }
}

