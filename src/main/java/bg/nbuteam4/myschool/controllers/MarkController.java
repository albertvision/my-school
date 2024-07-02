package bg.nbuteam4.myschool.controllers;

import bg.nbuteam4.myschool.dto.ActionResult;
import bg.nbuteam4.myschool.dto.MarkCreateRequest;
import bg.nbuteam4.myschool.entity.*;
import bg.nbuteam4.myschool.enums.ActionResultType;
import bg.nbuteam4.myschool.enums.MarkStatus;
import bg.nbuteam4.myschool.repository.*;
import bg.nbuteam4.myschool.validation.GlobalFilter;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/marks")
public class MarkController {

    private final MarkRepository markRepository;

    private final StudyPeriodRepository studyPeriodRepository;
    private final TeacherRepository teacherRepository;
    private final EducObjRepository educObjRepository;
    private final TeachEducRepository teachEducRepository;
    private final ClassStudentRepository classStudentRepository;
    private final SchoolClassEducObjRepository schoolClassEducObjRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TypeMarkRepository typeMarkRepository;


    public MarkController(
            MarkRepository markRepository,
            StudyPeriodRepository studyPeriodRepository,
            TeacherRepository teacherRepository,
            EducObjRepository educObjRepository,
            TeachEducRepository teachEducRepository,
            ClassStudentRepository classStudentRepository,
            SchoolClassEducObjRepository schoolClassEducObjRepository,
            SchoolClassRepository schoolClassRepository,
            TypeMarkRepository typeMarkRepository
    ) {
        this.markRepository = markRepository;
        this.studyPeriodRepository = studyPeriodRepository;
        this.teacherRepository = teacherRepository;
        this.educObjRepository = educObjRepository;
        this.teachEducRepository = teachEducRepository;
        this.classStudentRepository = classStudentRepository;
        this.schoolClassEducObjRepository = schoolClassEducObjRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.typeMarkRepository = typeMarkRepository;
    }

    @GetMapping
    public String index(Model model,
                        @GlobalFilter("school") School school,
                        @GlobalFilter("studyPeriod") StudyPeriod studyPeriod,
                        @RequestParam("schoolClassId") Optional<Long> schoolClassId,
                        @RequestParam("teacherId") Optional<Long> teacherId,
                        RedirectAttributes redirectAttributes) {

        model.addAttribute("title", "Оценки");

        List<Teacher> teachers = teacherRepository.findAll();

        List<SchoolClass> schoolClasses = schoolClassRepository.findBySchoolId(school.getId())
                .stream()
                .sorted()
                .toList();

        model.addAttribute("school", school);
        model.addAttribute("teachers", teachers);
        model.addAttribute("schoolClasses", schoolClasses);

        if (schoolClassId.isPresent() && teacherId.isPresent()) {


            List<ClassStudent> classStudents = classStudentRepository.findByStudyPeriodIdAndSchoolClassIdOrderByStudentNumberInClass(studyPeriod.getId(), schoolClassId.get());
            model.addAttribute("classStudents", classStudents);

            Set<EducObj> teacherEducObjs = teachEducRepository.findBySchoolIdAndTeacherId(school.getId(), teacherId.get())
                    .stream()
                    .map(TeachEduc::getEducObj)
                    .collect(Collectors.toSet());

            // Fetch educObjs associated with the school class and study period
            Set<EducObj> classEducObjs = schoolClassEducObjRepository.findByStudyPeriodIdAndSchoolClassId(studyPeriod.getId(), schoolClassId.get())
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
//        model.addAttribute("marks", markRepository.findByStudyPeriodIdAndSchoolClassId(studyPeriodId, schoolClassId));
            model.addAttribute("marksDTO", markRepository.findByStudyPeriodIdAndSchoolClassIdAndTeacherIdWithStudentNamesOrderByIdAsc(studyPeriod.getId(), schoolClassId.get(), teacherId.get()));

//            model.addAttribute("studyPeriodId", studyPeriodId);
            model.addAttribute("selectedTeacher", teacherRepository.findById(teacherId.get()).orElse(null));
            model.addAttribute("selectedSchoolClass", schoolClassRepository.findById(schoolClassId.get()).orElse(null));
            model.addAttribute("teacherClassEducObjs", teacherClassEducObjs);
            ////    1 - болест, 2 - домашни причини, 3 - закъснение, 9 - отсъствие
            model.addAttribute("typeMarks", typeMarkRepository.findBySchoolIdOrderByIdAsc(school.getId()));
//        Статус: 0 - необработено, 1 - неизвинено, 2 - извинено
            model.addAttribute("statuses", MarkStatus.values());

        }
        model.addAttribute("schoolClassId", schoolClassId.orElse(null));
        model.addAttribute("teacherId", teacherId.orElse(null));
        return "marks/index";
    }

