package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pet.lovers.entities.*;
import pet.lovers.service.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/adopter")
public class AdopterController {

    PetService petService;
    AdoptionRequestService adoptionRequestService;
    AdopterService adopterService;
    VisitService visitService;
    ShelterService shelterService;

    public AdopterController(VisitService visitService, PetService petService, AdoptionRequestService adoptionRequestService, AdopterService adopterService,ShelterService shelterService) {
        this.visitService = visitService;
        this.petService = petService;
        this.adoptionRequestService = adoptionRequestService;
        this.adopterService = adopterService;
        this.shelterService = shelterService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "index";
    }

    @GetMapping("/pets")
    public String listAvailablePets(Model model) {
        List<PetStatus> criteria = Arrays.asList(PetStatus.AVAILABLE, PetStatus.PENDING_ADOPTION);
        List<Pet> pets = petService.getPetsByPetStatus(criteria);
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
    public String saveAdoptionRequest(@PathVariable int id, @ModelAttribute("adoptionRequest") Visit visit) {

        Adopter adopter = adopterService.getCurrentUser();

        Pet pet = petService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        AdoptionRequest adoptionRequest = new AdoptionRequest(visit.getDateTime(), visit.getShelter(), adopter, visit.getPet());
        adoptionRequestService.save(adoptionRequest);

        petService.updatePetStatus(pet, PetStatus.PENDING_ADOPTION);
        return "redirect:/adoption-requests/adopter";
    }

    @GetMapping("/view-shelter/{shelter_id}")
    public String viewShelter(Model model, @PathVariable int shelter_id){
        Shelter shelter = shelterService.getShelterById(shelter_id);
        model.addAttribute("shelter", shelter);
        return "shelter/shelter-view";
    }

    @PreAuthorize("hasRole('ROLE_ADOPTER')")
    @GetMapping("/shelter/{shelter_id}/request-visit")
    public String visitShelter(@PathVariable Integer shelter_id, Model model) {
        Shelter shelter = shelterService.findByUserId(shelter_id)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        Adopter adopter = adopterService.getCurrentUser();

        Visit visit = new Visit(LocalDateTime.now(), shelter, adopter);

        model.addAttribute("visit",visit);
        return "adopter/new-visit";
    }

    @PreAuthorize("hasRole('ROLE_ADOPTER')")
    @PostMapping("/shelter/{shelter_id}/request-visit")
    public String visitShelter(@PathVariable int shelter_id, Model model){
        Shelter shelter = shelterService.getShelterById(shelter_id);
        Adopter adopter = adopterService.getCurrentUser();
        Visit visit = new Visit(LocalDateTime.now(),shelter,adopter);
        visitService.save(visit);
        return "redirect:/adopter/dashboard";
    }

}

