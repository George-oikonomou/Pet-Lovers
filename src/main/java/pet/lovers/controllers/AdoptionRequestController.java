package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pet.lovers.entities.AdoptionRequest;
import pet.lovers.entities.PetStatus;
import pet.lovers.entities.UserStatus;
import pet.lovers.service.AdoptionRequestService;
import pet.lovers.service.PetService;
import pet.lovers.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/adoption-requests")
public class AdoptionRequestController {

    private final AdoptionRequestService adoptionRequestService;
    private final UserService userService;
    private final PetService petService;


    public AdoptionRequestController(AdoptionRequestService adoptionRequestService , UserService userService, PetService petService) {
        this.userService = userService;
        this.adoptionRequestService = adoptionRequestService;
        this.petService = petService;
    }

    //ADOPTER
    @PreAuthorize("hasRole('ROLE_ADOPTER')")
    @GetMapping("/adopter")
    public String viewAdopterAdoptionRequests(Model model) {
        List <AdoptionRequest> requests = adoptionRequestService.findByAdopterId(userService.getCurrentUser().getId()); //not filtered on purpose

        model.addAttribute("adoptionRequests", requests);
        return "adopter/adoption-requests";
    }

    //SHELTER
    @PreAuthorize("hasRole('ROLE_SHELTER')")
    @GetMapping("/shelter")
    public String viewShelterAdoptionRequests(Model model) {
        List<AdoptionRequest> requests = adoptionRequestService.findByShelterId(userService.getCurrentUser().getId())
                                                               .stream()
                                                               .filter(adoptionRequest -> adoptionRequest.getAdopter().getUserStatus() == UserStatus.APPROVED || adoptionRequest.getRequestStatus() != UserStatus.PENDING)
                                                               .toList();

        model.addAttribute("adoptionRequests", requests);
        return "shelter/adoption-requests";
    }

    @PreAuthorize("hasRole('ROLE_SHELTER')")
    @GetMapping("/shelter/{id}")
    public String viewAdoptionRequest(@PathVariable Integer id, Model model) {
        try {
            AdoptionRequest request = adoptionRequestService.findActiveById(id).orElseThrow(IllegalArgumentException::new);
            model.addAttribute("adoptionRequest", request);
            return "shelter/adoption-request";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Adoption request not found!");
            return "/error/error-404";
        }
    }

    @PreAuthorize("hasRole('ROLE_SHELTER')")
    @PostMapping("/shelter/{id}/approve")
    public String approveAdoptionRequest(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            AdoptionRequest request = adoptionRequestService.findActiveById(id).orElseThrow(IllegalArgumentException::new);
            if (request.getRequestStatus().equals(UserStatus.PENDING)) {//todo  SERVICE
                request.setRequestStatus(UserStatus.APPROVED);
                request.getPet().setPetStatus(PetStatus.ADOPTED);
                petService.savePet(request.getPet());
                adoptionRequestService.updateAdoptionRequest(request);
                redirectAttributes.addFlashAttribute("msg", "Adoption request approved. " + request.getPet().getName() + " has been adopted.");
            }
            return "redirect:/adoption-requests/shelter";
        }catch(IllegalArgumentException e){
            model.addAttribute("error", "Adoption request not found!");
            return "/error/error-404";
        }
    }

    @PreAuthorize("hasRole('ROLE_SHELTER')")
    @PostMapping("/shelter/{id}/reject")
    public String rejectAdoptionRequest(@PathVariable Integer id,Model model, RedirectAttributes redirectAttributes) {
        try {
            AdoptionRequest request = adoptionRequestService.findActiveById(id).orElseThrow(IllegalArgumentException::new);
            if (request.getRequestStatus().equals(UserStatus.PENDING)) {//todo SERVICE
                request.setRequestStatus(UserStatus.REJECTED);
                request.getPet().setPetStatus(PetStatus.AVAILABLE);
                petService.savePet(request.getPet());
                adoptionRequestService.updateAdoptionRequest(request);
                redirectAttributes.addFlashAttribute("msg", "Adoption request declined. "+request.getPet().getName()+" is now available for adoption."

);
            }
            return "redirect:/adoption-requests/shelter";
        }catch(IllegalArgumentException e){
            model.addAttribute("error", "Adoption request not found!");
            return "/error/error-404";
        }
    }
}