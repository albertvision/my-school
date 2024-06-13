package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.ActionResultType;
import bg.nbuteam4.myschool.dto.UserCreateRequest;
import bg.nbuteam4.myschool.entity.Role;
import bg.nbuteam4.myschool.entity.User;
import bg.nbuteam4.myschool.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UsersController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    String index(Model model) {
        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("title", "Потребители");

        return "users/index";
    }

    @GetMapping("/create")
    String create(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserCreateRequest());
        }

        model.addAttribute("title", "Нов потребител");
        model.addAttribute("roles", Role.values());

        return "users/create";
    }

    @PostMapping("/create")
    String doCreate(
            @Valid @ModelAttribute UserCreateRequest requestUser,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            attributes.addFlashAttribute("errors", result);
            attributes.addFlashAttribute("user", requestUser);

            return "redirect:/users/create";
        }

        User user = new User();
        user.setUsername(requestUser.getUsername());
        user.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        user.setRole(Role.valueOf(requestUser.getRole()));

        // todo take care of when username is taken
        userRepository.save(user);

        attributes.addFlashAttribute("result", new ActionResult("Успешно създаване.", ActionResultType.SUCCESS));

        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    RedirectView index(RedirectAttributes attributes, @PathVariable("id") Long id) {
        userRepository.deleteById(id);

        attributes.addFlashAttribute("result", new ActionResult("Успешно изтриване.", ActionResultType.SUCCESS));

        return new RedirectView("/users");
    }
}
