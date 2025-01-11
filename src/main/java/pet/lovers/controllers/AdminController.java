package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import pet.lovers.entities.Role;
import pet.lovers.entities.User;
import pet.lovers.entities.UserStatus;
import pet.lovers.repositories.RoleRepository;
import pet.lovers.service.AdoptionRequestService;
import pet.lovers.service.PetService;
import pet.lovers.service.ShelterService;
import pet.lovers.service.UserService;


@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    UserService userService;
    RoleRepository roleRepository;
    ShelterService shelterService;
    PetService petService;
    AdoptionRequestService adoptionRequestService;

    public AdminController(UserService userService, RoleRepository roleRepository, ShelterService shelterService, PetService petService, AdoptionRequestService adoptionRequestService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.shelterService = shelterService;
        this.petService = petService;
        this.adoptionRequestService = adoptionRequestService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "index";
    }

    //USER MANAGEMENT
    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/users";
    }

    @GetMapping("/user/{user_id}")
    public String showUser(@PathVariable Long user_id, Model model){
        model.addAttribute("user", userService.getUser(user_id));
        return "admin/user";
    }

    @PostMapping("/user/{user_id}")
    public String editUser(@PathVariable Long user_id, @ModelAttribute("user") User user) {
        User the_user = (User) userService.getUser(user_id);
        the_user.setEmail(user.getEmail());
        the_user.setUsername(user.getUsername());
        userService.updateUser(the_user);
        return "/admin/users";
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).get();
        user.getRoles().remove(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/users";

    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).get();
        user.getRoles().add(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/users";

    }

    //SHELTER MANAGEMENT
    @GetMapping("/shelters")
    public String viewPendingShelters(Model model) {
        model.addAttribute("shelters", shelterService.getSheltersByUserStatus(UserStatus.PENDING));
        return "admin/shelters";
    }

    @GetMapping("/shelters/approve/{id}")
    public String approveShelter(@PathVariable int id) {
        shelterService.approveShelter(id);
        return "redirect:/admin/shelters";
    }

    @GetMapping("/shelters/reject/{id}")
    public String rejectShelter(@PathVariable int id) {
        shelterService.rejectShelter(id);
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
        petService.approvePet(id);
        return "redirect:/admin/pets";
    }

    @GetMapping("/pets/reject/{id}")
    public String rejectPet(@PathVariable int id) {
        petService.rejectPet(id);
        return "redirect:/admin/pets";
    }

    // VIEW ADOPTION REQUESTS
    @GetMapping("/adoption-requests")
    public String viewAdoptions(Model model) {
        model.addAttribute("adoptionRequests", adoptionRequestService.getAdoptionRequests());
        return "admin/adoption-requests";
    }


}