    @PostMapping("/create")
    public String createMark(@Valid @ModelAttribute MarkCreateRequest request,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("schoolClassId") Optional<Long> schoolClassId,
                             @RequestParam("teacherId") Optional<Long> teacherId) {
        if (result.hasErrors()) {
            // Handle validation errors
            redirectAttributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени данни.", ActionResultType.ERROR));
            redirectAttributes.addFlashAttribute("errors", result);
            redirectAttributes.addFlashAttribute("request", request); // To keep the entered data in case of errors
            return "redirect:/marks?schoolClassId=" + schoolClassId.orElse(null) + "&teacherId=" + teacherId.orElse(null);
        }

        // Convert MarkCreateRequest to Mark entity
        Mark mark = new Mark();
        mark.setStudyPeriod(studyPeriodRepository.findById(request.getStudyPeriodId()).orElse(null));
        mark.setSchoolClass(schoolClassRepository.findById(request.getSchoolClassId()).orElse(null));
        mark.setStudentNumberInClass(request.getStudentNumberInClass());
        mark.setEducObj(educObjRepository.findById(request.getEducObjId()).orElse(null));
        mark.setTeacher(teacherRepository.findById(request.getTeacherId()).orElse(null));
        mark.setValue(request.getValue());
        mark.setTypeMark(request.getTypeMark());
        mark.setStatus(request.getStatus());
        mark.setDateTime(request.getDateTime());

        try {
            // Save the mark entity
            markRepository.save(mark);
            redirectAttributes.addFlashAttribute("result", new ActionResult("Успешно въвеждане на оценка.", ActionResultType.SUCCESS));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Възникна грешка при въвеждане на оценка.", ActionResultType.ERROR));
        }

        return "redirect:/marks?schoolClassId=" + schoolClassId.orElse(null) + "&teacherId=" + teacherId.orElse(null);
    }

    @PostMapping("/{id}/delete")
    public String deleteMark(@PathVariable Long id, RedirectAttributes redirectAttributes,
                             @RequestParam("schoolClassId") Optional<Long> schoolClassId,
                             @RequestParam("teacherId") Optional<Long> teacherId) {
        // Check if the mark exists
        Mark mark = markRepository.findById(id).orElse(null);
        if (mark == null) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Оценката не беше намерена.", ActionResultType.ERROR));
        } else {
            try {
                // Delete the mark entity
                markRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("result", new ActionResult("Успешно изтриване на оценка.", ActionResultType.SUCCESS));
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("result", new ActionResult("Възникна грешка при изтриване на оценка.", ActionResultType.ERROR));
            }
        }

        return "redirect:/marks?schoolClassId=" + schoolClassId.orElse(null) + "&teacherId=" + teacherId.orElse(null);
    }

    @PostMapping("/{id}/update")
    public String updateMark(@PathVariable Long id, @Valid @ModelAttribute MarkCreateRequest request,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("schoolClassId") Optional<Long> schoolClassId,
                             @RequestParam("teacherId") Optional<Long> teacherId) {
        if (result.hasErrors()) {
            // Handle validation errors
            redirectAttributes.addFlashAttribute("result", new ActionResult("Има невалидно попълнени полета.", ActionResultType.ERROR));
            redirectAttributes.addFlashAttribute("errors", result);
            redirectAttributes.addFlashAttribute("request", request); // To keep the entered data in case of errors
            return "redirect:/marks?schoolClassId=" + schoolClassId.orElse(null) + "&teacherId=" + teacherId.orElse(null);
        }

        // Retrieve the existing mark entity by ID
        Mark mark = markRepository.findById(id).orElse(null);
        if (mark == null) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Оценката не беше намерена.", ActionResultType.ERROR));
            return "redirect:/marks?schoolClassId=" + schoolClassId.orElse(null) + "&teacherId=" + teacherId.orElse(null);
        }

        // Update the fields of the existing mark entity
        mark.setStudyPeriod(studyPeriodRepository.findById(request.getStudyPeriodId()).orElse(null));
        mark.setSchoolClass(schoolClassRepository.findById(request.getSchoolClassId()).orElse(null));
        mark.setStudentNumberInClass(request.getStudentNumberInClass());
        mark.setEducObj(educObjRepository.findById(request.getEducObjId()).orElse(null));
        mark.setTeacher(teacherRepository.findById(request.getTeacherId()).orElse(null));
        mark.setValue(request.getValue());
        mark.setTypeMark(request.getTypeMark());
        mark.setStatus(request.getStatus());
        mark.setDateTime(request.getDateTime());

        try {
            // Save the updated mark entity
            markRepository.save(mark);
            redirectAttributes.addFlashAttribute("result", new ActionResult("Успешна редакция на оценка.", ActionResultType.SUCCESS));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("result", new ActionResult("Възникна грешка при редакция на оценка.", ActionResultType.ERROR));
        }

        return "redirect:/marks?schoolClassId=" + schoolClassId.orElse(null) + "&teacherId=" + teacherId.orElse(null);
    }

}
