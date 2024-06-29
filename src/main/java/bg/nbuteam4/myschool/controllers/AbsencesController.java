package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.ActionResultType;
import bg.nbuteam4.myschool.dto.TeachEducCreateRequest;
import bg.nbuteam4.myschool.entity.*;
import bg.nbuteam4.myschool.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/absences")
public class AbsencesController {

    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;
    private final EducObjRepository educObjRepository;
    private final TeachEducRepository teachEducRepository;
    private final StudentRepository studentRepository;
    private final HttpSession httpSession;
    private final StudyPeriodRepository studyPeriodRepository;

    public AbsencesController(SchoolRepository schoolRepository, TeacherRepository teacherRepository, EducObjRepository educObjRepository, TeachEducRepository teachEducRepository, StudentRepository studentRepository, HttpSession httpSession,
                              StudyPeriodRepository studyPeriodRepository) {
        this.schoolRepository = schoolRepository;
        this.teacherRepository = teacherRepository;
        this.educObjRepository = educObjRepository;
        this.teachEducRepository = teachEducRepository;
        this.studentRepository = studentRepository;
        this.httpSession = httpSession;
        this.studyPeriodRepository = studyPeriodRepository;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("title", "Отсъствия");

        long schoolId = (long) httpSession.getAttribute("schoolId");
        long studyPeriodId = (long) httpSession.getAttribute("studyPeriodId");
        long teacherId = (long) httpSession.getAttribute("teacherId"); //add teacher to session if user isTeacher

        School school = schoolRepository.findById(schoolId).orElse(null);
        StudyPeriod studyPeriod = studyPeriodRepository.findById(studyPeriodId).orElse(null);
        List<Teacher> teachers = teacherRepository.findAll();
        List<EducObj> schoolEducObjs = educObjRepository.findBySchoolId(schoolId);

        model.addAttribute("school", school);
        model.addAttribute("teachers", teachers);
        model.addAttribute("schoolEducObjs", schoolEducObjs);

        return "absences/index";
    }

    @PostMapping("/set-teacher-and-class")
    public String setTeacherAndClass(Model model) {

        model.addAttribute("title", "Отсъствия");

        long schoolId = (long) httpSession.getAttribute("schoolId");
        long studyPeriodId = (long) httpSession.getAttribute("studyPeriodId");
        long teacherId = (long) httpSession.getAttribute("teacherId"); //add teacher to session if user isTeacher

        School school = schoolRepository.findById(schoolId).orElse(null);

        Teacher teacher;
        List<Teacher> teachers;
        if (teacherId != 0) {
            teacher = teacherRepository.findById(teacherId).orElse(null);
            model.addAttribute("teacher", teacher);
        } else {
            teachers = teacherRepository.findAll();
            model.addAttribute("teachers", teachers);
        }

        StudyPeriod studyPeriod = studyPeriodRepository.findById(studyPeriodId).orElse(null);
        List<EducObj> schoolEducObjs = educObjRepository.findBySchoolId(schoolId);

        model.addAttribute("school", school);
        model.addAttribute("schoolEducObjs", schoolEducObjs);
//
//        //this should be the subjects in school of teacher in class
//        List<EducObj> schoolTeacherEducObjs = teachEducRepository.findBySchoolIdAndTeacherId(schoolId, teacherId)
//                .stream()
//                .map(TeachEduc::getEducObj)
//                .toList()
//                .filter(educObj -> {educObj);
//        model.addAttribute("school", school);
//        model.addAttribute("selectedTeacher", teacher);
//        model.addAttribute("teachers", teachers);
//        model.addAttribute("schoolEducObjs", schoolEducObjs);
//        model.addAttribute("schoolTeacherEducObjs", schoolTeacherEducObjs);
//

        return "absences/index";
    }

//delete all subjects of teacher and then add the checked ones

    @PostMapping("/updateSubjects")
    public String updateSubjects(@Valid @ModelAttribute TeachEducCreateRequest teachEducCreateRequest,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam List<Long> educObjIds,
                                 Model model) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Има невалидни данни.", ActionResultType.ERROR));
            redirectAttributes.addFlashAttribute("errors", result);
            redirectAttributes.addFlashAttribute("teachEduc", teachEducCreateRequest);

            return "redirect:/teachEduc";
        }

        // Delete all existing TeachEduc entries for the given teacher and school
        teachEducRepository.deleteBySchoolIdAndTeacherId(teachEducCreateRequest.getSchoolId(), teachEducCreateRequest.getTeacherId());


        // Create new TeachEduc entries based on the selected subjects
        School school = schoolRepository.findById(teachEducCreateRequest.getSchoolId()).orElse(null);
        Teacher teacher = teacherRepository.findById(teachEducCreateRequest.getTeacherId()).orElse(null);

        for (Long educObjId : educObjIds) {
            EducObj educObj = educObjRepository.findById(educObjId).orElse(null);
            if (educObj != null) {
                TeachEduc teachEduc = new TeachEduc();
                teachEduc.setSchool(school);
                teachEduc.setTeacher(teacher);
                teachEduc.setEducObj(educObj);
                teachEducRepository.save(teachEduc);
            }
        }
        redirectAttributes.addFlashAttribute("result", new ActionResult("Успешно запазване.", ActionResultType.SUCCESS));
        return "redirect:/teachEduc";
    }


/*

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
*/

}
