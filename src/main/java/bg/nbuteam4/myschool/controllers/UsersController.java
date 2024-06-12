package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.ActionResultType;
import bg.nbuteam4.myschool.entity.User;
import bg.nbuteam4.myschool.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    String index(Model model) {
        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("title", "Потребители");

        return "users/index";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    RedirectView index(RedirectAttributes attributes, @PathVariable("id") Long id) {
        userRepository.deleteById(id);

        attributes.addFlashAttribute("result", new ActionResult("Записът беше изтрит успешно.", ActionResultType.SUCCESS));

        return new RedirectView("/users");
    }
}
