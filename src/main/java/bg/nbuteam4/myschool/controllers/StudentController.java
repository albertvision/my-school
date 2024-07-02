package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.StudentSaveRequest;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.Student;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.StudentRepository;
import bg.nbuteam4.myschool.validation.GlobalFilter;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

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
    String index(
            Model model,
            @GlobalFilter("school") School school
    ) {
        List<Student> students = studentRepository.findBySchoolId(school.getId());

        model.addAttribute("students", students);
        model.addAttribute("title", "Ученици");

        return "student/index";
    }

    @GetMapping("/{id}")
    String edit(
            Model model,
            @PathVariable("id") Long id,
            @GlobalFilter("school") School school
    ) {
        Student student = studentRepository.findByIdAndSchool(id, school).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        StudentSaveRequest request = Optional.ofNullable((StudentSaveRequest) model.getAttribute("request"))
                .orElse(StudentSaveRequest.createFromEntity(student));

        model.addAttribute("request", request);
        model.addAttribute("title", "Редактиране на ученик");

        return "student/save";
    }

    // -------------------
    @PostMapping("/save")
    String doSave(
            @GlobalFilter("school") School school,
            @Valid @ModelAttribute StudentSaveRequest saveRequest,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        Student student = Optional.ofNullable(saveRequest.getId())
                .flatMap(it -> studentRepository.findByIdAndSchool(it, school))
                .orElseGet(Student::new);

        boolean isStudentNew = student.getId() == null;

        if (result.hasErrors()) {
            attributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            attributes.addFlashAttribute("errors", result);
            attributes.addFlashAttribute("request", saveRequest);

            return "redirect:/student/" + (isStudentNew ? "create" : student.getId());
        }

        student = saveRequest.toEntity(student);
        student.setSchool(school);

        try {
            studentRepository.save(student);
        } catch (DataIntegrityViolationException exception) {
            if (exception.getCause() instanceof ConstraintViolationException && ((ConstraintViolationException) exception.getCause()).getConstraintName().equals("student.unique_student_egn_per_school")) {
                result.addError(new FieldError("studentCreateRequest", "egn", "Вече има друг ученик с този ЕГН в това училище."));

                attributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
                attributes.addFlashAttribute("errors", result);
                attributes.addFlashAttribute("request", saveRequest);

                return "redirect:/student/" + (isStudentNew ? "create" : student.getId());
            }
        }

        attributes.addFlashAttribute("result", new ActionResult("Успешно записване.", ActionResultType.SUCCESS));
        return "redirect:/student/" + student.getId();
    }


    @GetMapping("/create")
    String create(Model model) {
        if (!model.containsAttribute("request")) {
            model.addAttribute("request", new StudentSaveRequest().setStatus(true));
        }

        model.addAttribute("title", "Нов ученик");

        return "student/save";
    }

    @PostMapping("/{id}/delete")
    RedirectView deleteStudent(
            RedirectAttributes attributes,
            @PathVariable("id") Long id,
            @SessionAttribute("schoolId") Long schoolId
    ) {
        School school = schoolRepository.findById(schoolId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        studentRepository.deleteByIdAndSchool(id, school);

        attributes.addFlashAttribute("result", new ActionResult("Успешно изтриване.", ActionResultType.SUCCESS));
        return new RedirectView("/student");
    }
}
