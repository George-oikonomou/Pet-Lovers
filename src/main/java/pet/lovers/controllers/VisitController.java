package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pet.lovers.entities.Adopter;
import pet.lovers.entities.Shelter;
import pet.lovers.entities.Visit;
import pet.lovers.service.UserService;
import pet.lovers.service.VisitService;

import java.util.List;

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
        model.addAttribute("visits", visitService.getVisitsByShelterId(shelter.getId()));//not filtered on purpose
        return "shelter/visits";
    }

    //ADOPTER
    @PreAuthorize("hasRole('ROLE_ADOPTER')")
    @GetMapping("/adopter/visits")
    public String viewAdopterAdoptionRequests(Model model) {
        Adopter currentUser = (Adopter) userService.getCurrentUser();
        List<Visit> visits  = currentUser.getVisits();//not filtered on purpose

        model.addAttribute("visits", visits);
        return "adopter/visit";
    }
}
