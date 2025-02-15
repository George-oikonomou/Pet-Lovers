package pet.lovers.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(Model model) {

        model.addAttribute("title", "Home");
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("title", "About Us");
        model.addAttribute("content", "Our mission is to help pets find loving homes by connecting adopters with animal shelters. We started in 2024 and have since helped a lot of pets find their forever homes. We believe that every pet deserves a loving home and we are here to help make that happen!");
        return "auth/about";
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        model.addAttribute("title", "Contact Us");
        model.addAttribute("email", "support@petadoption.com");
        model.addAttribute("phone", "+1 (800) 555-1234");
        model.addAttribute("address", "123 Pet Lane, Adoption City, PA 55555");
        return "auth/contact";
    }
}
