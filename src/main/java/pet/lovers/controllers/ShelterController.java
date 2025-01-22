package pet.lovers.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import pet.lovers.entities.EmploymentRequest;
import pet.lovers.entities.Pet;
import pet.lovers.entities.Shelter;
import pet.lovers.entities.Vet;
import pet.lovers.entities.UserStatus;
import pet.lovers.service.EmploymentRequestService;
import pet.lovers.service.PetService;
import pet.lovers.service.UserService;
import pet.lovers.service.VetService;
import pet.lovers.service.ShelterService;


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

    public ShelterController(UserService userService, PetService petService, EmploymentRequestService employmentRequestService, VetService vetService, ShelterService shelterService) {
        this.userService = userService;
        this.petService = petService;
        this.employmentRequestService = employmentRequestService;
        this.vetService = vetService;
        this.shelterService = shelterService;
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
        shelter.getPets().add(pet);
        shelterService.updateShelter(shelter);
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
            shelter.setVet(selectedVet);
            selectedVet.getShelters().add(shelter);

            shelterService.updateShelter(shelter);
            vetService.updateVet(selectedVet);

            EmploymentRequest request = employmentRequestService.findByVetAndShelter(selectedVet, shelter).orElseThrow(IllegalArgumentException::new);
            employmentRequestService.deleteEmploymentRequest(request);
            employmentRequestService.saveEmploymentRequest(request);
        }catch (IllegalArgumentException e){
            model.addAttribute("error", "Vet request not found!");
            return "shelter/vet-review";
        }
        return "redirect:/shelter/vet-review?success";
    }
}