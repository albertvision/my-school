package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.AbsenceCreateRequest;
import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.entity.*;
import bg.nbuteam4.myschool.enums.AbsenceStatus;
import bg.nbuteam4.myschool.enums.AbsenceType;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/absences")
public class AbsencesController {

    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;
    private final EducObjRepository educObjRepository;
    private final TeachEducRepository teachEducRepository;
    private final ClassStudentRepository classStudentRepository;
    private final HttpSession httpSession;
    private final StudyPeriodRepository studyPeriodRepository;
    private final SchoolClassEducObjRepository schoolClassEducObjRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final AbsenceRepository absenceRepository;

    public AbsencesController(SchoolRepository schoolRepository, TeacherRepository teacherRepository, EducObjRepository educObjRepository, TeachEducRepository teachEducRepository, ClassStudentRepository classStudentRepository, HttpSession httpSession,
                              StudyPeriodRepository studyPeriodRepository,
                              SchoolClassEducObjRepository schoolClassEducObjRepository,
                              SchoolClassRepository schoolClassRepository, AbsenceRepository absenceRepository) {
        this.schoolRepository = schoolRepository;
        this.teacherRepository = teacherRepository;
        this.educObjRepository = educObjRepository;
        this.teachEducRepository = teachEducRepository;
        this.classStudentRepository = classStudentRepository;
        this.httpSession = httpSession;
        this.studyPeriodRepository = studyPeriodRepository;
        this.schoolClassEducObjRepository = schoolClassEducObjRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.absenceRepository = absenceRepository;
    }

    @GetMapping
    public String index(Model model) {

        model.addAttribute("title", "Отсъствия");

        long schoolId = (long) httpSession.getAttribute("schoolId");
        long studyPeriodId = (long) httpSession.getAttribute("studyPeriodId");
        long sessionTeacherId = 0L;//(long) httpSession.("teacherId"); //find teacher from seesion user - if user isTeacher

        Teacher teacher = null;
        List<Teacher> teachers = null;
        if (sessionTeacherId != 0) {
            teacher = teacherRepository.findById(sessionTeacherId).orElse(null);
            model.addAttribute("teacher", teacher);
        } else {
            teachers = teacherRepository.findAll();
            model.addAttribute("teachers", teachers);
        }

        School school = schoolRepository.findById(schoolId).orElse(null);
        StudyPeriod studyPeriod = studyPeriodRepository.findById(studyPeriodId).orElse(null);


        List<SchoolClass> schoolClasses = schoolClassRepository.findBySchoolId(schoolId);

        model.addAttribute("school", school);
        model.addAttribute("teachers", teachers);
        model.addAttribute("schoolClasses", schoolClasses);

        return "absences/index";
    }

