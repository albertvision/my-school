package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.ActionResultType;

import bg.nbuteam4.myschool.dto.SchoolClassCreateRequest;

import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.SchoolClass;
import bg.nbuteam4.myschool.repository.SchoolClassRepository;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/schoolClass")
public class SchoolClassController {

    private final SchoolClassRepository schoolClassRepository;
    private final SchoolRepository schoolRepository;
    private final SessionProperties sessionProperties;
    private final HttpSession httpSession;
    private final DashboardController dashboardController;


    public SchoolClassController(SchoolClassRepository schoolClassRepository, SchoolRepository schoolRepository, SessionProperties sessionProperties, HttpSession httpSession, DashboardController dashboardController) {
        this.schoolClassRepository = schoolClassRepository;
        this.schoolRepository = schoolRepository;
        this.sessionProperties = sessionProperties;
        this.httpSession = httpSession;
        this.dashboardController = dashboardController;
    }


    @GetMapping
    public String index(Model model) {


        long schoolId = (long) httpSession.getAttribute("schoolId");

        School school = schoolRepository.findById(schoolId).orElse(null);
        List<SchoolClass> schoolschoolClass = schoolClassRepository.findBySchoolId(schoolId);

        model.addAttribute("school", school);
        model.addAttribute("schoolClass", schoolschoolClass);
        return "schoolClass/index";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @RequestParam String name, Model model) {
        SchoolClass schoolClass = schoolClassRepository.findById(id).orElse(null);
        if (schoolClass != null) {
            schoolClass.setName(name); // Промяна на полето name (или други полета)
            schoolClassRepository.save(schoolClass); // Записване на промените
        }
        return "redirect:/schoolClass";
    }

    @PostMapping("/create")
    public String createSchoolClass(@Valid @ModelAttribute SchoolClassCreateRequest requestschoolClass,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            redirectAttributes.addFlashAttribute("errors", result);
            redirectAttributes.addFlashAttribute("schoolClass", requestschoolClass);

            return "redirect:/schoolClass";
        }

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName(requestschoolClass.getName());
        schoolClass.setSchool(schoolRepository.findById(requestschoolClass.getSchoolId()).orElse(null));

        schoolClassRepository.save(schoolClass); // Записване на промените

        redirectAttributes.addFlashAttribute("result", new ActionResult("Успешно създаване.", ActionResultType.SUCCESS));
        return "redirect:/schoolClass";
    }


    @PostMapping("/{id}/delete")
    RedirectView index(RedirectAttributes attributes, @PathVariable("id") Long id) {

        try {
            schoolClassRepository.deleteById(id);
            attributes.addFlashAttribute("result", new ActionResult("Успешно изтриване.", ActionResultType.SUCCESS));
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            System.out.println("Съществуват свързани данни, изтриването не е възможно.");
            attributes.addFlashAttribute("result", new ActionResult("Съществуват свързани данни, изтриването не е възможно.", ActionResultType.ERROR));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/schoolClass");
    }

}
