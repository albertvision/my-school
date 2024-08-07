package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.TeacherSaveRequest;
import bg.nbuteam4.myschool.entity.EducObj;
import bg.nbuteam4.myschool.entity.School;
import bg.nbuteam4.myschool.entity.TeachEduc;
import bg.nbuteam4.myschool.entity.Teacher;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.repository.EducObjRepository;
import bg.nbuteam4.myschool.repository.SchoolRepository;
import bg.nbuteam4.myschool.repository.TeachEducRepository;
import bg.nbuteam4.myschool.repository.TeacherRepository;
import bg.nbuteam4.myschool.validation.GlobalFilter;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;
    private final HttpSession httpSession;
    private final EducObjRepository educObjRepository;

    private final TeachEducRepository teachEducRepository;


    public TeacherController(TeacherRepository teacherRepository,
                             SchoolRepository schoolRepository,
                             HttpSession httpSession,
                             EducObjRepository educObjRepository,
                             TeachEducRepository teachEducRepository
    ) {
        this.teacherRepository = teacherRepository;
        this.schoolRepository = schoolRepository;
        this.httpSession = httpSession;
        this.educObjRepository = educObjRepository;
        this.teachEducRepository = teachEducRepository;
    }


    @GetMapping
    public String index(
            Model model,
            @GlobalFilter("school") School school
    ) {
        List<Teacher> schoolTeacher = teacherRepository.findBySchoolId(school.getId());

        model.addAttribute("title", "Преподаватели");
        model.addAttribute("school", school);
        model.addAttribute("teacher", schoolTeacher);

        return "teacher/index";
    }

    @GetMapping("/{id}")
    public String edit(
            @PathVariable("id") Long id,
            @GlobalFilter("school") School school,
            Model model
    ) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        List<TeachEduc> teacherSubjects = teachEducRepository.findBySchoolIdAndTeacherId(school.getId(), teacher.getId());

        TeacherSaveRequest request = TeacherSaveRequest.create(teacher, school, teacherSubjects);

        model.addAttribute("request", request);
        model.addAttribute("title", "Редактиране на преподавател");
        model.addAttribute("subjects", educObjRepository.findBySchoolId(school.getId()));

        return "teacher/save";
    }

    @GetMapping("/create")
    public String create(
            Model model,
            @GlobalFilter("school") School school
    ) {
        if (!model.containsAttribute("request")) {
            model.addAttribute("request", new TeacherSaveRequest());
        }

        List<EducObj> educObjs = educObjRepository.findBySchoolId(school.getId());

        model.addAttribute("title", "Нов преподавател");
        model.addAttribute("subjects", educObjs);

        return "teacher/save";
    }

    @PostMapping("/save")
    public String saveTeacher(
            @GlobalFilter("school") School school,
            @Valid @ModelAttribute TeacherSaveRequest request,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        Map<Long, EducObj> schoolSubjects = educObjRepository.findBySchoolId(school.getId())
                .stream()
                .collect(Collectors.toMap(EducObj::getId, Function.identity()));

        Teacher teacher = Optional.ofNullable(request.getId())
                .flatMap(teacherRepository::findById)
                .orElseGet(Teacher::new);

        boolean isTeacherNew = teacher.getId() == null;

        if (!request.getSubjectIds().stream().allMatch(schoolSubjects::containsKey)) {
            result.addError(new FieldError("teacherCreateRequest", "subjectIds", "invalid value found"));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            redirectAttributes.addFlashAttribute("errors", result);
            redirectAttributes.addFlashAttribute("request", request);

            return "redirect:/teacher/" + (isTeacherNew ? "create" : "/" + teacher.getId());
        }

        teacher.setName(request.getName());
        teacher.setEgn(request.getEgn());
        teacher.setSchool(school);

        teacherRepository.save(teacher);

        if (!isTeacherNew) {
            // Delete all existing TeachEduc entries for the given teacher and school
            teachEducRepository.deleteBySchoolIdAndTeacherId(school.getId(), teacher.getId());
        }

        List<TeachEduc> savedTeacherSubjects = request.getSubjectIds()
                .stream()
                .map(it -> new TeachEduc().setEducObj(schoolSubjects.get(it))
                        .setTeacher(teacher)
                        .setSchool(school)
                )
                .map(teachEducRepository::save)
                .toList();

        if (savedTeacherSubjects.size() != request.getSubjectIds().size()) {
            throw new RuntimeException("Error when saving teacher subjects.");
        }

        if (request.isPrincipal() || (school.getPrincipal() != null && school.getPrincipal().getId().equals(teacher.getId()))) {
            school.setPrincipal(request.isPrincipal() ? teacher : null);
            schoolRepository.save(school);
        }

        redirectAttributes.addFlashAttribute("result", new ActionResult("Успешно записване.", ActionResultType.SUCCESS));
        return "redirect:/teacher/" + teacher.getId();
    }


    @PostMapping("/{id}/delete")
    @Transactional
    RedirectView index(
            RedirectAttributes attributes,
            @PathVariable("id") Long id,
            @GlobalFilter("school") School school
    ) {
        try {
            if (school.getPrincipal() != null && school.getPrincipal().getId().equals(id)) {
                school.setPrincipal(null);
                schoolRepository.save(school);
            }

            teachEducRepository.deleteBySchoolIdAndTeacherId(school.getId(), id);
            teacherRepository.deleteById(id);

            attributes.addFlashAttribute("result", new ActionResult("Успешно изтриване.", ActionResultType.SUCCESS));
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            attributes.addFlashAttribute("result", new ActionResult("Съществуват свързани данни, изтриването не е възможно.", ActionResultType.ERROR));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/teacher");
    }

}
