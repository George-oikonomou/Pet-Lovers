package pet.lovers.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import pet.lovers.entities.*;
import pet.lovers.service.*;


@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final ShelterService shelterService;
    private final PetService petService;
    private final AdoptionRequestService adoptionRequestService;
    private final EmailService emailService;


    public AdminController(UserService userService, RoleService roleService, ShelterService shelterService, PetService petService, AdoptionRequestService adoptionRequestService, EmailService emailService) {
        this.userService = userService;
        this.roleService = roleService;
        this.shelterService = shelterService;
        this.petService = petService;
        this.adoptionRequestService = adoptionRequestService;
        this.emailService = emailService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "index";
    }

    //USER MANAGEMENT
    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleService.findAll());
        return "admin/users";
    }

    @GetMapping("/user/{user_id}")
    public String showUser(@PathVariable Long user_id, Model model){
        User user = (User) userService.getUser(user_id);
        model.addAttribute("user", user);

        String redirectUrl = switch (user) {
            case Adopter adopter-> "/admin/adopter/" + user_id;
            case Shelter shelter-> "/admin/shelter/" + user_id;
            case Vet vet-> "/admin/vet/" + user_id;
            default -> "#";
        };

        model.addAttribute("redirectUrl", redirectUrl);
        return "admin/user";
    }

    @PostMapping("/adopter/{user_id}")
    public String editAdopter(@PathVariable Long user_id, @ModelAttribute("user") Adopter adopter) {
        Adopter theAdopter = (Adopter) userService.getUser(user_id);
        userService.updateUserDetails(theAdopter, adopter.getEmail(), adopter.getUsername(), adopter.getFullName(), adopter.getContactNumber());
        return "redirect:/admin/users";
    }

    @PostMapping("/shelter/{user_id}")
    public String editShelter(@PathVariable Long user_id, @ModelAttribute("user") Shelter shelter) {
        Shelter theShelter = (Shelter) userService.getUser(user_id);
        userService.updateUserDetails(theShelter, shelter.getEmail(), shelter.getUsername(), shelter.getFullName(), shelter.getContactNumber());
        return "redirect:/admin/users";
    }

    @PostMapping("/vet/{user_id}")
    public String editVet(@PathVariable Long user_id, @ModelAttribute("user") Vet vet) {
        Vet theVet = (Vet) userService.getUser(user_id);
        userService.updateUserDetails(theVet, vet.getEmail(), vet.getUsername(), vet.getFullName(), vet.getContactNumber());
        return "redirect:/admin/users";
    }

    //SHELTER MANAGEMENT
    @GetMapping("/shelters")
    public String viewPendingShelters(Model model) {
        model.addAttribute("shelters", shelterService.getSheltersByUserStatus(UserStatus.PENDING));
        return "admin/shelters";
    }

    @GetMapping("/shelters/approve/{id}")
    public String approveShelter(@PathVariable int id) {
        shelterService.findByUserId(id).ifPresent(shelter -> {
            shelterService.approveShelter(id);
            emailService.sendAcceptShelterMessage(shelter);
        });

        return "redirect:/admin/shelters";
    }

    @GetMapping("/shelters/reject/{id}")
    public String rejectShelter(@PathVariable int id) {
        shelterService.findByUserId(id).ifPresent(shelter -> {
            shelterService.rejectShelter(id);
            emailService.sendRejectShelterMessage(shelter);
        });

        return "redirect:/admin/shelters";
    }

    //PET MANAGEMENT
    @GetMapping("/pets")
    public String viewPets(Model model) {
        model.addAttribute("pets", petService.getPetsByUserStatus(UserStatus.PENDING));
        return "admin/pets";
    }

    @GetMapping("/pets/approve/{id}")
    public String approvePet(@PathVariable int id) {
        petService.findById(id).ifPresent(pet -> {
            petService.approvePet(id);
            emailService.sendApprovePetMessage(pet);
        });

        return "redirect:/admin/pets";
    }

    @GetMapping("/pets/reject/{id}")
    public String rejectPet(@PathVariable int id) {
        petService.findById(id).ifPresent(pet -> {
            petService.rejectPet(id);
            emailService.sendRejectPetMessage(pet);
        });

        return "redirect:/admin/pets";
    }

    @GetMapping("/pets/all")
    public String viewAllPets(Model model) {
        model.addAttribute("pets", petService.getPets());
        return "admin/all-pets";
    }

    // VIEW ADOPTION REQUESTS
    @GetMapping("/adoption-requests")
    public String viewAdoptions(Model model) {
        model.addAttribute("adoptionRequests", adoptionRequestService.getAdoptionRequests());
        return "admin/adoption-requests";
    }

    @GetMapping("/adoption-request/{id}")
    public String viewAdoptionRequest(@PathVariable int id, Model model) {
        AdoptionRequest request = adoptionRequestService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Adoption request not found"));

        model.addAttribute("adoptionRequest", request);
        return "admin/adoption-request";
    }

    @PostMapping("/adoption-request/{id}/notify")
    public String notifyShelter(@PathVariable int id, HttpServletRequest request) {
        adoptionRequestService.findById(id)
                .ifPresent(adoptionRequest -> emailService.sendReminderToShelter(adoptionRequest));

        String referer = request.getHeader("Referer");

        if (referer != null) {
            if (referer.contains("/adoption-requests")) {
                return "redirect:/admin/adoption-requests";
            } else if (referer.contains("/adoption-request")) {
                return "redirect:/admin/adoption-request/" + id;
            }
        }

        return "redirect:/admin/adoption-requests";
    }
}