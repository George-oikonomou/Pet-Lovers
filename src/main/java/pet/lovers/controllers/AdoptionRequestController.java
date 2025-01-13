package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pet.lovers.entities.*;
import pet.lovers.service.AdopterService;
import pet.lovers.service.AdoptionRequestService;
import pet.lovers.service.ShelterService;
import pet.lovers.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/adoption-requests")
public class AdoptionRequestController {

    private AdoptionRequestService adoptionRequestService;
    private UserService userService;
    private AdopterService adopterService;


    public AdoptionRequestController( AdoptionRequestService adoptionRequestService , UserService userService, AdopterService adopterService) {
        this.userService = userService;
        this.adoptionRequestService = adoptionRequestService;
        this.adopterService = adopterService;
    }

    @PreAuthorize("hasRole('ROLE_SHELTER')")
    @GetMapping("/shelter")
    public String viewShelterAdoptionRequests(Model model) {
        User currentUser = userService.getCurrentUser();

        List<AdoptionRequest> requests = adoptionRequestService.findByShelter((Shelter) currentUser);

        model.addAttribute("adoptionRequests", requests);
        return "shelter/adoption-requests";
    }

    @PreAuthorize("hasRole('ROLE_ADOPTER')")
    @GetMapping("/adopter")
    public String viewAdopterAdoptionRequests(Model model) {
        User currentUser = userService.getCurrentUser();
        Adopter adopter = adopterService.findByUserId(currentUser.getId()).orElseThrow();

        List <AdoptionRequest> requests = adoptionRequestService.findByAdopterId(adopter.getId());

        model.addAttribute("adoptionRequests", requests);
        return "adopter/adoption-requests";
    }

    @PreAuthorize("hasRole('ROLE_SHELTER')")
    @GetMapping("/{id}")
    public String viewAdoptionRequest(@PathVariable Integer id, Model model) {
        AdoptionRequest request = adoptionRequestService.findById(id)
                .orElseThrow(() -> new IllegalStateException("Adoption request not found"));

        model.addAttribute("adoptionRequest", request);
        return "shelter/adoption-request";
    }

    @PreAuthorize("hasRole('ROLE_SHELTER')")
    @PostMapping("/{id}/approve")
    public String approveAdoptionRequest(@PathVariable Integer id) {
        AdoptionRequest request = adoptionRequestService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Adoption request not found"));

        if (UserStatus.PENDING.equals(request.getRequestStatus())) {
            request.setRequestStatus(UserStatus.APPROVED);
            adoptionRequestService.updateAdoptionRequest(request);
        }

        return "redirect:/shelter/adoption-requests";
    }

    @PreAuthorize("hasRole('ROLE_SHELTER')")
    @PostMapping("/{id}/reject")
    public String rejectAdoptionRequest(@PathVariable Integer id) {
        AdoptionRequest request = adoptionRequestService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Adoption request not found"));

        if (UserStatus.PENDING.equals(request.getRequestStatus())) {
            request.setRequestStatus(UserStatus.REJECTED);
            adoptionRequestService.updateAdoptionRequest(request);
        }

        return "redirect:/shelter/adoption-requests";
    }

}

