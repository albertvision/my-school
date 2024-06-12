package bg.nbuteam4.myschool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping
    String index(Model model) {
        model.addAttribute("title", "Табло");

        return "dashboard/index";
    }

    @GetMapping("/schools")
    String schools() {
        System.out.println("Tyk sme!");
        return "schools";
    }
}
