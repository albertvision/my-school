package bg.nbuteam4.myschool.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {
    @GetMapping(value = "/")
    String index() {
        return "dashboard";
    }

    @GetMapping(value = "/schools")
    String schools() {
        System.out.println("Tyk sme!");
        return "schools";
    }
}
