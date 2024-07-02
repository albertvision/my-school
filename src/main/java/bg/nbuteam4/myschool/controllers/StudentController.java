package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.StudentSaveRequest;
import bg.nbuteam4.myschool.entity.*;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.repository.ClassStudentRepository;
import bg.nbuteam4.myschool.repository.SchoolClassRepository;
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

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/student")
@PreAuthorize("hasRole('ADMIN')")
public class StudentController {
    private final SchoolRepository schoolRepository;
    private final HttpSession httpSession;
    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final ClassStudentRepository classStudentRepository;

    public StudentController(
            StudentRepository studentRepository,
            SchoolRepository schoolRepository,
            HttpSession httpSession,
            SchoolClassRepository schoolClassRepository, ClassStudentRepository classStudentRepository) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.httpSession = httpSession;
        this.schoolClassRepository = schoolClassRepository;
        this.classStudentRepository = classStudentRepository;
    }

    @GetMapping
    String index(
            Model model,
            @GlobalFilter("school") School school,
            @GlobalFilter("studyPeriod") StudyPeriod studyPeriod,
            @RequestParam(value = "schoolClassId", required = false) Long schoolClassId
    ) {

        List<Student> students = studentRepository.findBySchoolId(school.getId());

        Map<SchoolClass, List<ClassStudent>> classStudents = Optional.ofNullable(schoolClassId)
                .map(it -> classStudentRepository.findByStudyPeriodIdAndSchoolClassId(studyPeriod.getId(), schoolClassId))
                .orElseGet(() -> classStudentRepository.findByStudyPeriodId(studyPeriod.getId()))
                .stream()
                .sorted(Comparator.comparing(ClassStudent::getSchoolClass))
                .sorted(Comparator.comparing(ClassStudent::getStudentNumberInClass))
                .collect(Collectors.groupingBy(
                        ClassStudent::getSchoolClass,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        model.addAttribute("classStudentsByClass", classStudents);
        model.addAttribute("schoolClasses", getSchoolClassesSelectOptions(school));
        model.addAttribute("selectedSchoolClassId", schoolClassId);
        model.addAttribute("students", students);
        model.addAttribute("title", "Ученици");

        return "student/index";
    }

    @GetMapping("/{id}")
    String edit(
            Model model,
            @PathVariable("id") Long id,
            @GlobalFilter("school") School school,
            @GlobalFilter("studyPeriod") StudyPeriod studyPeriod
    ) {
        Student student = studentRepository.findByIdAndSchool(id, school).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        Optional<ClassStudent> classStudent = classStudentRepository.findBySchoolIdAndStudyPeriodIdAndStudentId(
                school.getId(),
                studyPeriod.getId(),
                student.getId()
        );

        StudentSaveRequest request = Optional.ofNullable((StudentSaveRequest) model.getAttribute("request"))
                .orElse(StudentSaveRequest.createFromEntity(student, classStudent));

        model.addAttribute("request", request);
        model.addAttribute("schoolClasses", getSchoolClassesSelectOptions(school));
        model.addAttribute("title", "Редактиране на ученик");

        return "student/save";
    }

    // -------------------
    @PostMapping("/save")
    String doSave(
            @GlobalFilter("school") School school,
            @GlobalFilter("studyPeriod") StudyPeriod studyPeriod,
            @Valid @ModelAttribute StudentSaveRequest saveRequest,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        Student student = Optional.ofNullable(saveRequest.getId())
                .flatMap(it -> studentRepository.findByIdAndSchool(it, school))
                .orElseGet(Student::new);

        boolean isStudentNew = student.getId() == null;

        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(saveRequest.getSchoolClassId());

        if (schoolClass.isEmpty() || !schoolClass.get().getSchool().equals(school)) {
            result.addError(new FieldError("studentCreateRequest", "schoolClassId", "Избрали сте несъществуващ в училището клас."));
        }

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

            Student finalStudent = student;
            ClassStudent classStudent = Optional.ofNullable(student.getId())
                    .flatMap(studentId -> classStudentRepository
                            .findBySchoolIdAndStudyPeriodIdAndStudentId(
                                    school.getId(),
                                    studyPeriod.getId(),
                                    studentId
                            )
                    )
                    .orElseGet(() -> new ClassStudent()
                            .setStudyPeriod(studyPeriod)
                            .setStudent(finalStudent)
                    );

            classStudent.setSchoolClass(schoolClass.get())
                    .setStudentNumberInClass(
                            1 + classStudentRepository
                                    .findHighestStudentNumberInSchoolClassId(saveRequest.getSchoolClassId())
                                    .orElse(0)
                    );

            classStudentRepository.save(classStudent);

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
    String create(
            Model model,
            @GlobalFilter("school") School school
    ) {
        if (!model.containsAttribute("request")) {
            model.addAttribute("request", new StudentSaveRequest().setStatus(true));
        }

        model.addAttribute("schoolClasses", getSchoolClassesSelectOptions(school));
        model.addAttribute("title", "Нов ученик");

        return "student/save";
    }

    private Map<Long, String> getSchoolClassesSelectOptions(School school) {
        return schoolClassRepository
                .findBySchoolId(school.getId())
                .stream().collect(Collectors.toMap(SchoolClass::getId, SchoolClass::getName));
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
