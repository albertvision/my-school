package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.StudentSaveRequest;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.Student;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/student")
@PreAuthorize("hasRole('ADMIN')")
public class StudentController {
    private final SchoolRepository schoolRepository;
    private final HttpSession httpSession;
    private final StudentRepository studentRepository;

    public StudentController(
            StudentRepository studentRepository,
            SchoolRepository schoolRepository,
            HttpSession httpSession
    ) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.httpSession = httpSession;
    }

    @GetMapping
    String index(Model model) {
        long schoolId = (long) httpSession.getAttribute("schoolId");

        School school = schoolRepository.findById(schoolId).orElse(null);
        List<Student> students = studentRepository.findBySchoolId(schoolId);

        model.addAttribute("students", students);
        model.addAttribute("title", "Ученици");

        return "student/index";
    }

    @GetMapping("/{egn}")
    String edit(
            Model model,
            @PathVariable("egn") String egn
    ) {
        Student student = studentRepository.findByEgn(egn).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        model.addAttribute("student", student);

        model.addAttribute("title", "Редактиране на ученици");
        //   model.addAttribute("roles", Role.values());

        return "student/save";
    }

    // -------------------
    @PostMapping("/edit/{egn}")
    public String editStudent(
            @Valid @ModelAttribute StudentSaveRequest studentCreateRequest,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            attributes.addFlashAttribute("errors", result);
            attributes.addFlashAttribute("student", studentCreateRequest);

            return "redirect:/student/" + studentCreateRequest.getEgn();
        }

        Student student = studentRepository.findByEgn(studentCreateRequest.getEgn())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        student.setFirstName(studentCreateRequest.getFirstName());
        student.setMiddleName(studentCreateRequest.getMiddleName());
        student.setLastName(studentCreateRequest.getLastName());
        student.setParentEGN(studentCreateRequest.getParentEgn());
        student.setParentName(studentCreateRequest.getParentName());
        student.setStatus(studentCreateRequest.getStatus());

        studentRepository.save(student);

        attributes.addFlashAttribute("result", new ActionResult("Успешно редактиране на ученика.", ActionResultType.SUCCESS));

        return "redirect:/student";
    }

    // -------------------
    @PostMapping("/create")
    String doCreate(
            @Valid @ModelAttribute StudentSaveRequest saveRequest,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            attributes.addFlashAttribute("errors", result);
            attributes.addFlashAttribute("student", saveRequest);

            return "redirect:/student/create";
        }

        Student student = new Student();
        student.setEgn(saveRequest.getEgn());
        student.setFirstName(saveRequest.getFirstName());
        student.setMiddleName(saveRequest.getMiddleName());
        student.setLastName(saveRequest.getLastName());
        student.setParentEGN(saveRequest.getParentEgn());
        student.setParentName(saveRequest.getParentName());
        student.setStatus(saveRequest.getStatus());

        studentRepository.save(student);

        attributes.addFlashAttribute("result", new ActionResult("Успешно създаване на ученик.", ActionResultType.SUCCESS));
        return "redirect:/student";
    }


    @GetMapping("/create")
    String create(Model model) {
        if (!model.containsAttribute("student")) {
            model.addAttribute("student", new Student());
        }

        model.addAttribute("title", "Нов ученик");
//
        return "student/save";
    }

    @PostMapping("/{egn}/delete")
    RedirectView deleteStudent(
            RedirectAttributes attributes,
            @PathVariable("egn") String egn
    ) {
        studentRepository.deleteByEgn(egn);
        attributes.addFlashAttribute("result", new ActionResult("Успешно изтриване.", ActionResultType.SUCCESS));
        return new RedirectView("/student");
    }
}
