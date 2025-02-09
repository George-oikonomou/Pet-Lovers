package pet.lovers.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pet.lovers.entities.*;
import pet.lovers.service.*;

import java.util.Optional;


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
    public String getAdminDashboard(Model model) {
        long totalUsers = userService.countTotalUsers();
        int pendingShelters = shelterService.countPendingShelters();
        int newPets = petService.countNewPetListings();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("pendingShelters", pendingShelters);
        model.addAttribute("newPets", newPets);

        return "index";
    }

    //USER MANAGEMENT
    @GetMapping("/users")//todo
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleService.findAll());
        return "admin/users";
    }


    @GetMapping("/user/{user_id}")
    public String showUser(@PathVariable Long user_id, Model model) {
        User user = (User) userService.getUser(user_id);

        String redirectUrl = switch (user) {
            case Adopter adopter -> {
                model.addAttribute("user", adopter);
                yield "/admin/adopter/" + user_id;
            }
            case Shelter shelter -> {
                model.addAttribute("user", shelter);
                yield "/admin/shelter/" + user_id;
            }
            case Vet vet -> {
                model.addAttribute("user", vet);
                yield "/admin/vet/" + user_id;
            }
            default -> "#";
        };

        model.addAttribute("userStatuses", UserStatus.values());
        model.addAttribute("redirectUrl", redirectUrl);
        return "admin/user";
    }

    @GetMapping("/users/{user_id}/delete")
    public String deleteUser(@PathVariable Long user_id) {
        User user = (User) userService.getUser(user_id);


        String email = user.getEmail();
        String username = user.getUsername();
        userService.deleteUser(user);
        System.out.println("Deleting user: " + user.getUsername()+ user.getEmail());

        emailService.sendDeleteUserMessage(email, username);
        return "redirect:/admin/users?deleted";
    }

    @PostMapping("/adopter/{user_id}")
    public String editAdopter(@PathVariable Long user_id, @ModelAttribute("user") Adopter adopter) {
        Adopter theAdopter = (Adopter) userService.getUser(user_id);
        userService.updateUserDetails(theAdopter, adopter.getEmail(), adopter.getUsername(), adopter.getFullName(), adopter.getContactNumber(), adopter.getUserStatus());
        return "redirect:/admin/users";
    }

    @PostMapping("/shelter/{user_id}")
    public String editShelter(@PathVariable Long user_id, @ModelAttribute("user") Shelter shelter) {
        Shelter theShelter = (Shelter) userService.getUser(user_id);
        userService.updateUserDetails(theShelter, shelter.getEmail(), shelter.getUsername(), shelter.getFullName(), shelter.getContactNumber(),shelter.getUserStatus());
        return "redirect:/admin/users";
    }

    @PostMapping("/vet/{user_id}")
    public String editVet(@PathVariable Long user_id, @ModelAttribute("user") Vet vet) {
        Vet theVet = (Vet) userService.getUser(user_id);
        userService.updateUserDetails(theVet, vet.getEmail(), vet.getUsername(), vet.getFullName(), vet.getContactNumber(), vet.getUserStatus());
        return "redirect:/admin/users";
    }

    //SHELTER MANAGEMENT
    @GetMapping("/shelters")
    public String viewPendingShelters(Model model) {
        model.addAttribute("shelters", shelterService.getSheltersByUserStatus(UserStatus.PENDING));
        return "admin/shelters";
    }

    @GetMapping("/shelters/approve/{id}")
    public String approveShelter(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Optional<Shelter> shelter = shelterService.findByUserId(id);
        if (shelter.isPresent()) {
            shelterService.approveShelter(id);
            emailService.sendAcceptShelterMessage(shelter.get());
            redirectAttributes.addFlashAttribute("msg", "Successfully approved shelter.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Shelter not found.");
        }
        return "redirect:/admin/shelters";
    }


    @GetMapping("/shelters/reject/{id}")
    public String rejectShelter(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Optional<Shelter> shelter = shelterService.findByUserId(id);
        if (shelter.isPresent()) {
            shelterService.rejectShelter(id);
            emailService.sendRejectShelterMessage(shelter.get());
            redirectAttributes.addFlashAttribute("msg", "Successfully rejected shelter.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Shelter not found.");
        }

        return "redirect:/admin/shelters";
    }

    //PET MANAGEMENT
    @GetMapping("/pets")
    public String viewPets(Model model) {
        model.addAttribute("pets", petService.getPetsByUserStatus(UserStatus.PENDING));
        return "admin/pets";
    }

    @GetMapping("/pets/approve/{id}")//todo
    public String approvePet(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Optional<Pet> pet = petService.findById(id);
        if (pet.isPresent()) {
            petService.approvePet(id);
            emailService.sendApprovePetMessage(pet.get());
            redirectAttributes.addFlashAttribute("msg", "Successfully approved pet.");
        }else {
            redirectAttributes.addFlashAttribute("error", "Pet not found.");
        }

        return "redirect:/admin/pets";
    }

    @GetMapping("/pets/reject/{id}")
    public String rejectPet(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Optional<Pet> pet = petService.findById(id);
        if (pet.isPresent()) {
            petService.rejectPet(id);
            emailService.sendRejectPetMessage(pet.get());
            redirectAttributes.addFlashAttribute("msg", "Successfully rejected pet.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Pet not found.");
        }

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

    @GetMapping("/adoption-request/{id}")//todo
    public String viewAdoptionRequest(@PathVariable int id, Model model) {
        try {
            AdoptionRequest request = adoptionRequestService.findById(id).orElseThrow(IllegalArgumentException::new);
            model.addAttribute("adoptionRequest", request);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Adoption request not found.");
        }

        return "admin/adoption-request";
    }

    @PostMapping("/adoption-request/{id}/notify")
    public String notifyShelter(@PathVariable int id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        adoptionRequestService.findById(id)
                              .ifPresent(emailService::sendReminderToShelter);

        String referer = request.getHeader("Referer");

        if (referer != null) {
            redirectAttributes.addFlashAttribute("msg", "Successfully sent notification to shelter.");

            if (referer.contains("/adoption-requests")) {
                return "redirect:/admin/adoption-requests";
            } else if (referer.contains("/adoption-request")) {
                return "redirect:/admin/adoption-request/" + id;
            }
        }

        redirectAttributes.addFlashAttribute("msg", "Successfully sent notification to shelter.");
        return "redirect:/admin/adoption-requests";
    }
}