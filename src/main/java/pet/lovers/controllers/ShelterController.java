package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pet.lovers.entities.*;
import pet.lovers.service.*;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/shelter")
@PreAuthorize("hasRole('ROLE_SHELTER')")
public class ShelterController {
    private final UserService userService;
    private final PetService petService;
    private final EmploymentRequestService employmentRequestService;
    private final VetService vetService;
    private final ShelterService shelterService;
    private final VisitService visitService;

    public ShelterController(UserService userService, PetService petService, EmploymentRequestService employmentRequestService, VetService vetService, ShelterService shelterService, VisitService visitService) {
        this.userService = userService;
        this.petService = petService;
        this.employmentRequestService = employmentRequestService;
        this.vetService = vetService;
        this.shelterService = shelterService;
        this.visitService = visitService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "index";
    }


    //View Pets
    @GetMapping("/pets")
    public String showPets(Model model) {
        Shelter shelter = (Shelter) userService.getCurrentUser();
        model.addAttribute("pets",shelter.getPets());
        return "shelter/pets";
    }

    //Register New Pet
    @GetMapping("/new-pet")
    public String newPet(Model model) {
        Pet pet = new Pet();
        model.addAttribute("newPet", pet);
        return "shelter/new-pet";
    }

    @PostMapping("/new-pet")
    public String savePet(@ModelAttribute Pet pet, Model model){//todo Service
        Shelter shelter = (Shelter) userService.getCurrentUser();
        pet.setShelter(shelter);
        Integer id = petService.savePet(pet);
        String message = "Pet '"+id+"' : "+ pet.getName() +" saved successfully !";
        model.addAttribute("msg", message);
        return "shelter/pets";
    }

    @GetMapping("/vet-review")
    public String showVetReviewPage(Model model) {
        Shelter shelter = (Shelter) userService.getCurrentUser();

        List<EmploymentRequest> employmentRequests = shelter.getEmploymentRequests()
                                                            .stream()
                                                            .filter(employmentRequest -> employmentRequest.getVet().getUserStatus() == UserStatus.APPROVED && employmentRequest.getStatus() == UserStatus.PENDING)
                                                            .toList();

        model.addAttribute("pendingVets", employmentRequests);
        return "shelter/vet-review";
    }

    @PostMapping("/vet-review/approve")//todo
    public String approveVet(@RequestParam("vetRequestId") Integer vetRequestId,Model model) {
        Shelter shelter = (Shelter) userService.getCurrentUser();
        Vet selectedVet = vetService.getVetById(vetRequestId);

        try {
            shelter.setVet(selectedVet);
            selectedVet.getShelters().add(shelter);

            shelterService.updateShelter(shelter);
            vetService.updateVet(selectedVet);

            EmploymentRequest request = employmentRequestService.findByVetAndShelter(selectedVet, shelter).orElseThrow(IllegalArgumentException::new);
            request.setStatus(UserStatus.APPROVED);
            employmentRequestService.saveEmploymentRequest(request);
        }catch (IllegalArgumentException e){
            model.addAttribute("error", "Vet request not found!");
            return "shelter/vet-review";
        }
        return "redirect:/shelter/vet-review?success";
    }

    @PostMapping("/vet-review/reject")//todo
    public String rejectVet(@RequestParam("vetRequestId") Integer vetRequestId,Model model) {
        Shelter shelter = (Shelter) userService.getCurrentUser();
        Vet selectedVet = vetService.getVetById(vetRequestId);

        try {
            EmploymentRequest request = employmentRequestService.findByVetAndShelter(selectedVet, shelter).orElseThrow(IllegalArgumentException::new);
            employmentRequestService.deleteEmploymentRequest(request);
        }catch (IllegalArgumentException e){
            model.addAttribute("error", "Vet request not found!");
            return "shelter/vet-review";
        }
        return "redirect:/shelter/vet-review?rejected";
    }

    @GetMapping("/pets/{petId}/edit")
    public String editPet(Model model, @PathVariable String petId) {
        Shelter shelter = (Shelter) userService.getCurrentUser();
        try {
            Pet pet = shelter.getPets().stream()
                                       .filter(p -> p.getId() == Integer.parseInt(petId))
                                       .findFirst()
                                       .orElseThrow(IllegalArgumentException::new);

            model.addAttribute("pet", pet);
            model.addAttribute("PetStatuses", Arrays.asList(PetStatus.AVAILABLE, PetStatus.UNAVAILABLE));
            return "shelter/edit-pet";
        }catch (IllegalArgumentException e){
            model.addAttribute("error", "Pet not found!");
            return "shelter/pets";
        }
    }

    @PostMapping("/pets/pet/edit")
    public String updatePet(@ModelAttribute Pet pet, RedirectAttributes redirectAttributes) {
        Shelter shelter = (Shelter) userService.getCurrentUser();



        try {
            Pet selectedPet = shelter.getPets().stream()
                                               .filter(p -> p.getId() == pet.getId())
                                               .findFirst()
                                               .orElseThrow(IllegalArgumentException::new);


            if (pet.getPetStatus() != selectedPet.getPetStatus()
                    && (selectedPet.getPetStatus() == PetStatus.PENDING_ADOPTION || selectedPet.getPetStatus() == PetStatus.ADOPTED))
            {
                redirectAttributes.addFlashAttribute("error", "Pets under adoption process cannot be updated!");
                return "redirect:/shelter/pets";
            }



            petService.updatePet(selectedPet, pet.getName(), pet.getBreed(), pet.getPetStatus(), pet.getYearBirthed(), pet.getType(), pet.getWeight(), pet.getSex());
            redirectAttributes.addFlashAttribute("success", "Pet updated successfully!");
            return "redirect:/shelter/pets";
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("error", "Pet not found!");
            return "redirect:/shelter/pets";
        }
    }

    @GetMapping("/visits")
    public String showVisits(Model model) {
        Shelter shelter = (Shelter) userService.getCurrentUser();

        List<Visit> visits = visitService.getVisitsByShelterId(shelter.getId())
                .stream()
                .toList();

        model.addAttribute("visits", visits);

        return "shelter/visits";
    }
}