    @PostMapping
    public String setTeacherAndClass(@RequestParam Long requestTeacherId, @RequestParam Long schoolClassId, Model model) {

        model.addAttribute("title", "Отсъствия");

        long schoolId = (long) httpSession.getAttribute("schoolId");
        long studyPeriodId = (long) httpSession.getAttribute("studyPeriodId");
        long sessionTeacherId = 0L;//(long) httpSession.getAttribute("teacherId"); //find teacher from seesion user - if user isTeacher
        //TODO make this from session rather than 0L
        School school = schoolRepository.findById(schoolId).orElse(null);

        model.addAttribute("school", school);

        Teacher teacher = null;
        List<Teacher> teachers = null;
        if (sessionTeacherId != 0) {
            teacher = teacherRepository.findById(sessionTeacherId).orElse(null);
        } else {
            teachers = teacherRepository.findAll();
            teacher = teacherRepository.findById(requestTeacherId).orElse(null);
            model.addAttribute("teachers", teachers);
        }
            model.addAttribute("teacher", teacher);

        StudyPeriod studyPeriod = studyPeriodRepository.findById(studyPeriodId).orElse(null);

        List<SchoolClass> schoolClasses = schoolClassRepository.findBySchoolId(schoolId);
        model.addAttribute("schoolClasses", schoolClasses);

        List<ClassStudent> classStudents = classStudentRepository.findByStudyPeriodIdAndSchoolClassId(studyPeriodId, schoolClassId);
        model.addAttribute("classStudents", classStudents);

        // Fetch educObjs taught by the teacher in the given school
        long teacherId;
        if(sessionTeacherId!=0) teacherId=sessionTeacherId;
        else teacherId=requestTeacherId;
        Set<EducObj> teacherEducObjs = teachEducRepository.findBySchoolIdAndTeacherId(schoolId, teacherId)
                .stream()
                .map(TeachEduc::getEducObj)
                .collect(Collectors.toSet());

        // Fetch educObjs associated with the school class and study period
        Set<EducObj> classEducObjs = schoolClassEducObjRepository.findByStudyPeriodIdAndSchoolClassId(studyPeriodId, schoolClassId)
                .stream()
                .map(SchoolClassEducObj::getEducObj)
                .collect(Collectors.toSet());

        // Retain only the elements that are present in both sets
        teacherEducObjs.retainAll(classEducObjs);

        // Sort by ID and collect into a LinkedHashSet to maintain the order
        LinkedHashSet<EducObj> teacherClassEducObjs = teacherEducObjs.stream()
                .sorted((e1, e2) -> e1.getId().compareTo(e2.getId()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

//        //this should be the subjects in school of teacher in class


        //if the teacher is selected through the dropdown list
        model.addAttribute("absences", absenceRepository.findAll());
        model.addAttribute("studyPeriodId", studyPeriodId);
        model.addAttribute("selectedTeacher", teacherRepository.findById(requestTeacherId).orElse(null));
        model.addAttribute("selectedSchoolClass", schoolClassRepository.findById(schoolClassId).orElse(null));
        model.addAttribute("teacherClassEducObjs", teacherClassEducObjs);
        ////    1 - болест, 2 - домашни причини, 3 - закъснение, 9 - отсъствие
        model.addAttribute("absenceTypes", AbsenceType.values());
//        Статус: 0 - необработено, 1 - неизвинено, 2 - извинено
        model.addAttribute("statuses", AbsenceStatus.values());


        return "absences/index";
    }

    @PostMapping("/create")
    public String createAbsence(@Valid @ModelAttribute AbsenceCreateRequest request,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Handle validation errors
            redirectAttributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени данни.", ActionResultType.ERROR));
            redirectAttributes.addFlashAttribute("errors", result);
            redirectAttributes.addFlashAttribute("request", request); // To keep the entered data in case of errors
            return "redirect:/absences"; // Redirect to the form page
        }

        // Convert AbsenceCreateRequest to Absence entity
        Absence absence = new Absence();
        absence.setStudyPeriod(studyPeriodRepository.findById(request.getStudyPeriodId()).orElse(null));
        absence.setSchoolClass(schoolClassRepository.findById(request.getSchoolClassId()).orElse(null));
        absence.setStudentNumberInClass(request.getStudentNumberInClass());
        absence.setAbsenceDate(request.getAbsenceDate());
        absence.setEducObj(educObjRepository.findById(request.getEducObjId()).orElse(null));
        absence.setAbsenceType(request.getAbsenceType());
        absence.setTeacher(teacherRepository.findById(request.getTeacherId()).orElse(null));
        absence.setNotes(request.getNotes());
        absence.setStatus(request.getStatus());

        try {
            // Save the absence entity
            absenceRepository.save(absence);
            redirectAttributes.addFlashAttribute("result", new ActionResult("Успешно въвеждане на отсъствие.", ActionResultType.SUCCESS));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Възникна грешка при въвеждане на отсъствие.", ActionResultType.ERROR));
        }

        return "redirect:/absences";
    }

    @PostMapping("/{id}/delete")
    public String deleteAbsence(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // Check if the absence exists
        Absence absence = absenceRepository.findById(id).orElse(null);
        if (absence == null) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Отсъствието не беше намерено.", ActionResultType.ERROR));
        } else {
            try {
                // Delete the absence entity
                absenceRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("result", new ActionResult("Успешно изтриване на отсъствие.", ActionResultType.SUCCESS));
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("result", new ActionResult("Възникна грешка при изтриване на отсъствие.", ActionResultType.ERROR));
            }
        }

        return "redirect:/absences";
    }

    @PostMapping("/{id}/update")
    public String updateAbsence(@PathVariable Long id, @Valid @ModelAttribute AbsenceCreateRequest request,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Handle validation errors
            redirectAttributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            redirectAttributes.addFlashAttribute("errors", result);
            redirectAttributes.addFlashAttribute("request", request); // To keep the entered data in case of errors
            return "redirect:/absences"; // Redirect to the form page
        }

        // Retrieve the existing absence entity by ID
        Absence absence = absenceRepository.findById(id).orElse(null);
        if (absence == null) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Отсъствието не беше намерено.", ActionResultType.ERROR));
            return "redirect:/absences";
        }

        // Update the fields of the existing absence entity
        absence.setStudyPeriod(studyPeriodRepository.findById(request.getStudyPeriodId()).orElse(null));
        absence.setSchoolClass(schoolClassRepository.findById(request.getSchoolClassId()).orElse(null));
        absence.setStudentNumberInClass(request.getStudentNumberInClass());
        //maybe make these un-editable? ^^^ TODO
        absence.setAbsenceDate(request.getAbsenceDate());
        absence.setEducObj(educObjRepository.findById(request.getEducObjId()).orElse(null));
        absence.setAbsenceType(request.getAbsenceType());
        absence.setTeacher(teacherRepository.findById(request.getTeacherId()).orElse(null));
        absence.setNotes(request.getNotes());
        absence.setStatus(request.getStatus());

        try {
            // Save the updated absence entity
            absenceRepository.save(absence);
            redirectAttributes.addFlashAttribute("result", new ActionResult("Успешна редакция на отсъствие.", ActionResultType.SUCCESS));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Възникна грешка при редакция на отсъствие.", ActionResultType.ERROR));
        }

        return "redirect:/absences";
    }







}
