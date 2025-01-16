package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pet.lovers.entities.Shelter;
import pet.lovers.service.UserService;
import pet.lovers.service.VisitService;
@Controller
public class VisitController {

    private final VisitService visitService;
    private final UserService userService;

    public VisitController(VisitService visitService, UserService userService) {
        this.visitService = visitService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_SHELTER')")
    @GetMapping("/shelter/visits")
    public String showVisits(Model model) {
        Shelter shelter = (Shelter) userService.getCurrentUser();
        model.addAttribute("visits", visitService.getVisitsByShelterId(shelter.getId()));
        return "shelter/visits";
    }
}
