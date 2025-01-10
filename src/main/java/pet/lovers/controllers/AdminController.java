package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import java.util.List;
import pet.lovers.entities.User;
import pet.lovers.repositories.RoleRepository;
import pet.lovers.service.ShelterService;
import pet.lovers.service.UserService;


@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    UserService userService;
    RoleRepository roleRepository;
    ShelterService shelterService;

    public AdminController(UserService userService, RoleRepository roleRepository, ShelterService shelterService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.shelterService = shelterService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "index";
    }

    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/users";
    }

    @GetMapping("/shelters")
    public String viewPendingShelters(Model model) {
        model.addAttribute("shelters", shelterService.getPendingShelters());
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

}
