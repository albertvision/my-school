package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.repository.SchoolRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final SchoolRepository schoolRepository;

    public DashboardController(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }


    @GetMapping
    String index(Model model) {
        model.addAttribute("title", "Табло");

        //for school selection
        model.addAttribute("schools", schoolRepository.findAll());

        //TODO the selected school needs to be kept in the session for use in other pages.

        return "dashboard/index";
    }

    @GetMapping("/schools")
    String schools() {
        System.out.println("Tyk sme!");
        return "schools";
    }
}
