package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

import pet.lovers.entities.Role;
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
        return "redirect:/admin/users";
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
