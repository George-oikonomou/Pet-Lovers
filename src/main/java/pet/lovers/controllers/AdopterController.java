package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pet.lovers.entities.Adopter;
import pet.lovers.entities.AdoptionRequest;
import pet.lovers.entities.Pet;
import pet.lovers.entities.Visit;
import pet.lovers.entities.Shelter;
import pet.lovers.entities.PetStatus;
import pet.lovers.service.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_ADOPTER')")
@RequestMapping("/adopter")
public class AdopterController {

    private final UserService userService;
    PetService petService;
    AdoptionRequestService adoptionRequestService;
    AdopterService adopterService;
    VisitService visitService;
    ShelterService shelterService;

    public AdopterController(VisitService visitService, PetService petService, AdoptionRequestService adoptionRequestService, AdopterService adopterService, ShelterService shelterService, UserService userService) {
        this.visitService = visitService;
        this.petService = petService;
        this.adoptionRequestService = adoptionRequestService;
        this.adopterService = adopterService;
        this.shelterService = shelterService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Adopter currentUser = adopterService.getCurrentUser();
        List<AdoptionRequest> adoptionRequests  = currentUser.getAdoptionRequests()
                .stream()
                .toList();

        model.addAttribute("adoptionRequests", adoptionRequests);
        return "index";
    }


    @GetMapping("/pets/{id}/request-adoption")
    public String newAdoptionRequest(@PathVariable Integer id, Model model) {
        Adopter adopter = adopterService.getCurrentUser();

        try {
            Pet pet = petService.findById(id).orElseThrow(IllegalArgumentException::new);
            AdoptionRequest adoptionRequest = new AdoptionRequest(LocalDateTime.now(), pet.getShelter(), adopter, pet);
            model.addAttribute("adoptionRequest", adoptionRequest);
            return "adopter/new-adoption-request";
        }catch (IllegalArgumentException e){
            model.addAttribute("error", "Pet not found!");
            return "/error/error-404";
        }
    }

    @PostMapping("/pets/{id}/request-adoption")
    public String saveAdoptionRequest(@PathVariable int id, @ModelAttribute("adoptionRequest") AdoptionRequest adoptionRequest, Model model, RedirectAttributes redirectAttributes) {

        Adopter adopter = adopterService.getCurrentUser();

        try {
            Pet pet = petService.findById(id).orElseThrow(IllegalArgumentException::new);
            if (adoptionRequestService.existsPendingRequest(adopter, pet))
                throw new IllegalStateException("You have already requested to adopt this pet!");


            AdoptionRequest theAdoptionRequest = new AdoptionRequest(adoptionRequest.getDateTime(), adoptionRequest.getShelter(), adopter, pet);
            adoptionRequestService.save(theAdoptionRequest);
            adopter.getAdoptionRequests().add(theAdoptionRequest);
            userService.updateUser(adopter);

            petService.updatePetStatus(pet, PetStatus.PENDING_ADOPTION);

            redirectAttributes.addFlashAttribute("msg", "Adoption request sent successfully!");
            return "redirect:/adoption-requests/adopter";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Pet not found!");
            return "/error/error-404";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/pets";
        }
    }


    @GetMapping("/shelter/{shelter_id}/request-visit")
    public String visitShelter(@PathVariable Integer shelter_id, Model model) {
        Adopter adopter = adopterService.getCurrentUser();

        try {
            Shelter shelter = shelterService.findActiveByUserId(shelter_id).orElseThrow(IllegalArgumentException::new);
            Visit visit = new Visit(LocalDateTime.now(), shelter, adopter);
            model.addAttribute("visit", visit);
            return "adopter/new-visit";
        }catch (IllegalArgumentException e){
            model.addAttribute("error", "Shelter not found!");
            return "/error/error-404";
        }
    }

    @PostMapping("/shelter/{shelter_id}/request-visit")
    public String visitShelter(@PathVariable int shelter_id, @ModelAttribute("visit") Visit visit ,Model model, RedirectAttributes redirectAttributes) {
        Adopter adopter = adopterService.getCurrentUser();

        try {
            Shelter shelter = shelterService.findActiveByUserId(shelter_id).orElseThrow(IllegalArgumentException::new);
            Visit Thevisit = new Visit(visit.getDateTime(), shelter, adopter);
            visitService.save(Thevisit);
            adopter.getVisits().add(Thevisit);
            userService.updateUser(adopter);
            redirectAttributes.addFlashAttribute("msg", "Visit request sent successfully!");
            return "redirect:/adopter/visits";
        }catch (IllegalArgumentException e){
            model.addAttribute("error", "Shelter not found!");
            return "/error/error-404";
        }
    }


    @GetMapping("/visits")
    public String viewAdopterAdoptionRequests(Model model) {
        Adopter currentUser = adopterService.getCurrentUser();
        List<Visit> visits  = currentUser.getVisits()
                .stream()
                .filter(visit -> visit.getDateTime().isAfter(LocalDateTime.now()))
                .toList();

        model.addAttribute("visits", visits);
        return "adopter/visit";
    }
}