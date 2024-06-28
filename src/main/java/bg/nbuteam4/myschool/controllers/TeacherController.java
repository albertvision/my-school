package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.ActionResultType;
import bg.nbuteam4.myschool.dto.TeacherCreateRequest;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.Teacher;
import bg.nbuteam4.myschool.repository.TeacherRepository;
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
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;
    private final SessionProperties sessionProperties;
    private final HttpSession httpSession;
    private final DashboardController dashboardController;


    public TeacherController(TeacherRepository teacherRepository, SchoolRepository schoolRepository, SessionProperties sessionProperties, HttpSession httpSession, DashboardController dashboardController) {
        this.teacherRepository = teacherRepository;
        this.schoolRepository = schoolRepository;
        this.sessionProperties = sessionProperties;
        this.httpSession = httpSession;
        this.dashboardController = dashboardController;
    }


    @GetMapping
    public String index(Model model) {


        long schoolId = (long) httpSession.getAttribute("schoolId");

        School school = schoolRepository.findById(schoolId).orElse(null);
        List<Teacher> schoolTeacher = teacherRepository.findBySchoolId(schoolId);

        model.addAttribute("school", school);
        model.addAttribute("teacher", schoolTeacher);
        return "teacher/index";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @RequestParam String name, String egn, Model model) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher != null) {
            teacher.setName(name); // Промяна на полето name (или други полета)
            teacher.setEgn(egn);
            teacherRepository.save(teacher); // Записване на промените
        }
        return "redirect:/teacher";
    }

    @PostMapping("/create")
    public String createTeacher(@Valid @ModelAttribute TeacherCreateRequest requestteacher,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            redirectAttributes.addFlashAttribute("errors", result);
            redirectAttributes.addFlashAttribute("teacher", requestteacher);

            return "redirect:/teacher";
        }

        Teacher teacher = new Teacher();
        teacher.setName(requestteacher.getName());
        teacher.setEgn(requestteacher.getEgn());
        teacher.setSchool(schoolRepository.findById(requestteacher.getSchoolId()).orElse(null));

        teacherRepository.save(teacher); // Записване на промените

        redirectAttributes.addFlashAttribute("result", new ActionResult("Успешно създаване.", ActionResultType.SUCCESS));
        return "redirect:/teacher";
    }


    @PostMapping("/{id}/delete")
    RedirectView index(RedirectAttributes attributes, @PathVariable("id") Long id) {

        try {
            teacherRepository.deleteById(id);
            attributes.addFlashAttribute("result", new ActionResult("Успешно изтриване.", ActionResultType.SUCCESS));
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            System.out.println("Съществуват свързани данни, изтриването не е възможно.");
            attributes.addFlashAttribute("result", new ActionResult("Съществуват свързани данни, изтриването не е възможно.", ActionResultType.ERROR));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/teacher");
    }

}
